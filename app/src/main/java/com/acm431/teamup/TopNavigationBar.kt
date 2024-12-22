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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // Reduced height for better proportions
            .background(Color(0xFF0E2C47)), // Dark blue background
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Text and Logo
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 8.dp) // Spacing between text and logo
            ) {
                Text(
                    text = "TEAM",
                    color = Color(0xFFF8EECF), // Light cream color
                    fontSize = 20.sp, // Reduced font size
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "UP",
                    color = Color(0xFFF8EECF), // Light cream color
                    fontSize = 20.sp, // Reduced font size
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Image(
                painter = painterResource(id = R.drawable.teamup_logo2), // Replace with your logo resource
                contentDescription = "Team Up Logo",
                modifier = Modifier
                    .size(60.dp), // Smaller logo size
                contentScale = ContentScale.Fit
            )
        }
    }
}