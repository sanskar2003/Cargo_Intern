package com.sanskar.jmdwebstudio.SignUp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SignUpService {
    @GET("send-otp")
    fun getOtp(
        @Query("email") email: String
    ): Call<SignUpResponse>
}
