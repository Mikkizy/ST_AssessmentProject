package com.example.stassessmentproject.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stassessmentproject.R
import com.example.stassessmentproject.presentation.Screen
import com.example.stassessmentproject.utils.Dimensions
import com.example.stassessmentproject.utils.ValidationEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = remember {
        viewModel.state
    }.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvents.Success -> {
                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_LONG
                    ).show()

                    //Navigate to photos screen
                    navController.navigate(Screen.ProductsScreen.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimensions.pagePadding)
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(Dimensions.threeSpacers))
        Text(
            text = stringResource(id = R.string.details),
            style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(Dimensions.threeSpacers))
        Spacer(modifier = Modifier.height(Dimensions.fourSpacers))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.username,
            onValueChange = {
                viewModel.onEvent(LoginEvents.EnteredUsername(it))
            },
            isError = state.value.usernameError != null,
            placeholder = {
                Text(text = "Username")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        if (state.value.usernameError != null) {
            Text(
                text = state.value.usernameError ?: "",
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(Dimensions.fourSpacers))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
            ,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorResource(id = R.color.teal_700),
                contentColor = Color.White
            ),
            onClick = { viewModel.onEvent(LoginEvents.ClickedLoginButton) }
        ) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}