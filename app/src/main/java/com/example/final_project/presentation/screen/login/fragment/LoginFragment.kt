package com.example.final_project.presentation.screen.login.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentLoginBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.login.viewmodel.LoginFragmentUiEvents
import com.example.final_project.presentation.screen.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        tvForgotPass.setOnClickListener {
            viewModel.onUiEvent(LoginFragmentUiEvents.ForgotPassword)
        }

        tvRegister.setOnClickListener {
            viewModel.onUiEvent(LoginFragmentUiEvents.NavigateToSignUpPage)
        }

        btnContinue.setOnClickListener {
            viewModel.onUiEvent(LoginFragmentUiEvents.NavigateToHomePage)
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

    private fun handleNavigationEvents(events: LoginFragmentUiEvents) {
        when (events) {
            is LoginFragmentUiEvents.NavigateToSignUpPage -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }

            is LoginFragmentUiEvents.NavigateToHomePage -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }

            is LoginFragmentUiEvents.NavigateToSmsAuthPage -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToPasscodeFragment(events.phoneNumber))
            }

            is LoginFragmentUiEvents.ForgotPassword -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
            }
        }
    }
}