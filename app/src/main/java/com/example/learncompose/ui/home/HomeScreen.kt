package com.example.learncompose.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    openCategoryAction: () -> Unit,
    navController: NavHostController? = null
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    val dismissBottomSheet = {
        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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
            navController?.navigate("MyAccount")
        }) {
            Text(text = "Open MyAccount")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            navController?.navigate("Test")
        }) {
            Text(text = "Go to test screen", style = MaterialTheme.typography.bodyLarge)
        }
        Button(onClick = {
            showBottomSheet = true
        }) {
            Text(text = "Show bottom sheet")
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        dismissBottomSheet()
                    }) {
                        Text("Hide bottom sheet")
                    }
                }
            }
        }
    }
}
