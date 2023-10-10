package com.example.learncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learncompose.`learn-common-compose`.LearnCommonCompose
import com.example.learncompose.ui.catalog.category.CategoryScreen
import com.example.learncompose.ui.catalog.product.ProductDetailScreen
import com.example.learncompose.ui.customer.AddressDetailScreen
import com.example.learncompose.ui.customer.MyAccountScreen
import com.example.learncompose.ui.home.CheckoutScreen
import com.example.learncompose.ui.home.HomeScreen
import com.example.learncompose.ui.test.TestScreen
import com.example.learncompose.ui.theme.LearnComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnComposeTheme {
                CompositionLocalProvider(
                    LocalAppColor provides AppColor(
                        bodyTextColor = Color.Black,
                        titleTextColor = Color.Red
                    )
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(), color = Color.White
                    ) {
                        MainApp()
                    }
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    LearnComposeTheme {
        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") {
                HomeScreen(
                    openCategoryAction = {
                        navController.navigate("Category")
                    },
                    openMyAccountScreen = {},
                    navController = navController
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
                    navArgument("customerId") { type = NavType.StringType }
                )
            ) { navBackStackEntry ->
                val cartId = navBackStackEntry.arguments?.getString("cartId")
                val customerId = navBackStackEntry.arguments?.getString("customerId")
                requireNotNull(cartId)
                requireNotNull(customerId)
                CheckoutScreen(cartId = cartId, customerId = customerId, placeOrderAction = {})
            }
            composable("AddressDetail?addressId={addressId}",
                arguments = listOf(
                    navArgument("addressId") {
                        nullable = true
                    }
                )
            ) { navBackStackEntry ->
                val addressId = navBackStackEntry.arguments?.getString("addressId")
                AddressDetailScreen(addressId = addressId, saveAddressAndBack = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("new_address_id", it)
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
}

data class AppColor(val bodyTextColor: Color, val titleTextColor: Color)

val LocalAppColor =
    compositionLocalOf { AppColor(bodyTextColor = Color.Black, titleTextColor = Color.Red) }