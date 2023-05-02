package com.example.stassessmentproject.presentation.product_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel(){

    val state = mutableStateOf(DetailsState())

}