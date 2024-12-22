package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopNavigationBar() }, // Use the TopNavigationBar file
        bottomBar = { BottomNavigationBar(navController) }, // Use BottomNavigationBar
        containerColor = Color(0xFFF9F5E7)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            HomePostList()
        }
    }
}

@Composable
fun HomePostList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        repeat(2) { // Simulate two posts
            HomePostItem(
                username = "Joshua Smith",
                userType = "Investor",
                timeAgo = "2h Ago",
                description = "This is a placeholder description for a post. Replace it with dynamic content.",
                images = listOf("Image 1", "Image 2", "Image 3")
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HomePostItem(
    username: String,
    userType: String,
    timeAgo: String,
    description: String,
    images: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(50))
                    ) {
                        Text(
                            text = username.take(2), // Display initials
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = username, style = MaterialTheme.typography.bodyLarge)
                        Text(text = userType, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        Text(text = timeAgo, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Images Placeholder
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                images.forEach { imagePlaceholder ->
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .weight(1f)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = imagePlaceholder,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Like and Comment Buttons
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = { /* Like */ }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Like")
                }
                IconButton(onClick = { /* Comment */ }) {
                    Icon(imageVector = Icons.Filled.Chat, contentDescription = "Comment")
                }
            }
        }
    }
}