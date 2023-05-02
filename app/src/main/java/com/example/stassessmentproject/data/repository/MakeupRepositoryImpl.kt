package com.example.stassessmentproject.data.repository

import com.example.stassessmentproject.data.remote.MakeupAPI
import com.example.stassessmentproject.domain.models.MakeupProduct
import com.example.stassessmentproject.domain.repository.MakeupRepository
import com.example.stassessmentproject.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MakeupRepositoryImpl @Inject constructor(
    private val makeupAPI: MakeupAPI
): MakeupRepository {
    override fun getMakeupProducts(): Flow<Resource<List<MakeupProduct>>> = flow {
        emit(Resource.Loading())
        delay(2000L)
        try {
            val response = makeupAPI.getMakeupProducts()
            if (response.isSuccessful) {
                response.body()?.let { products ->
                    return@let emit(Resource.Success(products.map { makeupDTOItem ->
                        makeupDTOItem.toMakeupProduct()
                    }))
                } ?:  emit( Resource.Error("An unknown error occurred!"))
            } else {
                emit( Resource.Error("Couldn't fetch data, try again!"))
            }
        } catch (e: Exception) {
            emit( Resource.Error("An error occurred! Check Internet Connection"))
        }
    }
}