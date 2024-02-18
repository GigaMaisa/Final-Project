package com.example.final_project.presentation.screen.passcode

import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.FragmentPasscodeBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.PasscodeEvent
import com.example.final_project.presentation.screen.passcode.adapter.PasscodeRecyclerViewAdapter
import com.example.final_project.presentation.state.PasscodeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasscodeFragment : BaseFragment<FragmentPasscodeBinding>(FragmentPasscodeBinding::inflate) {

    private val viewModel: PasscodeViewModel by viewModels()
    private val passcodeAdapter = PasscodeRecyclerViewAdapter()
    override fun setUp() {
        with(binding.recyclerViewPasscode) {
            layoutManager = object : LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false) { override fun canScrollHorizontally() = false }
            adapter = passcodeAdapter
        }
    }

    override fun setUpListeners() {
        passcodeAdapter.onTextChangeListener = {
            viewModel.onEvent(PasscodeEvent.ChangeTextInputEvent(it))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passcodeStateFlow.collect {
                    handleState(it)
                }
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