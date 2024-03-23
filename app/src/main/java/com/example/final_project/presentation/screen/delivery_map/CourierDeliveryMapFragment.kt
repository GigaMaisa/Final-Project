package com.example.final_project.presentation.screen.delivery_map

import android.Manifest
import android.app.Notification
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkBuilder
import com.example.final_project.R
import com.example.final_project.databinding.FragmentCourierDeliveryMapBinding
import com.example.final_project.presentation.base.BaseFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CourierDeliveryMapFragment : BaseFragment<FragmentCourierDeliveryMapBinding>(FragmentCourierDeliveryMapBinding::inflate)  {

    private val viewModel: CourierDeliveryMapViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap


    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        updateLocationUi(LatLng(41.7934135, 44.8025545))
    }

    override fun setUp() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        val notification = buildNotification()
        val notificationManager = NotificationManagerCompat.from(requireContext())
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(1, notification)
    }

    override fun setUpListeners() {}

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.directionStateFlow.collect {state->
                    state.direction?.let {
                        state.courierLocation?.let {courierLocation->
                            val path = PolyUtil.decode(it.routes[0].overview_polyline.points)
                            val polylineOptions = PolylineOptions()
                                .width(10f)
                                .color(Color.RED)
                                .addAll(path)

                            val bitmap = getDrawable(requireContext(), R.drawable.ic_delivery)!!.toBitmap(80, 80)
                            val markerOptions = MarkerOptions()
                                .position(courierLocation)
                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                .title("Delivery Location")
                                .snippet("Delivery Details")
                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))

                            mMap.clear()
                            mMap.addMarker(markerOptions)
                            mMap.addPolyline(polylineOptions)
                        }
                    }
                }
            }
        }
    }

    private fun buildNotification(): Notification {
        val navigationIntent = NavDeepLinkBuilder(requireContext())
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.courierDeliveryMapFragment)
            .createPendingIntent()

        val notificationBuilder = NotificationCompat.Builder(requireContext(), "deliveryNotificationChannel")
            .setContentTitle("Delivery in Progress")
            .setContentText("Delivering to...")
            .setSmallIcon(R.drawable.ic_delivery)
            .setContentIntent(navigationIntent)
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_LOW)

        return notificationBuilder.build()
    }

    private fun updateLocationUi(latLng: LatLng) = with(mMap) {
        moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
    }
}