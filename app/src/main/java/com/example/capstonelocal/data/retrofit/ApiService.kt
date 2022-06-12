package com.example.capstonelocal.data.retrofit

import com.example.capstonelocal.data.response.AmbuHospResponse
import com.example.capstonelocal.data.response.DataItem
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback

//interface ApiService {
//    @FormUrlEncoded
//    @POST("short/ambulance")
//    fun nearestAmbulance (
//        @Field("lintang") lintang: Double,
//        @Field("bujur") bujur: Double
//    ) : Call<List<DataItem>>
//
//    @FormUrlEncoded
//    @POST("short/hospital")
//    fun nearestHospital (
//        @Field("lintang") lintang: Double,
//        @Field("bujur") bujur: Double
//    ) : Call<List<DataItem>>
//}

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