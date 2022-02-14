package com.example.ketgomvvm.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.ketgomvvm.R
import com.example.ketgomvvm.databinding.ActivityMapsBinding
import com.example.ketgomvvm.data.datastore.ProductManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var _mMap: GoogleMap
    private lateinit var _binding: ActivityMapsBinding
    private lateinit var _fusedLocationClient: FusedLocationProviderClient
    private lateinit var _lastLocation: Location
    private lateinit var _productManager: ProductManager

    var startLatitude = FIRST_LONG_AND_LAT
    var startLongitude = FIRST_LONG_AND_LAT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMapsBinding.inflate(layoutInflater)
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        _productManager = ProductManager(applicationContext)
        setContentView(_binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun markerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
            .snippet(getAddress(currentLatLong.latitude, currentLatLong.longitude))

        markerOptions.title("$currentLatLong")
        _mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLong))
        _mMap.addMarker(markerOptions)

        _binding.btnSaveLocation.setOnClickListener {
            _binding.tvAddress.text = getAddress(currentLatLong.latitude, currentLatLong.longitude)
            lifecycleScope.launch {
                _productManager.storeProductSellingData(_binding.tvAddress.text.toString())
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        _mMap = googleMap
        _mMap.uiSettings.isZoomControlsEnabled = true
        _mMap.setOnMarkerClickListener(this)
        setupMap()
    }

    private fun setupMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_MAP
            )
            return
        }
        _mMap.isMyLocationEnabled = true
        _fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            location?.let {
                _lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                markerOnMap(currentLatLong)
                _mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, CAMERA_ZOOM))

                startLatitude = location.latitude
                startLongitude = location.longitude
            }
        }
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(lat, lng, MAX_ADDRESS_RESULT)
        return address[FIRST_ADDRESS_INDEX].getAddressLine(FIRST_ADDRESS_INDEX).toString()
    }

    override fun onMarkerClick(p0: Marker) = false

    companion object {
        private const val FIRST_ADDRESS_INDEX = 0
        private const val MAX_ADDRESS_RESULT = 1
        private const val REQUEST_CODE_MAP = 1
        private const val CAMERA_ZOOM = 12f
        private const val FIRST_LONG_AND_LAT = 0.0
    }
}


