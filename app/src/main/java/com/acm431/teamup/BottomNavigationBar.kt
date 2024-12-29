package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E2C47))
            .height(65.dp), // Navigation Bar Height
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Icon
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Search Icon
            IconButton(onClick = { navController.navigate("search") }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Add Post Button (Centered with smaller circle)
            IconButton(onClick = { navController.navigate("sharePost") }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp) // Icon size
                    )
                }

            // Notifications Icon
            IconButton(onClick = { navController.navigate("notifications") }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Profile Icon
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}









