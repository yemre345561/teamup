package com.acm431.teamup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UserTypeSelectionScreen(navController: NavHostController, onUserTypeSelected: (String) -> Unit) {
    // Background color from the login screen
    val backgroundColor = Color(0xFFF8EECF) // Exact match
    val primaryBlue = Color(0xFF274472)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center content vertically
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.teamup_logo), // Replace with your logo resource
            contentDescription = "TeamUp Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Fit
        )

        // Title
        Text(
            text = "TeamUp",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = primaryBlue,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Instruction Text
        Text(
            text = "Which side would you like to be on?",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Row for Options
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            // Student Card
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(180.dp) // Made the card bigger
                    .clip(RoundedCornerShape(16.dp))
                    .background(backgroundColor) // Removed blue background
                    .clickable { onUserTypeSelected("Student") }
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.girl_boy), // Replace with student icon resource
                    contentDescription = "Student Icon",
                    modifier = Modifier.size(100.dp), // Increased photo size
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Student",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryBlue,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Investor Card
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(180.dp) // Made the card bigger
                    .clip(RoundedCornerShape(16.dp))
                    .background(backgroundColor) // White background
                    .clickable { onUserTypeSelected("Investor") }
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.girl_boy), // Replace with investor icon resource
                    contentDescription = "Investor Icon",
                    modifier = Modifier.size(100.dp), // Increased photo size
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Investor",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryBlue,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Back Button to Login
        Button(
            onClick = { navController.navigate("login") }, // Navigate back to Login
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(48.dp)
        ) {
            Text(
                text = "Back to Log In",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}







