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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image


@Composable
fun ChangePasswordScreen(navController: NavHostController) {
    val backgroundColor = Color(0xFFFFFCEF) // Light cream background
    val primaryBlue = Color(0xFF274472)    // Primary blue for buttons

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            ChangePasswordTopBar(navController) // **Reusing Terms and Conditions Top Bar Logic**
        },
        bottomBar = { BottomNavigationBar(navController) } // Bottom navigation bar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // **Old Password Field**
            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                placeholder = { Text("Old Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryBlue,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // **New Password Field**
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("New Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryBlue,
                    unfocusedBorderColor = Color.Gray
                )
            )

            // **Update Button**
            Button(
                onClick = { /* Handle password update logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(bottom = 16.dp)
            ) {
                Text("Update", color = Color.White)
            }
        }
    }
}

// **Top Bar - Same as Terms and Conditions Screen**
@Composable
fun ChangePasswordTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Same height as Terms and Conditions Screen
            .background(Color(0xFF0E2C47)) // Dark blue background
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // **Back Arrow on Left**
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) { // Go back
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // **Centered Logo and App Name**
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
                painter = painterResource(id = R.drawable.teamup_logo2), // Logo
                contentDescription = "Team Up Logo",
                modifier = Modifier.size(50.dp), // Same size as Terms Screen
                contentScale = ContentScale.Fit
            )
        }
    }
}











