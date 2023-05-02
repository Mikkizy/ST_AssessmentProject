package com.example.stassessmentproject.presentation.products

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stassessmentproject.domain.usecases.GetMakeupProducts
import com.example.stassessmentproject.utils.Resource
import com.example.stassessmentproject.utils.USERNAME
import com.example.stassessmentproject.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getMakeupProducts: GetMakeupProducts,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ProductsEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    val username = context.dataStore.data.map {
        it[USERNAME] ?: ""
    }

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            getMakeupProducts().onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isError = true
                        )
                        _eventFlow.emit(ProductsEvents.ShowSnackBar(result.message ?: "An error occurred!"))
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            products = result.data ?: emptyList(),
                            isLoading = false,
                            isError = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}