package com.example.final_project.presentation.screen.forgot_password

import androidx.fragment.app.viewModels
import com.example.final_project.R
import com.example.final_project.databinding.FragmentForgotPasswordBinding
import com.example.final_project.presentation.base.BaseFragment

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private val viewModel: ForgotPasswordViewModel by viewModels()
    override fun setUp() {
        binding.tvSmsNumber.text = resources.getText(R.string.dot).toString().plus("4569")
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }


}