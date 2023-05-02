package com.example.stassessmentproject.presentation.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stassessmentproject.R
import com.example.stassessmentproject.presentation.Screen
import com.example.stassessmentproject.presentation.products.components.ProductsContent
import com.example.stassessmentproject.presentation.products.components.TopBar
import kotlinx.coroutines.flow.collectLatest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = remember {
        viewModel.state
    }.collectAsState()

    val productsList = state.value.products

    val scaffoldState = rememberScaffoldState()

    val username by viewModel.username.collectAsState(initial = "")

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ProductsEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(title = "Hi $username!") {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            ProductsContent(products = productsList) { product ->
                val encodedUrl = URLEncoder.encode(product.image_link, StandardCharsets.UTF_8.toString())
                navController.navigate(Screen.ProductDetailsScreen.route
                    .replace("{productName}", product.name ?: "N/A")
                    .replace("{productDescription}", product.description ?: "N/A")
                    .replace("{productImage}", encodedUrl ?: "N/A")
                    .replace("{productPrice}", product.price ?: "N/A")
                    .replace("{priceSign}", product.price_sign ?: "$")
                )
            }
            if (state.value.isLoading) {
                CircularProgressIndicator()
            }
            if (state.value.isError) {
                Column {
                    Text(
                        text = stringResource(id = R.string.error),
                        color = Color.Red,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.loadProducts() },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.teal_700),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}