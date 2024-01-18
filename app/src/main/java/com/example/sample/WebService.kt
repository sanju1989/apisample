package com.example.sample



import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


/**
 *  All web services are declared here
 */
@JvmSuppressWildcards
interface WebService {

    @Multipart
    @Headers("secret_key:sk_D9PHvUvNjnsGq5ldDpIfEjkqPST+Zi//Up93hAsuL73HwIO6hwPVLEpdbAzQRVw4cFu+",
            "publish_key:pk_y/mOG3uhTC5DU/VL4uqkjwv5AMVwYs8APyMiNNG374PtKRUnHdbZDHdVnRpubu3GAF4umg=="
    )
    @POST("sign-up")
    fun postData(
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone_no") phone_no: RequestBody,
        @Part("device_token") device_token: RequestBody,
        @Part("device_type") device_type: RequestBody,
        @Part("countryCode") countryCode: RequestBody,
        @Part profile_image: MultipartBody.Part?,
    ): Call<CommentResponse>
}