package com.sanskar.jmdwebstudio.VerifyOTP

import java.io.Serializable

data class VerifyOTPResponse(
    val success: Boolean,
    val message: String,
    val user: User
): Serializable
