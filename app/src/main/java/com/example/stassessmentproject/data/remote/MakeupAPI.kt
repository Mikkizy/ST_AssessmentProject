package com.example.stassessmentproject.data.remote

import com.example.stassessmentproject.data.remote.dto.MakeupDTOItem
import retrofit2.Response
import retrofit2.http.GET

interface MakeupAPI {
    @GET("api/v1/products.json")
    suspend fun getMakeupProducts(): Response<List<MakeupDTOItem>>
}