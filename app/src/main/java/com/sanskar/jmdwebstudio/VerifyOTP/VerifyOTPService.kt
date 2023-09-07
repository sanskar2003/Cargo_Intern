package com.sanskar.jmdwebstudio.VerifyOTP

import com.sanskar.jmdwebstudio.SignUp.SignUpResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VerifyOTPService {
    @GET("verify-otp")
    fun verifyOTP(
        @Query("email") email: String,
        @Query("hash") hash: String,
        @Query("otp") otp: String
    ): Call<VerifyOTPResponse>
}
