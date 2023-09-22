package com.example.learncompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.learncompose.ui.theme.LearnComposeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        ImageCompose()
        SimpleButton()
        RadioButtonCompose()
    }
}
// ******************************
@Composable
fun ImageCompose() {
    //    load image from local
    Image(painterResource(id = R.drawable.banner), contentDescription = "Alone Tree")

    Row() {
        //    load image from url
        AsyncImage(model = "https://www.w3schools.com/css/img_5terre.jpg",
            contentDescription = "Logo",
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onPress = {
                    Log.d("dTag", "onPress")
                }, onLongPress = {
                    Log.d("dTag", "onLongPress")
                }, onDoubleTap = {
                    Log.d("dTag", "onDoubleTap")
                })
            })

        //    Rounded image
        AsyncImage(
            model = "https://h5p.org/sites/default/files/h5p/content/1209180/images/file-6113d5f8845dc.jpeg",
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable(onClick = {
                    Log.d("dTag", "Press image")
                })
        )
    }

}

// ******************************
@Composable
fun SimpleButton() {
    OutlinedButton(
        onClick = { }, colors = ButtonDefaults.buttonColors(
            containerColor = Color.Yellow,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White,
        ), enabled = true, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    ) {
        Image(imageVector = Icons.Filled.Call, contentDescription = null)
        Text(text = "Call me!")
    }

    Button(
        onClick = {}, shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp, pressedElevation = 20.dp, disabledElevation = 0.dp
        ),
    ) {
        Column() {
            Image(imageVector = Icons.Filled.Call, contentDescription = null)
            Text(text = "Default button", color = Color.Magenta)
        }
    }

    TextButton(onClick = { /*TODO*/ }) {
        Text(text = "Text button")
    }
}

// ******************************
@Composable
fun RadioButtonWithTextCompose(text: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = {
                isSelected = !isSelected
            },
        )
    ) {
        RadioButton(
            selected = isSelected, onClick = null, colors = RadioButtonDefaults.colors(
                selectedColor = Color.Red,
                unselectedColor = Color.Green,
                disabledSelectedColor = Color.Gray
            )
        )
        Text(text = text, color = Color.Black)
    }
}

@Composable
fun CustomRadioButtonWithTextCompose(text: String, selected:Boolean, onClicK:() -> Unit) {

    Row(
        modifier = Modifier.selectable(
            selected = selected,
            onClick = onClicK,
        ).padding(top = 8.dp)
    ) {
        val icon = if (selected) {
            Icons.Default.CheckCircle
        } else {
            Icons.Default.AccountCircle
        }
        Icon(icon, contentDescription = null)
        Text(text = text, color = Color.Black)
    }
}

@Composable
fun RadioButtonCompose() {
    RadioButtonWithTextCompose(text = "Radio 1")

    var isSelected by remember {
        mutableStateOf(false)
    }
    CustomRadioButtonWithTextCompose(text = "Radio 2", selected = isSelected) {
        isSelected = !isSelected
    }
}

// ******************************

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearnComposeTheme {
        HomeScreen()
    }
}