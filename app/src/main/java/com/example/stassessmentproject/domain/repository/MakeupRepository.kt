package com.example.stassessmentproject.domain.repository

import com.example.stassessmentproject.domain.models.MakeupProduct
import com.example.stassessmentproject.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MakeupRepository {

    fun getMakeupProducts(): Flow<Resource<List<MakeupProduct>>>
}