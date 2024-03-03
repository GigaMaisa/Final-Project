package com.example.final_project.presentation.screen.chat.chat_contacts

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentChatContactsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.chat.ChatContactEvent
import com.example.final_project.presentation.screen.chat.adapter.ContactsRecyclerViewAdapter
import com.example.final_project.presentation.state.ContactsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatContactsFragment : BaseFragment<FragmentChatContactsBinding>(FragmentChatContactsBinding::inflate) {

    private val viewModel: ChatContactsViewModel by viewModels()
    private val contactsAdapter = ContactsRecyclerViewAdapter()
    override fun setUp() {
        setUpRecycler()
        viewModel.onEvent(ChatContactEvent.GetContactsEvent)
    }

    override fun setUpListeners() {
        contactsAdapter.onContactClick = {
            findNavController().navigate(ChatContactsFragmentDirections.actionChatPageToChatFragment(it.receiverId!!))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactsStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: ContactsState) {
        with(state) {
            contacts?.let {
                contactsAdapter.submitList(it)
            }
        }
    }

    private fun setUpRecycler() {
        with(binding.recyclerViewContacts) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactsAdapter
        }
    }

}