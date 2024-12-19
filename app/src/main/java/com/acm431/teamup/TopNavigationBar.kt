package com.acm431.teamup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun TopNavigationBar() {
    // Container for the top navigation bar
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Adjust height for better layout
            .background(Color(0xFF0E2C47)), // Dark blue background
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Logo and Text Column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // "TEAM" text
            Text(
                text = "TEAM",
                color = Color(0xFFF8EECF), // Light cream color
                fontSize = 18.sp, // Slightly reduced font size for better balance
                style = MaterialTheme.typography.labelLarge
            )
            // "UP" text
            Text(
                text = "UP",
                color = Color(0xFFF8EECF), // Light cream color
                fontSize = 18.sp, // Matching font size with "TEAM"
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Space between text and logo

        // Logo Image
        Image(
            painter = painterResource(id = R.drawable.teamup_logo2), // Ensure the logo resource is available
            contentDescription = "Team Up Logo",
            modifier = Modifier
                .size(50.dp), // Slightly reduced size for proportion
            contentScale = ContentScale.Fit
        )
    }
}






