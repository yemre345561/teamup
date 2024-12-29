package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun StudentHomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { StudentTopBar() }, // Student-specific Top Bar
        bottomBar = { BottomNavigationBar(navController) }, // Shared Bottom Bar
        containerColor = Color(0xFFFFFCEF) // Light cream background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Student-specific post list
            StudentPostList()
        }
    }
}

// **Student Top Bar**
@Composable
fun StudentTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E2C47))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Student Home",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
    }
}

// **Student Post List**
@Composable
fun StudentPostList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        repeat(2) { // Example with 2 posts
            StudentPostItem(
                username = "Student Alice",
                timeAgo = "1h Ago",
                description = "Student-specific post content goes here."
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// **Student Post Item**
@Composable
fun StudentPostItem(
    username: String,
    timeAgo: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4DA8DA)), // Light Blue
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = username,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            )
            Text(
                text = timeAgo,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }
    }
}




