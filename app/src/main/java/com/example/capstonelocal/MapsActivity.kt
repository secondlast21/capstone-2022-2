package com.example.capstonelocal

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstonelocal.data.StateResult
import com.example.capstonelocal.data.response.DataItem
import com.example.capstonelocal.databinding.ActivityMapsBinding
import com.example.capstonelocal.viewmodel.MainViewModel
import com.example.capstonelocal.viewmodelfactory.ViewModelFactory
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    private var listAmbulance: List<DataItem> = emptyList()
    private var listHospital: List<DataItem> = emptyList()
    lateinit var locationManager: LocationManager
    private var ambuLat: Double = 0.0
    private var ambuLng: Double = 0.0
    private var hospiLat: Double = 0.0
    private var hospiLng: Double = 0.0
    private var userLat: Double = 0.0
    private var userLng: Double = 0.0
    private var ambuPhone: Int = 0
    private var hospiPhone: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ambulatCreate", ambuLat.toString())
        Log.d("ambuLngCreate", ambuLng.toString())

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.progressBar.visibility = View.INVISIBLE

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getMyLocation()
        getCurrentLocation()

        Log.d("tempLat", userLat.toString())
        Log.d("tempLng", userLng.toString())

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

       binding.btnSearch.setOnClickListener {
           mMap.clear()
           getAmbulance(userLat, userLng)
           getHospital(userLat, userLng)

           Log.d("btnhospilat", hospiLat.toString())
           Log.d("btnhospilng", hospiLng.toString())

           Log.d("btnambulat", ambuLat.toString())
           Log.d("btnambulng", ambuLng.toString())

           Log.d("btnuserlat", userLat.toString())
           Log.d("btnuserlng", userLng.toString())

           val ambulanceLoc = LatLng(ambuLat, ambuLng)
           val hospitalLoc = LatLng(hospiLat, hospiLng)

           mMap.addMarker(MarkerOptions().position(ambulanceLoc).title("Ambulance"))
           mMap.addMarker(MarkerOptions().position(hospitalLoc).title("Hospital"))

           Toast.makeText(this, "Yey, Kamu dapat Ambulans", Toast.LENGTH_SHORT).show()
       }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

   private fun getCurrentLocation() {
       fusedLocationClient.lastLocation
           .addOnSuccessListener { location : android.location.Location? ->
               userLat = location!!.latitude
               userLng = location.longitude
           }
   }

    private fun getAmbulance(lintang: Double, bujur: Double) {
        mainViewModel.getNearestAmbulance(userLat!!, userLng!!).observe(this) { ambuLocate ->
            if (ambuLocate != null) {
                when (ambuLocate) {
                    is StateResult.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is StateResult.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, "Gagal Mencari", Toast.LENGTH_SHORT).show()
                    }
                    is StateResult.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        listAmbulance = ambuLocate.data
                        binding.nearestAmbulanceLocation.text = listAmbulance[0].alamat
                        binding.ambulanceName.text = listAmbulance[0].namaAmbulan
                        binding.ambulancePhone.text = listAmbulance[0].telepon.toString()
                        ambuLat = listAmbulance[0].lintang
                        ambuLng = listAmbulance[0].bujur
                        Log.d("listAmbulance", listAmbulance.toString())
                        Log.d("AmbuLatApi", ambuLat.toString())
                        Log.d("AmbuLngApi", ambuLng.toString())
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getHospital(lintang: Double, bujur: Double) {
        mainViewModel.getNearestHospital(userLat, userLng).observe(this) { hospiLocate ->
            if (hospiLocate != null) {
                when (hospiLocate) {
                    is StateResult.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is StateResult.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, "Gagal Mencari", Toast.LENGTH_SHORT).show()
                    }
                    is StateResult.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        listHospital = hospiLocate.data
                        binding.nearestAddress.text = listHospital[0].alamat
                        binding.hospiName.text = listHospital[0].nama
                        binding.hospitalPhone.text = listHospital[0].telepon.toString()
                        val availBed = listHospital[0].bedAvail
                        binding.bedAvailable.text = "Bed yang tersedia : $availBed"
                        hospiLat = listHospital[0].lintang
                        hospiLng = listHospital[0].bujur
                        Log.d("listHospital", listHospital.toString())
                        Log.d("HospiLat", hospiLat.toString())
                        Log.d("HospiLng", hospiLng.toString())
                    }
                    else -> {}
                }
            }
        }
    }
}
