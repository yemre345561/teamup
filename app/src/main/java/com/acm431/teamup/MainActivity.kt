package com.acm431.teamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(onNavigateToUserTypeSelection = { navController.navigate("userTypeSelection") })
        }
        composable("userTypeSelection") {
            UserTypeSelectionScreen(
                navController = navController,
                onUserTypeSelected = { userType ->
                    if (userType == "Investor") {
                        navController.navigate("signupInvestor")
                    } else {
                        navController.navigate("signupStudent")
                    }
                }
            )
        }
        composable("signupInvestor") {
            SignUpScreen(userType = "Investor", navController = navController)
        }
        composable("signupStudent") {
            SignUpScreen(userType = "Student", navController = navController)
        }
    }
}


