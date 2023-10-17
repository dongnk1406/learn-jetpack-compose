package com.example.learncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learncompose.ui.catalog.category.CategoryScreen
import com.example.learncompose.ui.catalog.product.ProductDetailScreen
import com.example.learncompose.ui.customer.AddressDetailScreen
import com.example.learncompose.ui.customer.MyAccountScreen
import com.example.learncompose.ui.home.CheckoutScreen
import com.example.learncompose.ui.home.HomeScreen
import com.example.learncompose.ui.`learn-common-compose`.LearnCommonCompose
import com.example.learncompose.ui.test.TestScreen
import com.example.learncompose.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                CompositionLocalProvider(
                    LocalAppColor provides AppColor(
                        bodyTextColor = Color.Black, titleTextColor = Color.Red
                    )
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val toggleDrawer: () -> Unit = {
        coroutineScope.launch {
            if (drawerState.isClosed) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }

    AppDrawer(toggleDrawer = toggleDrawer, drawerState = drawerState) {
        Scaffold(
            topBar = {
                AppTopBar(toggleDrawer = toggleDrawer)
            },
            bottomBar = {
                AppBottomBar()
            },
        ) { paddingValues ->
            AppNavigator(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun AppNavigator(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home", modifier = modifier) {
        composable("Home") {
            HomeScreen(
                openCategoryAction = {
                    navController.navigate("Category")
                }, navController = navController
            )
        }
        composable("LearnCommonCompose") {
            LearnCommonCompose()
        }
        composable("Category") {
            CategoryScreen(openProductDetail = { productId ->
                navController.navigate("ProductDetail/$productId")
            })
        }
        composable("MyAccount") {
            MyAccountScreen(navController = navController, openAddressScreen = { addressId ->
                val route =
                    if (addressId != null) "AddressDetail?addressId=$addressId" else "AddressDetail"
                navController.navigate(route)
            })
        }
        composable(
            "ProductDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString("productId")
            requireNotNull(productId)
            ProductDetailScreen(productId = productId, checkout = { cartId, customerId ->
                navController.navigate("Checkout/$cartId/$customerId")
            })
        }
        composable(
            "Checkout/{cartId}/{customerId}",
            arguments = listOf(navArgument("cartId") { type = NavType.StringType },
                navArgument("customerId") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val cartId = navBackStackEntry.arguments?.getString("cartId")
            val customerId = navBackStackEntry.arguments?.getString("customerId")
            requireNotNull(cartId)
            requireNotNull(customerId)
            CheckoutScreen(cartId = cartId, customerId = customerId, placeOrderAction = {})
        }
        composable(
            "AddressDetail?addressId={addressId}", arguments = listOf(navArgument("addressId") {
                nullable = true
            })
        ) { navBackStackEntry ->
            val addressId = navBackStackEntry.arguments?.getString("addressId")
            AddressDetailScreen(addressId = addressId, saveAddressAndBack = {
                navController.previousBackStackEntry?.savedStateHandle?.set("new_address_id", it)
                navController.popBackStack()
            })
        }
        composable("Test") {
            TestScreen(
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(toggleDrawer: () -> Unit) {
    TopAppBar(title = {
        Text(text = "Title", style = TextStyle(color = Color.White))
    },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { toggleDrawer() }) {
                Icon(Icons.Default.Menu, contentDescription = "", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, contentDescription = "", tint = Color.White)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "", tint = Color.White)
            }
        })
//    You can use your custom top bar
//    Text(text = "Top Bar", style = TextStyle(color = Color.Red))
}

@Composable
fun AppBottomBar() {
//    https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        contentColor = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(1f)
                    .clickable { }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "")
                Text(text = "Home")
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(1f)
                    .clickable { }) {

                Icon(Icons.Default.ShoppingCart, contentDescription = "")
                Text(text = "Cart")
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(1f)
                    .clickable { }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "")
                Text(text = "Chat")
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(1f)
                    .clickable { }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "")
                Text(text = "Profile")
            }
        }
    }
}

@Composable
fun AppDrawer(
    toggleDrawer: () -> Unit,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            NavigationDrawerItem(label = { Text(text = "Item 1") },
                selected = true,
                onClick = { toggleDrawer() })
            NavigationDrawerItem(label = { Text(text = "Item 2") }, selected = false, onClick = { })
        }
    }, drawerState = drawerState) {
        content()
    }
}

data class AppColor(val bodyTextColor: Color, val titleTextColor: Color)

val LocalAppColor =
    compositionLocalOf { AppColor(bodyTextColor = Color.Black, titleTextColor = Color.Red) }
