package com.example.stassessmentproject.domain.usecases

import com.example.stassessmentproject.domain.models.ValidationResult

class ValidateUsername {

    operator fun invoke(username: String): ValidationResult {

        return if (username.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "Username cannot be empty!"
            )
        } else ValidationResult(
            successful = true
        )
    }
}