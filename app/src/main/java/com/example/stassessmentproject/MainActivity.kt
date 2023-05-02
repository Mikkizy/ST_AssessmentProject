package com.example.stassessmentproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stassessmentproject.presentation.Screen
import com.example.stassessmentproject.presentation.login.LoginScreen
import com.example.stassessmentproject.presentation.product_details.DetailsViewModel
import com.example.stassessmentproject.presentation.product_details.ProductDetailsScreen
import com.example.stassessmentproject.presentation.products.ProductsScreen
import com.example.stassessmentproject.presentation.splash.SplashScreen
import com.example.stassessmentproject.ui.theme.STAssessmentProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            STAssessmentProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(route = Screen.SplashScreen.route){
                            SplashScreen(onSplashFinished = {navController.navigate(it.route)})
                        }
                        composable(route = Screen.LoginScreen.route){
                            LoginScreen(navController = navController)
                        }
                        composable(route = Screen.ProductsScreen.route){
                            ProductsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ProductDetailsScreen.route,
                            arguments = listOf(
                                navArgument("productName") {
                                    type = NavType.StringType
                                },
                                navArgument("productDescription") {
                                    type = NavType.StringType
                                },
                                navArgument("productImage") {
                                    type = NavType.StringType
                                },
                                navArgument("productPrice") {
                                    type = NavType.StringType
                                },
                                navArgument("priceSign") {
                                    type = NavType.StringType
                                }
                            )
                        ){
                            val detailsViewModel by viewModels<DetailsViewModel>()
                            val productName = it.arguments?.getString("productName")
                            val productDescription = it.arguments?.getString("productDescription")
                            val productImage = it.arguments?.getString("productImage")
                            val productPrice = it.arguments?.getString("productPrice")
                            val priceSign = it.arguments?.getString("priceSign")
                            val state = remember {
                                detailsViewModel.state.value.copy(
                                    name = productName!!,
                                    description = productDescription!!,
                                    imageUrl = productImage!!,
                                    price = productPrice!!,
                                    price_sign = priceSign!!
                                )
                            }
                            ProductDetailsScreen(navController = navController, state)
                        }
                    }
                }
            }
        }
    }
}