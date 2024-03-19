package com.example.final_project.presentation.screen.forgot_password.fragment

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentForgotPasswordBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.forgot_pass.ForgotPasswordEvents
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.screen.forgot_password.viewmodel.ForgotPasswordViewModel
import com.example.final_project.presentation.state.ForgotPasswordState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {
    private val viewModel: ForgotPasswordViewModel by viewModels()
    private var isDown = false
    private var isPhoneDown = false

    @Inject
    lateinit var auth: FirebaseAuth

    override fun setUp() {}

    override fun setUpListeners() = with(binding) {
        btnGoToSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()

            if (email.isNotEmpty() && phoneNumber.isNotEmpty()) {
                root.showSnackBar(getStringResource(R.string.fill_one_field)!!)
            } else if (email.isNotEmpty()) {
                viewModel.onEvent(ForgotPasswordEvents.SendVerificationToEmail(email))
            } else if (phoneNumber.isNotEmpty()) {
                viewModel.onEvent(ForgotPasswordEvents.SendVerificationToPhoneNumber(
                    phoneNumber = phoneNumber,
                    options = providePhoneAuthOptionsBuilder(phoneNumber)
                ))
            } else {
                root.showSnackBar(getStringResource(R.string.fill_all_fields)!!)
            }
        }

        arrowIcon.setOnClickListener {
            isDown = !isDown
            if (isDown) {
                etEmailContainer.visibility = View.VISIBLE
                arrowIcon.setImageResource(R.drawable.ic_arrow_up)
            } else {
                etEmailContainer.visibility = View.GONE
                arrowIcon.setImageResource(R.drawable.ic_arrow_down)
            }
        }

        arrowIconPhone.setOnClickListener {
            isPhoneDown = !isPhoneDown
            if (isPhoneDown) {
                etPhoneContainer.visibility = View.VISIBLE
                arrowIconPhone.setImageResource(R.drawable.ic_arrow_up)
            } else {
                etPhoneContainer.visibility = View.GONE
                arrowIconPhone.setImageResource(R.drawable.ic_arrow_down)
            }
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.forgotPassFlow.collect {
                        handleForgotPasswordState(state = it)
                    }
                }

                launch {
                    viewModel.navigationEvent.collect {
                        handleNavigationEvents(event = it)
                    }
                }
            }
        }
    }

    private fun getStringResource(@StringRes stringRes: Int?): String? {
        stringRes?.let {
            return resources.getString(it)
        }
        return null
    }

    private fun handleForgotPasswordState(state: ForgotPasswordState) = with(binding) {
        if (state.isLoading) progressBar.visibility = View.VISIBLE else View.GONE

        getStringResource(state.errorMessage)?.let {
            root.showSnackBar(it)
        }
    }

    private fun handleNavigationEvents(event: ForgotPasswordViewModel.ForgotPasswordNavigationEvents) {
        when (event) {
            is ForgotPasswordViewModel.ForgotPasswordNavigationEvents.NavigateToRestorePasswordPage -> {
                binding.root.showSnackBar(getStringResource(R.string.reset_passwprd)!!)
                findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
            }

            is ForgotPasswordViewModel.ForgotPasswordNavigationEvents.NavigateToPasscodeFragment -> {
                findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToPasscodeFragment(
                    phoneNumber = null,
                    verificationId = event.verificationId,
                    isForgot = true
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