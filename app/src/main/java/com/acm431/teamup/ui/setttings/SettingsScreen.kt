package com.acm431.teamup.ui.setttings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.acm431.teamup.BottomNavigationBar
import com.acm431.teamup.R

@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold(
        topBar = { SettingsTopBar(navController) }, // Top bar with back arrow and logo
        bottomBar = { BottomNavigationBar(navController) }, // Bottom bar
        containerColor = Color(0xFFFFFCEF) // Light background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Settings Title
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                color = Color(0xFF274472),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Settings Options
            SettingsOption(
                icon = Icons.Filled.Bookmark,
                label = "Saved Posts",
                onClick = { navController.navigate("savedPosts") }
            )
            SettingsOption(
                icon = Icons.Filled.Person,
                label = "Change Password",
                onClick = { navController.navigate("changePassword") }
            )
            SettingsOption(
                icon = Icons.Filled.Description,
                label = "Terms and Conditions",
                onClick = { navController.navigate("termsAndConditions") }
            )
            SettingsOption(
                icon = Icons.Filled.ExitToApp,
                label = "Log Out",
                onClick = { navController.navigate("logOut") } // Navigate to LogOutScreen
            )
        }
    }
}

// **Custom Top Bar with Logo, App Name, and Back Arrow**
@Composable
fun SettingsTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Adjust height for better layout
            .background(Color(0xFF0E2C47)) // Dark blue background
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // **Back Arrow on Left**
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) { // Go back
                Icon(
                    imageVector = Icons.Filled.ArrowBack, // Built-in back arrow
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // **Centered Logo and App Name**
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TEAM",
                    color = Color(0xFFF8EECF), // Light cream color
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "UP",
                    color = Color(0xFFF8EECF), // Light cream color
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.width(8.dp)) // Space between text and logo
            Image(
                painter = painterResource(id = R.drawable.teamup_logo2), // Replace with your logo
                contentDescription = "Team Up Logo",
                modifier = Modifier.size(50.dp), // Adjust size
                contentScale = ContentScale.Fit
            )
        }
    }
}

// **Reusable Settings Option Component**
@Composable
fun SettingsOption(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF274472),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = Color(0xFF274472)
        )
    }
}







