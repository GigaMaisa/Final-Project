package com.example.final_project.presentation.screen.chat.chat_messaging.fragment

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentChatBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.model.Message
import com.example.final_project.presentation.screen.chat.adapter.MessageRecyclerViewAdapter
import com.example.final_project.presentation.screen.chat.chat_messaging.viewmodel.ChatViewModel
import com.example.final_project.presentation.state.ChatState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch
import kotlin.random.Random

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private val safeArgs: ChatFragmentArgs by navArgs()
    private lateinit var receiverId: String
    private val messageAdapter = MessageRecyclerViewAdapter()

    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null
    override fun setUp() {
        viewModel.getReceiverId(safeArgs.uuid)
        receiverId = safeArgs.uuid

        with(binding.charRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }




        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = Firebase.database.reference

        senderRoom = receiverId + senderUid
        receiverRoom = senderUid + receiverId



        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfMessages = mutableListOf<Message>()
                    snapshot.children.forEach {
                        listOfMessages.add(it.getValue(Message::class.java)!!)
                    }
                    viewModel.fetchInitialChat(listOfMessages)
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        binding.sentButton.setOnClickListener{
            val message = binding.messageBox.text.toString()

            senderUid?.let {uid ->
                val senderRoom = "$receiverId$uid"
                val receiverRoom = "$uid$receiverId"

                val messageObject = Message(Random.nextLong(1, Long.MAX_VALUE), message, uid)
                d("messageBro", messageObject.toString())
                d("messageBro", viewModel.chatStateFlow.value.messages.toString())

                val messageId = mDbRef.child("chats").child(senderRoom).child("messages").push().key
                if (messageId != null) {
                    val senderMessageRef = mDbRef.child("chats").child(senderRoom).child("messages").child(messageId)
                    val receiverMessageRef = mDbRef.child("chats").child(receiverRoom).child("messages").child(messageId)

                    senderMessageRef.setValue(messageObject)
                        .addOnSuccessListener {
                            receiverMessageRef.setValue(messageObject)
                        }
                        .addOnFailureListener { exception ->

                        }
                }
                binding.messageBox.setText("")
            }
        }
    }

    override fun setUpListeners() {

    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chatStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: ChatState) {
        with(state) {
            messages?.let {
                messageAdapter.submitList(it)
            }
        }
    }
}