package com.example.learncompose.ui.catalog.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(openProductDetail: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Category Screen")
        Spacer(modifier = Modifier.height(24.dp))
        var productId by remember {
            mutableStateOf("")
        }

        OutlinedTextField(
            value = productId, onValueChange = {
                productId = it
            }, modifier = Modifier.fillMaxWidth(), textStyle = TextStyle(
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { openProductDetail(productId) }) {
            Text("Open Product Detail")
        }
    }
}