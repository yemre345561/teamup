package com.acm431.teamup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            ProfileTopBar(navController = navController) // Updated top bar with centered logo and name
        },
        bottomBar = { BottomNavigationBar(navController) } // Bottom navigation bar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFFCEF)) // Light cream background
                .padding(16.dp)
        ) {
            ProfileContent(navController) // Profile content
        }
    }
}

// **Top Navigation Bar (Centered Logo and App Name with Settings Icon)**
@Composable
fun ProfileTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Height for the top bar
            .background(Color(0xFF0E2C47)) // Dark blue background
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // **Centered Content (Logo and App Name)**
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

        // **Settings Icon (Top-Right Corner)**
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Filled.Settings, // Settings icon
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

// **Profile Content**
@Composable
fun ProfileContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // **Profile Picture Placeholder**
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Profile\nPicture",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // **Name and Role**
        Text(
            text = "Joshua Smith",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            color = Color.Black
        )
        Text(
            text = "Investor", // Replace based on user type
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))

        // **Personal Info Card**
        ProfileInfoCard(
            title = "Personal Info",
            content = "Your personal information will be displayed here."
        )
        Spacer(modifier = Modifier.height(16.dp))

        // **CV Card**
        ProfileInfoCard(
            title = "CV",
            content = "Uploaded CVs will be displayed here."
        )
        Spacer(modifier = Modifier.height(16.dp))

        // **Posts Section**
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

// **Reusable Info Card Component**
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

// **Reusable Post Item Component**
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













