package com.example.final_project.presentation.screen.signup.success.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.final_project.NavGraphDirections
import com.example.final_project.R
import com.example.final_project.databinding.FragmentSignUpSuccessBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.signup.success.viewmodel.SignUpSuccessNavigationEvents
import com.example.final_project.presentation.screen.signup.success.viewmodel.SignUpSuccessViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class SignUpSuccessFragment : BaseFragment<FragmentSignUpSuccessBinding>(FragmentSignUpSuccessBinding::inflate) {
    private val viewModel: SignUpSuccessViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        btnTryOrder.setOnClickListener {
            viewModel.onUiEvent(SignUpSuccessNavigationEvents.NavigateToHomePage)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigationEvent.collect {
                        handleNavigationEvent(event = it)
                    }
                }
            }
        }
    }

    private fun handleNavigationEvent(event: SignUpSuccessNavigationEvents) {
        when (event) {
            is SignUpSuccessNavigationEvents.NavigateToHomePage -> {
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(
                    NavGraphDirections.actionGlobalToHomeFragment())
            }
        }
    }
}