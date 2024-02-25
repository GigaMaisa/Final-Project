package com.example.final_project.presentation.screen.home_container.fragment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentHomeContainerBinding
import com.example.final_project.presentation.base.BaseFragment

class HomeContainerFragment : BaseFragment<FragmentHomeContainerBinding>(FragmentHomeContainerBinding::inflate) {
    override fun setUp() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nested_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ -> }
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }

}