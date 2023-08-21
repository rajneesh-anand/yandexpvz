package com.neo.yandexpvz.model


data class GiftCard(
    val id: Int,
    val product: String,
    val productValue: String,
    val redeemCode: String,
    val redeemStatus: String,
    val createdAt: String

)
