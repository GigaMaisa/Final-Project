package com.example.final_project.presentation.screen.delivery_location

import android.util.Log.d
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.FragmentDeliveryLocationBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.DeliveryLocationEvent
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.model.delivery_location.LocationType
import com.example.final_project.presentation.screen.delivery_location.adapter.AddressTypeRecyclerAdapter
import com.example.final_project.presentation.screen.delivery_location.adapter.SpinnerLocationTypeAdapter
import com.example.final_project.presentation.state.DeliveryLocationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryLocationFragment :
    BaseFragment<FragmentDeliveryLocationBinding>(FragmentDeliveryLocationBinding::inflate) {

    private val viewModel: DeliveryLocationViewModel by viewModels()
    private val args: DeliveryLocationFragmentArgs by navArgs()
    private val addressAdapter = AddressTypeRecyclerAdapter()
    private val spinnerAdapter = SpinnerLocationTypeAdapter()
    private lateinit var selectedLocationType: LocationType
    override fun setUp() {
        setUpLocationName()
        setUpRecycler()
        setUpSpinner()
        setUpSubmitLocationListener()
    }

    override fun setUpListeners() {
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
                    viewModel.locations().collect {
                        d("locations", it.toString())
                    }
                }

            }
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
                    viewModel.onEvent(DeliveryLocationEvent.SelectAddressTypeEvent(it))
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
            viewModel.onEvent(DeliveryLocationEvent.UpdateErrorMessage(null))
        }
    }

    private fun setUpSubmitLocationListener() {
        binding.btnAddAddressDetails.setOnClickListener {
            viewModel.onEvent(DeliveryLocationEvent.AddLocationEvent(
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