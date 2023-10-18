package com.example.learncompose.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    openCategoryAction: () -> Unit, navController: NavHostController? = null
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
        modifier = Modifier.fillMaxSize(),
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
        Button(onClick = { openCategoryAction() }) {
            Text("Open Category")
        }
        Button(onClick = {
            navController?.navigate("MyAccount")
        }) {
            Text(text = "Open MyAccount")
        }
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
        CoroutinesCompose()

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
    ConstraintLayoutContent()
    DraggableTextLowLevel()
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout(
        modifier = Modifier
            .background(color = Color(0xFF9CCC65))
            .fillMaxWidth()
            .height(100.dp)
    ) {
        // Create references for the composables to constrain
        val (button, text1, text2) = createRefs()
        val verticalGuideline50 = createGuidelineFromTop(0.5f)
        val horizontalGuideline50 = createGuidelineFromStart(0.5f)
        val localContext = LocalContext.current
        Button(onClick = {
            Toast.makeText(localContext, "This is default toast message", Toast.LENGTH_SHORT).show()
        },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text("ConstraintLayout", Modifier.constrainAs(text1) {
            start.linkTo(button.end)
            top.linkTo(button.bottom)
        })

        Text("ConstraintLayout Guideline", Modifier.constrainAs(text2) {
            start.linkTo(horizontalGuideline50)
            bottom.linkTo(verticalGuideline50)
        })
    }
}

@Composable
private fun DraggableTextLowLevel() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableFloatStateOf(0f) }
        var offsetY by remember { mutableFloatStateOf(0f) }
        var currentColor: Color by remember {
            mutableStateOf(Color.Yellow)
        }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .clip(CircleShape)
                .background(currentColor)
                .size(80.dp)
                .pointerInput(Unit) {
                    detectDragGestures(onDragStart = {
                        currentColor = Color.Red
                    }, onDragEnd = {
                        currentColor = Color.Yellow
                    }, onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    })
                })
    }
}

@Composable
fun CoroutinesCompose() {
    var loading by remember {
        mutableStateOf(false)
    }
    var count by remember {
        mutableIntStateOf(0)
    }

    val mainScope = CoroutineScope(Job() + Dispatchers.Main)
    val ioScope = CoroutineScope(Job() + Dispatchers.IO)
    val defaultScope = CoroutineScope(Job() + Dispatchers.Default)


    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { count++ }) {
            Text(text = "Increase")
        }
        Text(text = count.toString(), modifier = Modifier.padding(horizontal = 16.dp))

        Button(onClick = {
            loading = !loading
            mainScope.launch {
                Log.d("dTag", "mainScope with thread: ${Thread.currentThread().name}")
            }
            ioScope.launch {
                Log.d("dTag", "ioScope with thread: ${Thread.currentThread().name}")
            }
            defaultScope.launch {
                Log.d("dTag", "defaultScope with thread: ${Thread.currentThread().name}")
            }

        }, modifier = Modifier.padding(end = 16.dp)) {
            Text(text = if (loading) "Stop loading" else "Start loading")
        }
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.width(20.dp),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    HomeScreen(openCategoryAction = { /*TODO*/ })
}

