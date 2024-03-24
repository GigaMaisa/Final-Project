package com.example.final_project.presentation.screen.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentMapsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate) {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var map: GoogleMap? = null
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        updateLocationUi(LatLng(41.7934135, 44.8025545))
        binding.btnSelectLocation.isClickable = true
    }

    override fun setUp() {
        setUpMap()
        updateCurrentLocationUI()
        setUpAutocompleteFragment()
    }

    override fun setUpListeners() {
        binding.btnSelectLocation.setOnClickListener {
            map?.let {
                getLocationName(it.projection.visibleRegion.latLngBounds.center.latitude, it.projection.visibleRegion.latLngBounds.center.longitude)
            }
        }
    }

    override fun setUpObservers() {
    }

    private fun updateCurrentLocationUI() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        updateLocationUi(LatLng(task.result.latitude, task.result.longitude))
                    }
                } else {
                    map?.let {
                        it.uiSettings.isMyLocationButtonEnabled = false
                    }
                    requestPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                }
            }
        } else {
            requestPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
    }

    private fun updateLocationUi(latLng: LatLng) =
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))

    private fun setUpAutocompleteFragment() {
        with(binding.autocompleteFragment.getFragment<AutocompleteSupportFragment>()) {
            setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)).
            setCountries("GE").
            setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    place.latLng?.let {
                        updateLocationUi(it)
                    }
                }

                override fun onError(status: Status) {}
            })
        }
    }

    private fun getLocationName(latitude: Double, longitude: Double) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Geocoder(requireContext()).getFromLocation(latitude, longitude, 1) {
                    findNavController().navigate(
                        MapsFragmentDirections.actionMapsFragmentToDeliveryLocationFragment(
                            location = LatLng(latitude, longitude),
                            locationName = it[0].getAddressLine(0)
                        )
                    )
                }
            } else {
                val address = Geocoder(requireContext()).getFromLocation(latitude, longitude, 1)?.get(0)
                address?.let {
                    findNavController().navigate(MapsFragmentDirections.actionMapsFragmentToDeliveryLocationFragment(
                        location = LatLng(latitude, longitude),
                        locationName = it.toString()
                    ))
                }
            }
        } catch (e: Exception) {
            Log.e("LocationName", "Error getting location name: ${e.message}")
        }
    }

    private fun setUpMap() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}