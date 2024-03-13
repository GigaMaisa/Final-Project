package com.example.final_project.presentation.screen.all_restaurants

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentAllRestaurantsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.AllRestaurantsEvent
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.screen.all_restaurants.adapter.AllRestaurantsRecyclerViewAdapter
import com.example.final_project.presentation.state.AllRestaurantsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllRestaurantsFragment : BaseFragment<FragmentAllRestaurantsBinding>(FragmentAllRestaurantsBinding::inflate) {

    private val viewModel: AllRestaurantsViewModel by viewModels()
    private val allRestaurantsRecyclerAdapter = AllRestaurantsRecyclerViewAdapter()

    override fun setUp() {
        setUpRecycler()
        viewModel.onEvent(AllRestaurantsEvent.GetAllRestaurantsEvent)
    }

    override fun setUpListeners() {
        allRestaurantsRecyclerAdapter.onClick = {
            viewModel.onEvent(AllRestaurantsEvent.GoToRestaurantsDetailsEvent(restaurantId = it))
        }

        binding.btnGoBack.setOnClickListener {
            viewModel.onEvent(AllRestaurantsEvent.GoBackEvent)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.restaurantsStateFlow.collect {
                        handleState(it)
                    }
                }

                launch {
                    viewModel.uiState.collect {
                        handleUiEvent(it)
                    }
                }
            }
        }
    }

    private fun handleState(state: AllRestaurantsState) = with(state) {
        restaurants?.let {
            allRestaurantsRecyclerAdapter.submitList(it)
        }

        binding.progressBar.isVisible = isLoading

        errorMessage?.let {
            requireView().showSnackBar(resources.getString(it))
            viewModel.onEvent(AllRestaurantsEvent.UpdateErrorMessageEvent(null))
        }
    }

    private fun handleUiEvent(event: AllRestaurantsViewModel.AllRestaurantsUiEvents) {
        when(event) {
            is AllRestaurantsViewModel.AllRestaurantsUiEvents.NavigateBack -> findNavController().navigateUp()
            is AllRestaurantsViewModel.AllRestaurantsUiEvents.NavigateToRestaurantDetails ->
                findNavController().navigate(AllRestaurantsFragmentDirections.actionAllRestaurantsFragmentToRestaurantDetailsFragment(
                restaurantId = event.restaurantId
            ))
        }
    }

    private fun setUpRecycler() {
        with(binding.recyclerRestaurants) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allRestaurantsRecyclerAdapter
        }
    }

}