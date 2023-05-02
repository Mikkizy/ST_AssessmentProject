package com.example.stassessmentproject.domain.usecases

import com.example.stassessmentproject.domain.models.MakeupProduct
import com.example.stassessmentproject.domain.repository.MakeupRepository
import com.example.stassessmentproject.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMakeupProducts @Inject constructor(
    private val repository: MakeupRepository
) {
    operator fun invoke(): Flow<Resource<List<MakeupProduct>>> {
        return repository.getMakeupProducts()
    }
}