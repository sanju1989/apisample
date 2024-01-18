package com.example.sample.repository

import android.net.Uri
import com.example.sample.ApiHelper
import com.example.sample.CommentResponse
import com.example.sample.httpcodes

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


object CommentRepository {
    private val webService = ApiHelper.createService()
    fun postData(
        successHandler: (CommentResponse) -> Unit,
        failureHandler: (String) -> Unit,
        onFailure: (Throwable) -> Unit,

        first_name: String,
        last_name: String,
        email: String,
        password: String,
        phone_no: String,
        device_token: String,
        device_type:String,
        countryCode: String,
        userimage: String,
    ) {
        var profileImg: MultipartBody.Part? = null
        val file = File(Uri.parse(userimage).path)
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        profileImg = MultipartBody.Part.createFormData("profile_image", file.name, requestFile)
        val first_name1 = first_name.toRequestBody("query".toMediaTypeOrNull())
        val last_name1 = last_name.toRequestBody("query".toMediaTypeOrNull())
        val email1 = email.toRequestBody("query".toMediaTypeOrNull())
        val password1= password.toRequestBody("query".toMediaTypeOrNull())
        val phone_no1 = phone_no.toRequestBody("query".toMediaTypeOrNull())
        val device_token1 = device_token.toRequestBody("query".toMediaTypeOrNull())
        val device_type1 = device_type.toRequestBody("query".toMediaTypeOrNull())
        val countryCode1 = countryCode.toRequestBody("query".toMediaTypeOrNull())

        webService.postData(
            first_name1,
            last_name1,
            email1,
            password1,
            phone_no1,
            device_token1,
            device_type1,
            countryCode1,
            profileImg,
        )
            .enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    response.body()?.let {
                        successHandler(it)
                    }
                    if (response.code() == 400) {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        failureHandler(jsonObj.getString("message"))
                    }
                    if (response.code() == httpcodes.STATUS_API_VALIDATION_ERROR) {
                        response.errorBody()?.let {
                            val error = ApiHelper.handleAuthenticationError(response.errorBody()!!)
                            failureHandler(error)
                        }

                    } else {
                        response.errorBody()?.let {
                            val error = ApiHelper.handleApiError(response.errorBody()!!)
                            failureHandler(error)
                        }
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    t.let {
                        onFailure(it)
                    }
                }

            })
    }
}

