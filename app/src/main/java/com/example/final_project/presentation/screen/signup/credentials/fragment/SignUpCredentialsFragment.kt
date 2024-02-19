package com.example.final_project.presentation.screen.signup.credentials.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentSignUpCredentialsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.signup.credentials.viewmodel.SignUpCredentialsNavigationEvents
import com.example.final_project.presentation.screen.signup.credentials.viewmodel.SignUpCredentialsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpCredentialsFragment : BaseFragment<FragmentSignUpCredentialsBinding>(FragmentSignUpCredentialsBinding::inflate) {
    private val viewModel: SignUpCredentialsViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        btnCreateAccount.setOnClickListener {
            viewModel.onUiEvent(SignUpCredentialsNavigationEvents.NavigateToSuccessPage)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect {
                    handleNavigationEvents(events = it)
                }
            }
        }
    }

    private fun handleNavigationEvents(events: SignUpCredentialsNavigationEvents) {
        when (events) {
            is SignUpCredentialsNavigationEvents.NavigateToSuccessPage -> {
                findNavController().navigate(SignUpCredentialsFragmentDirections.actionSignUpCredentialsFragmentToSignUpSuccessFragment())
            }
        }
    }
}