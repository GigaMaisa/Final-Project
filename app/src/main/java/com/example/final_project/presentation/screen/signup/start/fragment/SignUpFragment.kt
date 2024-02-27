package com.example.final_project.presentation.screen.signup.start.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentSignUpBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.signup.SendSmsEvent
import com.example.final_project.presentation.screen.signup.start.viewmodel.SignUpNavigationEvents
import com.example.final_project.presentation.screen.signup.start.viewmodel.SignUpViewModel
import com.example.final_project.presentation.state.VerificationState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private val viewModel: SignUpViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    override fun setUp() {}

    override fun setUpListeners() = with(binding) {
        btnContinue.setOnClickListener {
            val countryCode: String = ccp.selectedCountryCodeWithPlus
            val number = etPhoneNumber.text.toString().trim()

            viewModel.onEvent(SendSmsEvent.SendSmsToProvidedNumber(phoneNumber = "$countryCode$number", options = providePhoneAuthOptionsBuilder("$countryCode$number")))
        }

        tvLogin.setOnClickListener {
            viewModel.onUiEvent(SignUpNavigationEvents.NavigateToSignInPage)
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
                    viewModel.verificationState.collect {
                        handleVerificationState(it)
                    }
                }
            }
        }
    }

    private fun handleVerificationState(verificationState: VerificationState) = with(binding) {
        if (verificationState.isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            View.GONE
        }

        verificationState.errorMessage?.let {
            progressBar.visibility = View.GONE
        }
    }

    private fun handleNavigationEvents(events: SignUpNavigationEvents) {
        when (events) {
            is SignUpNavigationEvents.NavigateToSignInPage -> {
                findNavController().navigateUp()
            }

            is SignUpNavigationEvents.NavigateToOtpFillPage -> {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToPasscodeFragment(
                    phoneNumber = events.phoneNumber,
                    verificationId = events.verificationId
                ))
            }
        }
    }

    private fun providePhoneAuthOptionsBuilder(phoneNumber: String) : PhoneAuthOptions.Builder {
        return PhoneAuthOptions.newBuilder(auth)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity()).
            setPhoneNumber(phoneNumber)
    }
}