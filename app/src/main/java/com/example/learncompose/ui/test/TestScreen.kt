package com.example.learncompose.ui.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.navigation.NavHostController
import com.example.learncompose.LocalAppColor
import kotlin.random.Random

@Composable
fun TestScreen(
    navController: NavHostController? = null
) {
    var bodyTextColor by remember {
        mutableStateOf(Color.Black)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            navController?.popBackStack()
        }) {
            Text(text = "Back", color = LocalAppColor.current.bodyTextColor)
        }
        Text(text = "Default title text color", color = LocalAppColor.current.titleTextColor)
        Text(
            text = "Random text color",
            style = TextStyle(
                color = bodyTextColor
            )
        )
        Button(onClick = {
            bodyTextColor = getColor()
        }) {
            Text(text = "Make random title color")
        }
    }
}

fun getColor(): Color {
    var listColors = listOf<Color>(Color.Blue, Color.Cyan, Color.Green, Color.Red)
    var index = Random.nextInt(0, listColors.size - 1)
    return listColors[index]
}

