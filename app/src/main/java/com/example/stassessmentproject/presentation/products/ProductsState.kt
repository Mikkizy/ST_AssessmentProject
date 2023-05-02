package com.example.stassessmentproject.presentation.products

import com.example.stassessmentproject.domain.models.MakeupProduct

data class ProductsState(
    val products: List<MakeupProduct> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)