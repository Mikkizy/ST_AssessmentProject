package com.example.stassessmentproject.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stassessmentproject.R
import com.example.stassessmentproject.presentation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onSplashFinished: (nextDestination: Screen) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val isAppLaunchedBefore by splashViewModel.isAppLaunchedBefore
            .collectAsState(initial = false)

        LaunchedEffect(key1 = Unit) {
            delay(3000)
            if (isAppLaunchedBefore) {
                onSplashFinished(Screen.ProductsScreen)
            } else {
                /** Not launched before so we should navigate to Login screen */
                onSplashFinished(Screen.LoginScreen)
            }
        }
        val appName = stringResource(id = R.string.app_name)
        Text(
            /** Manipulate app's name with a different text styles */
            text = buildAnnotatedString {
                append(appName.take(4))
                withStyle(
                    style = MaterialTheme.typography.h1
                        .copy(
                            fontSize = 64.sp,
                            color = MaterialTheme.colors.primary,
                            fontFamily = FontFamily.Cursive,
                        ).toSpanStyle(),
                ){
                    append(appName.takeLast(2))
                }
            },
            style = MaterialTheme.typography.h1
                .copy(
                    fontSize = 64.sp,
                    color = MaterialTheme.colors.secondary,
                    fontFamily = FontFamily.Cursive,
                ),
        )
    }
}