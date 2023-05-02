package com.example.stassessmentproject.presentation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login")
    object ProductsScreen: Screen("products")
    object ProductDetailsScreen: Screen(
        "details/{productName}/{productDescription}/{productImage}/{productPrice}/{priceSign}"
    )
    object SplashScreen: Screen("splash")
}