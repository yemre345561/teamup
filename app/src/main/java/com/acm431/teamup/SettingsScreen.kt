package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopNavigationBar() }, // Adjusted to match a static TopNavigationBar without navController
        bottomBar = { BottomNavigationBar(navController) }, // Using BottomNavigationBar
        containerColor = Color(0xFFF8EECF) // Light background matching app theme
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
                label = "Change Password", // Navigates to ChangePasswordScreen
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
                onClick = { /* Handle log out functionality */ }
            )
        }
    }
}

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




