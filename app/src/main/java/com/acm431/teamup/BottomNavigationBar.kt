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
            .background(Color(0xFF274472))
            .height(65.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
            }

            IconButton(onClick = { navController.navigate("search") }) {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(4.dp, CircleShape)
                    .background(Color(0xFFF8EECF), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { navController.navigate("sharePost") }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Post", tint = Color(0xFF274472))
                }
            }

            IconButton(onClick = { navController.navigate("notifications") }) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = Color.White)
            }

            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.White)
            }
        }
    }
}
