package com.ukaka.stassessmentproject.data.repository

import com.example.stassessmentproject.data.remote.MakeupAPI
import com.example.stassessmentproject.data.repository.MakeupRepositoryImpl
import com.example.stassessmentproject.domain.models.MakeupProduct
import com.example.stassessmentproject.domain.repository.MakeupRepository
import com.example.stassessmentproject.utils.Resource
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class MakeupRepositoryImplTest {

    private lateinit var makeupRepository: MakeupRepository
    private lateinit var makeupAPI: MakeupAPI
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8000)
        makeupAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(MakeupAPI::class.java)
        makeupRepository = MakeupRepositoryImpl(makeupAPI)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `makeupApi parses correctly`() = runTest{
        val productsList = listOf(
            MakeupProduct(1, "", "", "", "",
                "",  "", ""),
            MakeupProduct(2, "", "", "", "",
                "",  "", ""),
            MakeupProduct(3, "", "", "", "",
                "",  "", ""),
            MakeupProduct(4, "", "", "", "",
                "",  "", ""),
            MakeupProduct(5, "", "", "", "",
                "",  "", ""),
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(productsList))
        mockWebServer.apply {
            enqueue(expectedResponse)
        }

        var actualResponse = emptyList<MakeupProduct>()
        makeupRepository.getMakeupProducts().collectLatest { result ->
            when(result) {
                is Resource.Success -> {
                    actualResponse = result.data!!
                }
                else -> {
                    emptyList<MakeupProduct>()
                }
            }
        }

        assertThat(actualResponse.count()).isEqualTo(5)
    }
}