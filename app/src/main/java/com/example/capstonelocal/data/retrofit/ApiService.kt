package com.example.capstonelocal.data.retrofit

import com.example.capstonelocal.data.response.AmbuHospResponse
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("short/ambulance")
    suspend fun nearestAmbulance (
        @Field("lintang") lintang: Double,
        @Field("bujur") bujur: Double
    ) : AmbuHospResponse

    @FormUrlEncoded
    @POST("short/hospital")
    suspend fun nearestHospital (
        @Field("lintang") lintang: Double,
        @Field("bujur") bujur: Double
    ) : AmbuHospResponse
}