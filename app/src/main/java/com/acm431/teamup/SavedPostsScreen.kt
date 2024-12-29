package com.acm431.teamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview


class SavedPostsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SavedPostsScreen(navController)
        }
    }
}

// **Main Saved Posts Screen**
@Composable
fun SavedPostsScreen(navController: NavHostController) {
    Scaffold(
        topBar = { SavedPostsTopBar(navController) }, // Custom Top Bar with Back Arrow
        bottomBar = { BottomNavigationBar(navController) }, // Existing Bottom Bar
        containerColor = Color(0xFFFFFCEF) // Background Color
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // **Header Section**
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Title
                Text(
                    text = "Saved Posts",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00264D)
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Divider Line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFB0BEC5)) // Line color
                )
            }

            // **Content Section**
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved posts yet.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFB0BEC5) // Light gray text
                )
            }
        }
    }
}

// **Top Bar with Back Arrow and Centered Logo and Name**
@Composable
fun SavedPostsTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E2C47)) // Dark blue background
            .height(70.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // **Back Arrow**
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // **Centered Logo and App Name**
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TEAM",
                    color = Color(0xFFF8EECF), // Light cream
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "UP",
                    color = Color(0xFFF8EECF),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.teamup_logo2),
                contentDescription = "Logo",
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.size(48.dp)) // Placeholder for symmetry
    }
}

// **Preview**
@Preview(showBackground = true)
@Composable
fun PreviewSavedPostsScreen() {
    val navController = rememberNavController()
    SavedPostsScreen(navController)
}




