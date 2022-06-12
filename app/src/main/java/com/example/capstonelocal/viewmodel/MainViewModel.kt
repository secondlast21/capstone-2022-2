package com.example.capstonelocal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.capstonelocal.data.repository.MapsRepository

class MainViewModel(
    private val mapsRepository: MapsRepository
): ViewModel() {
    fun getNearestAmbulance(lintang: Double, bujur: Double) = mapsRepository.nearestAmbulance(lintang, bujur)

    fun getNearestHospital(lintang: Double, bujur: Double) = mapsRepository.nearestHospital(lintang, bujur)
}