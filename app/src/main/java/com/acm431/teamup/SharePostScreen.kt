package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image

@Composable
fun SharePostScreen(navController: NavHostController) {
    Scaffold(
        topBar = { SharePostTopBar(navController) }, // Custom Top Bar
        bottomBar = { BottomNavigationBar(navController) } // Bottom Navigation Bar
    ) { innerPadding ->
        SharePostContent(modifier = Modifier.padding(innerPadding))
    }
}

// **Top Bar with Logo, Name, and Back Arrow**
@Composable
fun SharePostTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Same height as other screens
            .background(Color(0xFF0E2C47)) // Dark blue background
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // **Back Arrow on Left**
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) { // Go back
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // **Centered Logo and App Name (Same as your top bar)**
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

// **Content Section**
@Composable
fun SharePostContent(modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var caption by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9F5E7)) // Background color
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = caption,
            onValueChange = { caption = it },
            label = { Text("Caption") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Photos", fontSize = 16.sp)
            Button(onClick = { /* Handle adding photos */ }) {
                Text("Add")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Files", fontSize = 16.sp)
            Button(onClick = { /* Handle adding files */ }) {
                Text("Add")
            }
        }

        OutlinedTextField(
            value = tags,
            onValueChange = { tags = it },
            label = { Text("Tags") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /* Handle post submission */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Post")
        }
    }
}


