package com.example.final_project.presentation.screen.chat.fragment

import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentChatBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.model.Message
import com.example.final_project.presentation.screen.chat.adapter.MessageRecyclerViewAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val messageAdapter = MessageRecyclerViewAdapter()
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null
    override fun setUp() {
        d("currentUser", Firebase.auth.currentUser.toString())
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().reference

        senderRoom = "agivv52GYgZkIfp95NVGoXIuxAH2" + senderUid
        receiverRoom = senderUid + "agivv52GYgZkIfp95NVGoXIuxAH2"

        with(binding.charRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        binding.sentButton.setOnClickListener{

            val message = binding.messageBox.text.toString()
            val messageObject = Message(Random.nextLong(1, 99999) , message, senderUid!!)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.messageBox.setText("")
        }

    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }


}