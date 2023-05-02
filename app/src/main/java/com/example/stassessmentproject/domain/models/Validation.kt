package com.example.stassessmentproject.domain.models

import com.example.stassessmentproject.domain.usecases.ValidateUsername

data class Validation(
    val validateUsername: ValidateUsername
)
