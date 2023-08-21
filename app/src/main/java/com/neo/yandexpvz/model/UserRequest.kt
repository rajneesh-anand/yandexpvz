package com.neo.yandexpvz.model

data class UserRequest(
    val email: String,
    val password: String,
    val mobile: String,
    val name: String,
    val fcmToken :String?
)