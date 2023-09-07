package com.sanskar.jmdwebstudio.VerifyOTP

import java.io.Serializable

data class User(
    val id: String,
    val email: String,
    val isAdmin: Boolean,
    val isActivate: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val v: Int
): Serializable
