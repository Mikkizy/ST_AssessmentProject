package com.example.stassessmentproject.domain.models

data class MakeupProduct(
    val id: Int,
    val brand: String?,
    val name: String?,
    val description: String?,
    val price: String?,
    val price_sign: String?,
    val image_link: String?,
    val product_type: String?
)
