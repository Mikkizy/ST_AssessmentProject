package com.ukaka.stassessmentproject.domain.usecases

import com.example.stassessmentproject.domain.usecases.ValidateUsername
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateUsernameTest {
    private lateinit var validateUsername: ValidateUsername

    @Before
    fun setUp() {
        validateUsername = ValidateUsername()
    }

    @Test
    fun blankEmailReturnsError() {
        val result = validateUsername("")
        assertThat(result.successful).isFalse()
    }
}