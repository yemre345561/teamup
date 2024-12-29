package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TermsAndConditionsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopNavigationBarWithBack(
                navController = navController,
                title = "Terms and Conditions"
            )
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
                // Terms and Conditions Content
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

                        **6. Service Availability**
                        - While TeamUp aims for 24/7 service availability, occasional maintenance or technical issues may cause interruptions. Users will be notified of scheduled downtimes in advance.

                        **7. Account Security**
                        - Users are responsible for securing their account credentials. TeamUp will not be held responsible for unauthorized access resulting from user negligence.
                        - Any suspicious activity on your account should be reported immediately to our support team.

                        **8. Updates to Terms**
                        - TeamUp reserves the right to update these terms and conditions at any time. Users will be notified of significant changes, and continued use of the platform constitutes acceptance of these updates.

                        **9. Reporting Issues**
                        - Users can report bugs, inappropriate content, or other issues directly through the app's support section. TeamUp will work promptly to resolve these.

                        **10. Termination of Service**
                        - TeamUp may terminate or suspend accounts that violate these terms or engage in behavior that disrupts the platform's safety and functionality.

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

// **Updated Top Navigation Bar with Back Button and Color**
@Composable
fun TopNavigationBarWithBack(navController: NavHostController, title: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF0E2C47), // **Updated Dark Blue Color**
            titleContentColor = Color.White
        ),
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    )
}














