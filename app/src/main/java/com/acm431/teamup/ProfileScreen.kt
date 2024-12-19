package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            ProfileTopBar(navController = navController) // Top bar with settings icon
        },
        bottomBar = { BottomNavigationBar(navController) } // Use BottomNavigationBar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ProfileContent()
        }
    }
}

@Composable
fun ProfileTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF274472))
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = "TEAM UP - Profile",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            color = Color.White
        )

        // Settings Icon
        IconButton(onClick = { navController.navigate("settings") }) {
            Icon(
                imageVector = Icons.Filled.Settings, // Explicitly use imageVector
                contentDescription = "Settings",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ProfileContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, shape = RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Profile\nPicture",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Joshua Smith",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            color = Color.Black
        )
        Text(
            text = "Investor",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))

        ProfileInfoCard(title = "Personal Info", content = "Your personal information will be displayed here.")
        Spacer(modifier = Modifier.height(16.dp))

        ProfileInfoCard(title = "CV", content = "Uploaded CVs will be displayed here.")
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Posts",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color(0xFF274472),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        repeat(2) {
            ProfilePostItem(
                username = "Joshua Smith",
                userType = "Investor",
                timeAgo = "2h Ago",
                description = "This is a placeholder description for a post."
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProfileInfoCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                color = Color(0xFF274472)
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfilePostItem(
    username: String,
    userType: String,
    timeAgo: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$username - $userType",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = timeAgo,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}









