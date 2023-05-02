package com.example.stassessmentproject.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductColor(
    val colour_name: String?,
    val hex_value: String?
)