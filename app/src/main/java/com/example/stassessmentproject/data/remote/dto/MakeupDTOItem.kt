package com.example.stassessmentproject.data.remote.dto

import com.example.stassessmentproject.domain.models.MakeupProduct
import kotlinx.serialization.Serializable

@Serializable
data class MakeupDTOItem(
    val api_featured_image: String?,
    val brand: String?,
    val category: String?,
    val created_at: String?,
    val currency: String?,
    val description: String?,
    val id: Int,
    val image_link: String?,
    val name: String?,
    val price: String?,
    val price_sign: String?,
    val product_api_url: String?,
    val product_colors: List<ProductColor?>,
    val product_link: String?,
    val product_type: String?,
    val rating: Double?,
    val tag_list: List<String?>,
    val updated_at: String?,
    val website_link: String?
) {
    fun toMakeupProduct(): MakeupProduct {
        return MakeupProduct(
            id = id,
            brand = brand,
            name = name,
            description = description,
            price = price,
            price_sign = price_sign,
            image_link = image_link,
            product_type = product_type
        )
    }
}