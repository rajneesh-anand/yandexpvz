package com.neo.yandexpvz.model

data class Product(
    val id: Int,
    val name: String,
    val coinValue: Int,
    val description: String,
    val category: String?,
    val image: String,
    val inStock : Int
)
