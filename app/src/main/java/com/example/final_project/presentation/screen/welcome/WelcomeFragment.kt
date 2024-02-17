package com.example.final_project.presentation.screen.welcome

import com.example.final_project.databinding.FragmentWelcomeBinding
import com.example.final_project.presentation.base.BaseFragment

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    override fun setUp() {

    }

    override fun setUpListeners() = with(binding) {
        btnGoToSignIn.setOnClickListener {

        }

        btnGoToSignUp.setOnClickListener {

        }
    }


    override fun setUpObservers() {

    }

}