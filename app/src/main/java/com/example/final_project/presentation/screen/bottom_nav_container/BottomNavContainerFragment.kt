package com.example.final_project.presentation.screen.bottom_nav_container

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentBottomNavContainerBinding
import com.example.final_project.presentation.base.BaseFragment

class BottomNavContainerFragment : BaseFragment<FragmentBottomNavContainerBinding>(FragmentBottomNavContainerBinding::inflate) {
    override fun setUp() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nested_bottom_nav_graph_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ -> }
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }

}