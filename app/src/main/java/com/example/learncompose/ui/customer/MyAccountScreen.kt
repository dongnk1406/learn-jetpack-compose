package com.example.learncompose.ui.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAccountScreen(navController: NavController, openAddressScreen: (String?) -> Unit) {
    var addressId by remember { mutableStateOf("") }

    val newAddressId =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("new_address_id")
            ?.observeAsState()
    newAddressId?.value?.let {
        addressId = it
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text("My Account ")
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            openAddressScreen(null)
        }) {
            Text("Add address")
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = addressId, onValueChange = {
            addressId = it
        })
        Spacer(modifier = Modifier.height(6.dp))
        Button(onClick = {
            openAddressScreen(addressId)
        }) {
            Text("Edit Address")
        }
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }
        Button(onClick = {
            navController.popBackStack("Home", inclusive = false, saveState = true)
        }) {
            Text("Back to specific screen")
        }
    }
}