package com.sanskar.jmdwebstudio.SignUp

import java.io.Serializable

data class SignUpResponse(
    val success: Boolean,
    val message: String,
    val hash: String
): Serializable
