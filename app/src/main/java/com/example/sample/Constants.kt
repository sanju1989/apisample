package com.example.sample


 const val BUFFER_SIZE = 1024 * 2
 const val IMAGE_DIRECTORY = "/imunim"
const val BASE_URL = "https://app.undago.com/api/"
const val secret_key="sk_D9PHvUvNjnsGq5ldDpIfEjkqPST+Zi//Up93hAsuL73HwIO6hwPVLEpdbAzQRVw4cFu+"
const val publish_key="pk_y/mOG3uhTC5DU/VL4uqkjwv5AMVwYs8APyMiNNG374PtKRUnHdbZDHdVnRpubu3GAF4umg=="
internal interface httpcodes {
    companion object {
        val STATUS_OK = 200
        val STATUS_BAD_REQUEST = 400
        val STATUS_SESSION_EXPIRED = 401
        val STATUS_PLAN_EXPIRED = 403
        val STATUS_VALIDATION_ERROR = 404
        val STATUS_SERVER_ERROR = 500
        val STATUS_UNKNOWN_ERROR = 503
        val STATUS_API_VALIDATION_ERROR = 422
        val SESSION_EXPIRED = 203
    }
}

const val DOB_FORMAT = "MM/dd/yyyy"
const val CHALLENGE_DATE_FORMAT = "yyyy-MM-dd"
const val CHALLENGE_TIME_FORMAT="h:mm a"
const val SNACK_BAR_DURATION = 2500
const val SOMETHING_WENT_WRONG = "Something went wrong please try again later!"
const val SESSION_EXPIRED =
    "Sorry, looks like you are logged in another device with the same user."
