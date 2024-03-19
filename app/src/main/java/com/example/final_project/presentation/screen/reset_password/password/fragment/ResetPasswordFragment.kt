package com.example.final_project.presentation.screen.reset_password.password.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentResetPasswordBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.forgot_pass.UpdatePasswordEvents
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.screen.reset_password.password.viewmodel.ResetPasswordNavigationEvents
import com.example.final_project.presentation.screen.reset_password.password.viewmodel.ResetPasswordViewModel
import com.example.final_project.presentation.state.ResetPasswordState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {
    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        btnGoToLogin.setOnClickListener {
            viewModel.onEvent(UpdatePasswordEvents.UpdatePassword(
                etPassword.text.toString(),
                etRepeatPassword.text.toString()
            ))
        }

        btnGoBack.setOnClickListener {
            viewModel.onUiEvent(ResetPasswordNavigationEvents.NavigateBack)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.resetPasswordState.collect {
                        handleResetPasswordState(state = it)
                    }
                }

                launch {
                    viewModel.navigationEvent.collect {
                        handleNavigationEvents(it)
                    }
                }
            }
        }
    }

    private fun handleResetPasswordState(state: ResetPasswordState) = with(state) {
        binding.progressBar.isVisible = isLoading

        errorMessage?.let {
            requireView().showSnackBar(resources.getString(it))
        }
    }

    private fun handleNavigationEvents(event: ResetPasswordNavigationEvents) {
        when (event) {
            is ResetPasswordNavigationEvents.NavigateToSuccessResetPage -> {
                findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToResetSuccessFragment())
            }
            is ResetPasswordNavigationEvents.NavigateBack -> findNavController().navigateUp()
        }
    }
}