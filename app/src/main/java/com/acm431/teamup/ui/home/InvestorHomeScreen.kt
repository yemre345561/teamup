package com.acm431.teamup.ui.home

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
import com.acm431.teamup.BottomNavigationBar

@Composable
fun InvestorHomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { InvestorTopBar() }, // Investor-specific Top Bar
        bottomBar = { BottomNavigationBar(navController) }, // Shared Bottom Bar
        containerColor = Color(0xFFFFFCEF) // Light cream background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Investor-specific post list
            InvestorPostList()
        }
    }
}

// **Investor Top Bar**
@Composable
fun InvestorTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E2C47))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Investor Home",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
    }
}

// **Investor Post List**
@Composable
fun InvestorPostList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        repeat(2) { // Example with 2 posts
            InvestorPostItem(
                username = "Investor John",
                timeAgo = "2h Ago",
                description = "Investor-specific post content goes here."
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// **Investor Post Item**
@Composable
fun InvestorPostItem(
    username: String,
    timeAgo: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF18507E)), // Dark Blue
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




