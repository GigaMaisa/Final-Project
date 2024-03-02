package com.example.final_project.presentation.screen.splash.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.databinding.FragmentSplashBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.splash.SplashNavigationEvents
import com.example.final_project.presentation.screen.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()

    override fun setUp() {
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavEvents(it)
                }
            }
        }
    }

    private fun handleNavEvents(events: SplashNavigationEvents) {
        when (events) {
            is SplashNavigationEvents.NavigateToLogIn -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToWelcomeFragment())
            }

            is SplashNavigationEvents.NavigateToHome -> {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToPlaceholderDestination())
            }

            else -> {}
        }
    }
}