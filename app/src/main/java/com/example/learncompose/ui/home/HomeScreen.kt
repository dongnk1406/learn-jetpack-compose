package com.example.learncompose.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    openCategoryAction: () -> Unit,
    openMyAccountScreen: () -> Unit,
    navController: NavHostController? = null
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            navController?.navigate("LearnCommonCompose")
        }) {
            Text(text = "Open learn common compose")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { openCategoryAction() }) {
            Text("Open Category")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
//            openMyAccountScreen()
            navController?.navigate("MyAccount")
        }) {
            Text(text = "Open MyAccount")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            navController?.navigate("Test")
        }) {
            Text(text = "Go to test screen")
        }
    }
}