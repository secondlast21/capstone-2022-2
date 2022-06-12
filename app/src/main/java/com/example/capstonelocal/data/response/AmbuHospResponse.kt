package com.example.capstonelocal.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AmbuHospResponse(
	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: List<DataItem>
) : Parcelable

@Parcelize
data class DataItem (
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("nama_ambulan")
	val namaAmbulan: String,

	@field:SerializedName("nama_driver")
	val namaDriver: String,

	@field:SerializedName("kode")
	val kode: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("jenis")
	val jenis: String,

	@field:SerializedName("tipe")
	val tipe: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("bed_avail")
	val bedAvail: Int,

	@field:SerializedName("telepon")
	val telepon: Long,

	@field:SerializedName("lintang")
	val lintang: Double,

	@field:SerializedName("bujur")
	val bujur: Double,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,
) : Parcelable
