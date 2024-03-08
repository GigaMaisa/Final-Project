package com.example.final_project.presentation.screen.delivery_location_add

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.FragmentDeliveryLocationAddBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.DeliveryLocationAddEvent
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.model.delivery_location.LocationType
import com.example.final_project.presentation.screen.delivery_location_add.adapter.AddressTypeRecyclerAdapter
import com.example.final_project.presentation.screen.delivery_location_add.adapter.SpinnerLocationTypeAdapter
import com.example.final_project.presentation.state.DeliveryLocationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryLocationAddFragment :
    BaseFragment<FragmentDeliveryLocationAddBinding>(FragmentDeliveryLocationAddBinding::inflate) {

    private val viewModel: DeliveryLocationAddViewModel by viewModels()
    private val args: DeliveryLocationAddFragmentArgs by navArgs()
    private val addressAdapter = AddressTypeRecyclerAdapter()
    private val spinnerAdapter = SpinnerLocationTypeAdapter()
    private lateinit var selectedLocationType: LocationType
    override fun setUp() {
        setUpLocationName()
        setUpRecycler()
        setUpSpinner()

    }

    override fun setUpListeners() {
        setUpSubmitLocationListener()
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.deliveryLocationStateFlow.collect {
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

    private fun handleUiEvent(event: DeliveryLocationAddViewModel.DeliveryLocationAddUiEvent) {
        when (event) {
            DeliveryLocationAddViewModel.DeliveryLocationAddUiEvent.NavigateToDeliveryLocations -> findNavController().navigate(
                DeliveryLocationAddFragmentDirections.actionDeliveryLocationAddFragmentToDeliveryLocationFragment()
            )

            DeliveryLocationAddViewModel.DeliveryLocationAddUiEvent.NavigateBack -> findNavController().navigateUp()
        }
    }

    private fun setUpLocationName() {
        binding.tvAddress.text = args.locationName
    }

    private fun setUpRecycler() {
        with(binding.recyclerAddressType) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = addressAdapter.apply {
                addressAdapter.onAddressClick = {
                    viewModel.onEvent(DeliveryLocationAddEvent.SelectAddressTypeEvent(it))
                }
            }
        }
    }

    private fun setUpSpinner() {
        binding.spinnerLocationType.adapter = spinnerAdapter
        binding.spinnerLocationType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedLocationType = spinnerAdapter.items[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun handleState(state: DeliveryLocationState) = with(state) {
        addressAdapter.submitList(addressType)

        errorMessage?.let {
            requireView().showSnackBar(resources.getString(it))
            viewModel.onEvent(DeliveryLocationAddEvent.UpdateErrorMessage(null))
        }
    }

    private fun setUpSubmitLocationListener() {
        binding.btnAddAddressDetails.setOnClickListener {
            viewModel.onEvent(
                DeliveryLocationAddEvent.AddLocationEvent(
                    buildingName = binding.etBuildingName.text.toString(),
                    entrance = binding.etEntrance.text.toString(),
                    floor = binding.etFloor.text.toString(),
                    apartment = binding.etApartment.text.toString(),
                    description = binding.etDescription.text.toString(),
                    locationType = selectedLocationType,
                    addressLocation = args.location,
                    addressLocationName = args.locationName
                )
            )
        }
    }
}