package com.neo.yandexpvz.model


data class RedeemRequest(
    val email: String,
    val mobile: String,
    val name: String,
    val spentCoin:String,
    val product : String,
    val productValue : String
)