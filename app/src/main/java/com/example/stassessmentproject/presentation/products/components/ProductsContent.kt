package com.example.stassessmentproject.presentation.products.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.stassessmentproject.R
import com.example.stassessmentproject.domain.models.MakeupProduct
import com.example.stassessmentproject.utils.Dimensions

@Composable
fun ProductsContent(
    products: List<MakeupProduct>,
    onProductClick: (MakeupProduct) -> Unit
) {
    val groupedProductByBrand = products.groupBy { it.brand }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = Dimensions.pagePadding.minus(8.dp)),
        verticalArrangement = Arrangement.spacedBy(Dimensions.oneSpacer.times(0.8f))
    ) {
        item {
            Text(
                text = "Please select any item from your favorite brands.",
                style = MaterialTheme.typography.h6
            )
        }
        item { Spacer(modifier = Modifier.height(Dimensions.oneSpacer)) }
        groupedProductByBrand.forEach { (brand, makeupProducts) ->
            item {
                BrandItem(brand = brand!!, products = makeupProducts) {
                    onProductClick(it)
                }
            }
        }
    }
}

@Composable
fun BrandItem(
    brand: String,
    products: List<MakeupProduct>,
    onProductClick: (MakeupProduct) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = brand,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(Dimensions.oneSpacer))
        LazyRow(
            content = {
                items(
                items = products,
                key = {
                    it.id
                }
            ) {makeupProduct ->
                MakeupItem(
                    makeupProduct = makeupProduct,
                    onProductClick = onProductClick
                )
            }
        },
            horizontalArrangement = Arrangement.spacedBy(Dimensions.oneSpacer)
        )
        Spacer(modifier = Modifier.height(Dimensions.oneSpacer.times(1.5f)))
    }
}

@Composable
fun MakeupItem(
    makeupProduct: MakeupProduct,
    onProductClick: (MakeupProduct) -> Unit
){
    val painter = rememberAsyncImagePainter(
        model = makeupProduct.image_link,
        placeholder = painterResource(id = R.drawable.ic_placeholder),
        error = painterResource(id = R.drawable.ic_placeholder)
    )
    Box(
        modifier = Modifier
            .size(160.dp, 200.dp)
            .shadow(shape = MaterialTheme.shapes.medium, elevation = 1.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = makeupProduct.name ?: "Makeup Item",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clickable { onProductClick(makeupProduct) },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(Dimensions.oneSpacer.div(2)))
            Text(
                text = makeupProduct.name ?: "",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(Dimensions.oneSpacer.div(3)))
            Text(
                text = makeupProduct.product_type ?: "",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}