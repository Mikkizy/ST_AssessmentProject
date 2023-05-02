package com.example.stassessmentproject.presentation.products

sealed class ProductsEvents {
    data class ShowSnackBar(val message: String): ProductsEvents()
}
