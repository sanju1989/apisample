package com.app.imunimsales.utils

import com.google.gson.annotations.SerializedName

data class ApiStatus(@SerializedName("result") val status: Boolean, @SerializedName("message") val message: String)