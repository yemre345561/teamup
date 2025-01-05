package com.acm431.teamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acm431.teamup.data.repository.AuthRepository
import com.acm431.teamup.data.repository.PostRepository
import com.acm431.teamup.ui.home.HomeScreen
import com.acm431.teamup.ui.login.LoginScreen
import com.acm431.teamup.ui.login.SignUpScreen
import com.acm431.teamup.ui.notifications.NotificationsPage
import com.acm431.teamup.ui.post.SavedPostsScreen
import com.acm431.teamup.ui.post.SharePostScreen
import com.acm431.teamup.ui.profile.ProfileScreen
import com.acm431.teamup.ui.search.SearchScreen
import com.acm431.teamup.ui.setttings.ChangePasswordScreen
import com.acm431.teamup.ui.setttings.SettingsScreen
import com.acm431.teamup.ui.setttings.TermsAndConditionsScreen
import com.acm431.teamup.viewmodel.AuthViewModel
import com.acm431.teamup.viewmodel.AuthViewModelFactory
import com.acm431.teamup.viewmodel.PostViewModel
import com.acm431.teamup.viewmodel.PostViewModelFactory
import com.google.firebase.FirebaseApp

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
    val postRepository = remember { PostRepository() }
    val authRepository = remember { AuthRepository() }

    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(postRepository))
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))

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


        composable("profile") {
            ProfileScreen(navController, userId = null) // Kendi profiliniz
        }

        composable("profile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ProfileScreen(navController, userId)
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
            SavedPostsScreen(navController = navController, postViewModel = postViewModel, authRepository = authRepository)
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





