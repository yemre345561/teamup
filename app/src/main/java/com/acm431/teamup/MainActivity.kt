package com.acm431.teamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Set Content
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
        startDestination = "login" // Default start destination
    ) {
        // Login Screen
        composable("login") {
            LoginScreen(navController = navController)
        }

        // User Type Selection Screen
        composable("userTypeSelection") {
            UserTypeSelectionScreen(
                onUserTypeSelected = { userType ->
                    if (userType == "Investor") {
                        navController.navigate("signupInvestor")
                    } else {
                        navController.navigate("signupStudent")
                    }
                },
                navController = navController
            )
        }

        // Investor Sign-Up Screen
        composable("signupInvestor") {
            SignUpScreen(userType = "Investor", navController = navController)
        }

        // Student Sign-Up Screen
        composable("signupStudent") {
            SignUpScreen(userType = "Student", navController = navController)
        }

        // Home Screen
        composable("home") {
            HomeScreen(navController = navController)
        }

        // Profile Screen
        composable("profile") {
            ProfileScreen(navController = navController)
        }

        // Settings Screen
        composable("settings") {
            SettingsScreen(navController = navController)
        }

        // Change Password Screen
        composable("changePassword") {
            ChangePasswordScreen(navController = navController)
        }

        // Notifications Screen
        composable("notifications") {
            NotificationsPage(navController = navController)
        }

        // Terms and Conditions Screen
        composable("termsAndConditions") {
            TermsAndConditionsScreen(navController = navController)
        }

        // Saved Posts Screen
        composable("savedPosts") {
            SavedPostsScreen(navController = navController) // Passed navController
        }

        // Log Out Screen
        composable("logOut") {
            LogoutScreen(navController = navController) // Passed navController
        }

        // **Share Post Screen** (Now Accessible Everywhere via Bottom Navigation Bar)
        composable("sharePost") {
            SharePostScreen(navController = navController) // Passed navController
        }

        // **Search Screen** (Accessible via Bottom Navigation Bar)
        composable("search") {
            SearchScreen(navController = navController) // Passed navController
        }
    }
}





