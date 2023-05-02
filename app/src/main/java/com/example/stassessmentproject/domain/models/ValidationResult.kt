package com.example.stassessmentproject.domain.models

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
