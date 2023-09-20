package com.neo.yandexpvz.model

data class Highlight(
    val text: String,
    val data: String,
    val onClick: (data: String) -> Unit
)