package com.acm431.teamup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Correct Background Color
    val backgroundColor = Color(0xFFFFFCEF) // Slightly lighter shade
    val inputBackground = Color(0xFFD2E4F3) // Light blue input fields
    val primaryBlue = Color(0xFF274472)     // Dark blue for button and text

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo (Increased Size)
        Image(
            painter = painterResource(id = R.drawable.teamup_logo), // Replace with actual resource
            contentDescription = "TeamUp Logo",
            modifier = Modifier
                .size(140.dp) // Increased size for logo
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Title (Bigger & Bold)
        Text(
            text = "TeamUp",
            fontSize = 36.sp, // Bigger font size
            fontWeight = FontWeight.Bold, // Make bold
            color = primaryBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Username Input Field
        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackground,
                unfocusedContainerColor = inputBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password Input Field
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackground,
                unfocusedContainerColor = inputBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                navController.navigate("home")
            },
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
            shape = RoundedCornerShape(25.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)
        ) {
            Text(
                text = "Log in",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Register Button
        OutlinedButton(
            onClick = { navController.navigate("userTypeSelection") },
            shape = RoundedCornerShape(25.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryBlue),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(40.dp)
        ) {
            Text(
                text = "Register",
                fontSize = 14.sp
            )
        }
    }
}















