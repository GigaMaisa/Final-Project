package com.example.final_project.presentation.screen.home_container.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.final_project.NestedHomeNavGraphDirections
import com.example.final_project.R
import com.example.final_project.databinding.FragmentHomeContainerBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.home_container.viewmodel.HomeContainerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeContainerFragment : BaseFragment<FragmentHomeContainerBinding>(FragmentHomeContainerBinding::inflate) {
    private val viewModel: HomeContainerViewModel by viewModels()

    override fun setUp() {

    }

    override fun setUpListeners() {

    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.defaultLocationFlow.collect  {
                        if (it == null) {
                            requireActivity().findNavController(R.id.nested_nav_host_fragment).navigate(NestedHomeNavGraphDirections.actionGlobalToLocationsFragment())
                        }
                    }
                }
            }
        }
    }
}