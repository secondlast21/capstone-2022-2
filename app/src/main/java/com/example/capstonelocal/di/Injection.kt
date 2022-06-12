package com.example.capstonelocal.di

import com.example.capstonelocal.data.repository.MapsRepository
import com.example.capstonelocal.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(): MapsRepository {
        val apiService = ApiConfig.getApiService()
        return MapsRepository.getInstance(apiService)
    }
}