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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressDetailScreen(addressId: String?, saveAddressAndBack: (String?) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        var savableAddressId by rememberSaveable {
            mutableStateOf(addressId)
        }

        if (addressId.isNullOrEmpty()) {
            Text(text = "Add new Address")
        } else {
            Text(text = "Edit Address with id: $addressId")
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = savableAddressId ?: "", onValueChange = {
            savableAddressId = it
        })
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            saveAddressAndBack(savableAddressId)
        }) {
            Text("Save")
        }
    }

}