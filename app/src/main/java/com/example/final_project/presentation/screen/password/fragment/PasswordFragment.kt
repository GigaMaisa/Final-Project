package com.example.final_project.presentation.screen.password.fragment

import androidx.fragment.app.viewModels
import com.example.final_project.databinding.FragmentPasswordBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.password.viewmodel.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate) {
    private val viewModel: PasswordViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() {

    }

    override fun setUpObservers() {

    }

}