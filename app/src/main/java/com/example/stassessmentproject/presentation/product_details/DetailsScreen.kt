package com.example.stassessmentproject.presentation.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.stassessmentproject.R
import com.example.stassessmentproject.presentation.products.components.TopBar
import com.example.stassessmentproject.utils.Dimensions

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    state: DetailsState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(title = "Details") {
            navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Dimensions.pagePadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.name,
                style = MaterialTheme.typography.h4
            )
            val painter = rememberAsyncImagePainter(
                model = state.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_placeholder),
                error = painterResource(id = R.drawable.ic_placeholder)
            )
            Box(
                modifier = Modifier
                    .shadow(shape = MaterialTheme.shapes.medium, elevation = 2.dp)
                    .size(220.dp, 220.dp)
            ){
                Image(
                    painter = painter,
                    contentDescription = state.description,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = state.price_sign + state.price,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            Text(
                text = state.description,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify
            )
        }
    }
}