package com.acm431.teamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.layout.ContentScale


class LogOutScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            LogoutScreen(navController)
        }
    }
}

// **Main Logout Screen**
@Composable
fun LogoutScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCEF)), // Light background
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // **Top Bar with Back Arrow, Logo, and Name**
        LogoutTopBar(navController)

        // **Confirmation Text**
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Are you sure you want to",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00264D),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Log Out?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00264D),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            // **Yes and No Buttons**
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                // **Yes Button**
                Button(
                    onClick = { navController.navigate("login") }, // Navigate to Login
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .border(2.dp, Color(0xFF00264D), RoundedCornerShape(50))
                ) {
                    Text(
                        "Yes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00264D)
                    )
                }

                // **No Button**
                Button(
                    onClick = { navController.navigateUp() }, // Go back
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .border(2.dp, Color(0xFF00264D), RoundedCornerShape(50))
                ) {
                    Text(
                        "No",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00264D)
                    )
                }
            }
        }

        // **Bottom Navigation Bar**
        BottomNavigationBar(navController = navController)
    }
}

// **Top Bar for Logout Screen (Same as Settings)**
@Composable
fun LogoutTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Same height as Settings Screen
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
                modifier = Modifier.size(50.dp), // Same size as Settings Screen
                contentScale = ContentScale.Fit
            )
        }
    }
}




















