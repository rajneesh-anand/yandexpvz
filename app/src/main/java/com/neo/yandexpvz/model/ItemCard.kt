package com.neo.yandexpvz.model


data class ItemCard(
    val id: Int,
    val name: String,
    val price: Int,
    val salePrice: Int,
    val discount: Int,
    val image: String,
    val marketPlace: String,
    val inStock: String,
    val link: String
)
