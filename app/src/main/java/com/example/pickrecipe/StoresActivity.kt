package com.example.pickrecipe

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.android.synthetic.main.activity_stores.*

//This feature was based on this tutorial: https://developers.google.com/codelabs/maps-platform/location-places-android#10

class StoresActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mPlacesClient: PlacesClient
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    private val mDefaultLocation = LatLng(-34.0, 151.0)
    private val DEFAULT_ZOOM = 15
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private var mLocationPermissionGranted = false

    private lateinit var mLastKnownLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stores)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(applicationContext, apiKey)
        mPlacesClient = Places.createClient(this)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        buttonGetLocation.setOnClickListener {
            pickCurrentPlace()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        getLocationPermission()
    }

    @SuppressLint("MissingPermission")
    fun getStoresNearMe() {

        // Use fields to define the data types to return.
        val placeFields = listOf(
            Place.Field.NAME, Place.Field.ADDRESS,
            Place.Field.LAT_LNG, Place.Field.TYPES
        )

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        val request = FindCurrentPlaceRequest.builder(placeFields).build()
        val placeResponse = mPlacesClient.findCurrentPlace(request)

        placeResponse.addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                val response = task.result
                val placeLikelihood = response?.placeLikelihoods

                if (placeLikelihood != null) {
                    for (placeLikelihood in placeLikelihood) {
                        val currPlace = placeLikelihood.place
                        val currLatLng = currPlace.latLng
                        mMap.addMarker(currLatLng?.let { MarkerOptions().position(it).title("Grocery Store") })
                    }


                }
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    Log.e("Stores Activity", "Place not found: " + exception.statusCode)
                }
            }
        }
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    mLocationPermissionGranted = true
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient.lastLocation
                locationResult.addOnSuccessListener(this) { location ->
                    if (location != null) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = location
                        Log.d("Stores Activity", "Latitude: " + mLastKnownLocation.latitude)
                        Log.d("Stores Activity", "Longitude: " + mLastKnownLocation.longitude)
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    mLastKnownLocation.latitude,
                                    mLastKnownLocation.longitude
                                ), DEFAULT_ZOOM.toFloat()
                            )
                        )
                    } else {
                        Log.d("Stores Activity", "Current location is null. Using defaults.")
                        mMap.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                    }
                    getStoresNearMe()
                }
            }
        } catch (e: Exception) {
            Log.e("Exception: %s", e.message!!)
        }
    }

    private fun pickCurrentPlace() {
        if (mLocationPermissionGranted) {
            getDeviceLocation()
        } else {
            // The user has not granted permission.
            Log.i("Stores Activity", "The user did not grant location permission.")

            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(
                MarkerOptions()
                    .title("Default Location")
                    .position(mDefaultLocation)
                    .snippet("No places found because location permission is disabled.")
            )

            // Prompt the user for permission.
            getLocationPermission()
        }
    }
}