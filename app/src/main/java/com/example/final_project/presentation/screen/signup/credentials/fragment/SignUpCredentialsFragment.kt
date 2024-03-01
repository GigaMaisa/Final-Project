package com.example.final_project.presentation.screen.signup.credentials.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentSignUpCredentialsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.signup.SendUserDataEvent
import com.example.final_project.presentation.screen.signup.credentials.viewmodel.SignUpCredentialsNavigationEvents
import com.example.final_project.presentation.screen.signup.credentials.viewmodel.SignUpCredentialsViewModel
import com.example.final_project.presentation.state.AdditionalDataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpCredentialsFragment : BaseFragment<FragmentSignUpCredentialsBinding>(FragmentSignUpCredentialsBinding::inflate) {
    private val viewModel: SignUpCredentialsViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        btnCreateAccount.setOnClickListener {
            viewModel.onEvent(SendUserDataEvent.SendUserData(
                email = etEmail.text.toString(),
                password = etPassword.text.toString(),
                fullName = etFullName.text.toString()
            ))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigationEvent.collect {
                        handleNavigationEvents(events = it)
                    }
                }

                launch {
                    viewModel.dataState.collect {
                        handleAdditionalDataState(state = it)
                    }
                }

            }
        }
    }

    private fun handleAdditionalDataState(state: AdditionalDataState) {
        with(binding) {
            if (state.isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                View.GONE
            }

            state.errorMessage?.let {
                progressBar.visibility = View.GONE
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