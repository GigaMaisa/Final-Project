package com.example.final_project.presentation.screen.bottom_nav_container

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentBottomNavContainerBinding
import com.example.final_project.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomNavContainerFragment :
    BaseFragment<FragmentBottomNavContainerBinding>(FragmentBottomNavContainerBinding::inflate) {

    private val viewModel: BottomNavContainerViewModel by viewModels()
    override fun setUp() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nested_bottom_nav_graph_container) as NavHostFragment
        val navController = navHostFragment.navController
        getToChatFragment()
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ -> }
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartItemsNumber.collect {
                    d("showItemsNumber", it.toString())
                    if (it > 0) {
                        binding.bottomNavigationView.getOrCreateBadge(R.id.cartPage).number = it
                    }else{
                        binding.bottomNavigationView.removeBadge(R.id.cartPage)
                    }
                }
            }
        }
    }

    private fun getToChatFragment() = with(requireActivity()) {
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val imageUrl = intent.getStringExtra("imageUrl")
        id?.let { userId ->
            name?.let { fullName ->
                findNavController().navigate(
                    BottomNavContainerFragmentDirections.actionBottomNavPlaceHolderToChatFragment(
                        userId,
                        fullName,
                        imageUrl
                    )
                )
                intent.removeExtra("id")
            }
        }
    }

}