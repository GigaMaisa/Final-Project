package com.example.final_project.presentation.screen.reset_password.success.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.final_project.NavGraphDirections
import com.example.final_project.R
import com.example.final_project.databinding.FragmentResetSuccessBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.reset_password.success.viewmodel.ResetSuccessPageNavigationEvents
import com.example.final_project.presentation.screen.reset_password.success.viewmodel.ResetSuccessViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetSuccessFragment : BaseFragment<FragmentResetSuccessBinding>(FragmentResetSuccessBinding::inflate) {
    private val viewModel: ResetSuccessViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        btnLogin.setOnClickListener {
            viewModel.onUiEvent(ResetSuccessPageNavigationEvents.NavigateToLogin)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigationEvent.collect {
                        when (it) {
                            is ResetSuccessPageNavigationEvents.NavigateToLogin -> {
                                requireActivity().findNavController(R.id.nav_host_fragment).navigate(
                                    NavGraphDirections.actionGlobalToWelcomeFragment())
                            }
                        }
                    }
                }
            }
        }
    }
}