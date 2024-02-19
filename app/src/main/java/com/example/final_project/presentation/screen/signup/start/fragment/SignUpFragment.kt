package com.example.final_project.presentation.screen.signup.start.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentSignUpBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.signup.start.viewmodel.SignUpNavigationEvents
import com.example.final_project.presentation.screen.signup.start.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private val viewModel: SignUpViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        btnContinue.setOnClickListener {
            viewModel.onUiEvent(SignUpNavigationEvents.NavigateToSmsAuthPage(etPhoneNumber.text.toString()))
        }

        tvLogin.setOnClickListener {
            viewModel.onUiEvent(SignUpNavigationEvents.NavigateToSignInPage)
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

    private fun handleNavigationEvents(events: SignUpNavigationEvents) {
        when (events) {
            is SignUpNavigationEvents.NavigateToSignInPage -> {
                findNavController().navigateUp()
            }

            is SignUpNavigationEvents.NavigateToSmsAuthPage -> {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToPasscodeFragment(events.phoneNumber))
            }
        }
    }
}