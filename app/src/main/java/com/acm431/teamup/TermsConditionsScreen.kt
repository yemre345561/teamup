package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image


@Composable
fun TermsAndConditionsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TermsTopBar(navController) // Top bar logic reused
        },
        bottomBar = { BottomNavigationBar(navController = navController) } // Bottom navigation bar
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFCEF)) // Light cream background
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                // **Title at the Beginning of the Text Content**
                Text(
                    text = "Terms and Conditions",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0E2C47), // Dark Blue Title Color
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // **Terms and Conditions Content**
                Text(
                    text = """
                        Welcome to TeamUp! By using our platform, you agree to the following terms and conditions:

                        **1. User Responsibilities**
                        - Users are responsible for providing accurate and up-to-date information when registering on the platform. Any incorrect details may affect the user experience or result in the suspension of services.
                        - Users must maintain a respectful and professional tone when interacting with others through the app.

                        **2. Prohibited Activities**
                        - Posting offensive, illegal, or harmful content is strictly prohibited. Violations may result in immediate suspension or permanent account termination.
                        - Any attempt to hack, exploit, or misuse the platform for unauthorized purposes will result in legal action.

                        **3. Data Privacy**
                        - TeamUp is committed to protecting your personal information. Your data will not be shared with third parties without your explicit consent, except as required by law.
                        - Users must not share other users' personal data obtained through the platform without their consent.

                        **4. Intellectual Property**
                        - All content, branding, and features within the app are the intellectual property of TeamUp. Users may not copy, distribute, or modify these without prior permission.
                        - Users retain ownership of the content they upload but grant TeamUp the rights to display and use it for app functionality.

                        **5. Liability and Disputes**
                        - TeamUp is not responsible for disputes arising between users. We encourage users to resolve conflicts amicably.
                        - TeamUp shall not be held liable for damages resulting from the use or misuse of the app.

                        Thank you for using TeamUp! We are dedicated to helping you connect, collaborate, and achieve your goals.
                    """.trimIndent(),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

// **Top Bar for Terms Screen with Back Arrow and Logo**
@Composable
fun TermsTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Same height as logout page
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
                modifier = Modifier.size(50.dp), // Same size as logout page
                contentScale = ContentScale.Fit
            )
        }
    }
}
















