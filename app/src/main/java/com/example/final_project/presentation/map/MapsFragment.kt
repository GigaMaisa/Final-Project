package com.example.final_project.presentation.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.final_project.R
import com.example.final_project.databinding.FragmentMapsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
    }

    override fun setUp() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        updateCurrentLocationUI()
    }

    override fun setUpListeners() {
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
//                        viewModel.onEvent(MapsEvent.ChangeLocationEvent(LatLng(task.result.latitude, task.result.longitude)))
                        updateLocationUi(LatLng(task.result.latitude, task.result.longitude))
                    }
                } else {
                    map.uiSettings.isMyLocationButtonEnabled = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                    }
//                    viewModel.onEvent(MapsEvent.ChangeErrorMessage(task.exception?.message.toString()))
                }
            }
        }
    }

    private fun updateLocationUi(latLng: LatLng) = with(map) {
        clear()
        moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
        addMarker(MarkerOptions().position(latLng))
    }

}