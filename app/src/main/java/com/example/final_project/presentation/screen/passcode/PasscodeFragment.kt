package com.example.final_project.presentation.screen.passcode

import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.FragmentPasscodeBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.PasscodeEvent
import com.example.final_project.presentation.screen.passcode.adapter.PasscodeRecyclerViewAdapter
import com.example.final_project.presentation.state.PasscodeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasscodeFragment : BaseFragment<FragmentPasscodeBinding>(FragmentPasscodeBinding::inflate) {
    private val viewModel: PasscodeViewModel by viewModels()
    private val passcodeAdapter = PasscodeRecyclerViewAdapter()
    private val args: PasscodeFragmentArgs by navArgs()

    override fun setUp() {
        with(binding.recyclerViewPasscode) {
            layoutManager = object : LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false) { override fun canScrollHorizontally() = false }
            adapter = passcodeAdapter
        }
    }

    override fun setUpListeners() = with(binding) {
        passcodeAdapter.onTextChangeListener = {
            viewModel.onEvent(PasscodeEvent.ChangeTextInputEvent(it))
        }

        btnGoBack.setOnClickListener {
            viewModel.onUiEvent(PasscodeNavigationEvents.NavigateBack)
        }

        btnNext.setOnClickListener {
            viewModel.onUiEvent(PasscodeNavigationEvents.NavigateToSignUpCredentialsPage(args.phoneNumber))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.passcodeStateFlow.collect {
                        handleState(it)
                    }
                }

                launch {
                    viewModel.navigationEvent.collect {
                        handleNavigationState(state = it)
                    }
                }
            }
        }
    }

    private fun handleNavigationState(state: PasscodeNavigationEvents) {
        when (state) {
            is PasscodeNavigationEvents.NavigateBack -> {
                findNavController().navigateUp()
            }

            is PasscodeNavigationEvents.NavigateToSignUpCredentialsPage -> {
                findNavController().navigate(PasscodeFragmentDirections.actionPasscodeFragmentToSignUpCredentialsFragment(state.phoneNumber))
            }
        }
    }

    private fun handleState(state: PasscodeState) {
        passcodeAdapter.submitList(state.passcode)

        with(binding.tvResponseMessage) {
            state.errorMessage?.let {
                text = handleStringResource(it)
                setTextColor(Color.RED)
                passcodeAdapter.notifyItemChanged(5)
                viewModel.onEvent(PasscodeEvent.ResetPasscode)
            }

            state.successMessage?.let {
                text = handleStringResource(it)
                binding.btnNext.isClickable = true
                binding.btnNext.setBackgroundResource(R.drawable.button_background)
                setTextColor(Color.GREEN)
            }
        }
    }

    private fun handleStringResource(stringResource: Int): String {
        return resources.getString(stringResource)
    }
}