package com.example.final_project.presentation.screen.delivery_location

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentDeliveryLocationBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.DeliveryLocationEvent
import com.example.final_project.presentation.screen.delivery_location.adapter.DeliveryLocationsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryLocationFragment : BaseFragment<FragmentDeliveryLocationBinding>(FragmentDeliveryLocationBinding::inflate) {

    private val viewModel: DeliveryLocationViewModel by viewModels()
    private val deliveryLocationAdapter = DeliveryLocationsRecyclerAdapter()
    override fun setUp() {
        setUpRecycler()
    }

    override fun setUpListeners() {
        binding.floatingBtnAddLocations.setOnClickListener {
            viewModel.onEvent(DeliveryLocationEvent.NavigateToMapEvent)
        }

        binding.btnGoBack.setOnClickListener {
            viewModel.onEvent(DeliveryLocationEvent.NavigateBackEvent)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.locations.collect {
                        if (it.isEmpty()) {
                            binding.tvNoItemsAlert.visibility = View.VISIBLE
                        }
                        deliveryLocationAdapter.submitList(it)
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

    private fun handleUiEvent(event: DeliveryLocationViewModel.DeliveryLocationUiEvent) {
        when(event) {
            is DeliveryLocationViewModel.DeliveryLocationUiEvent.NavigateBack -> findNavController().navigateUp()
            is DeliveryLocationViewModel.DeliveryLocationUiEvent.NavigateToMap -> findNavController().navigate(DeliveryLocationFragmentDirections.actionDeliveryLocationFragmentToMapsFragment())
        }
    }

    private fun setUpRecycler() {
        with(binding.recyclerLocations) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = deliveryLocationAdapter
        }
    }

}