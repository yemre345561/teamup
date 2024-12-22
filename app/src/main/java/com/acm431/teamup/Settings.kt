package com.acm431.teamup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F5E9)) // Cream background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top Bar with TeamUp Text, Logo, and Settings Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF173251))
                    .padding(vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // TeamUp Text and Logo
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        // Text: "TEAM" on top and "UP" below
                        Column(
                            horizontalAlignment = Alignment.End, // Align text closer to the logo
                            modifier = Modifier.padding(end = 1.dp) // Add spacing
                        ) {
                            Text(
                                text = "TEAM",
                                color = Color(0xFFFEFDF6), // Cream color
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "UP",
                                color = Color(0xFFFEFDF6), // Cream color
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Logo
                        Image(
                            painter = painterResource(id = R.drawable.tu_background_02),
                            contentDescription = "TeamUp Logo",
                            modifier = Modifier
                                .height(50.dp) // Adjusted size
                                .width(50.dp) // Ensured consistent width
                        )
                    }
                }

                // Settings Icon
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(40.dp)
                        .background(Color(0xFFF7F5E9), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = "Settings Icon",
                        tint = Color(0xFF173251),
                        modifier = Modifier
                            .size(34.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            // Title Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Settings",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF173251),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp) // Space between text and underline
                )
                HorizontalDivider(
                    modifier = Modifier.padding(top = 4.dp),
                    thickness = 2.dp,
                    color = Color(0xFF173251)
                )
            }

            // Settings Options
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                SettingsOptionItem(
                    title = "Saved Posts",
                    iconId = R.drawable.bookmark,
                    onClick = { /* TODO: Navigate to Saved Posts */ }
                )
                HorizontalDivider(
                    color = Color(0xFF173251),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                SettingsOptionItem(
                    title = "Change Log In Information",
                    iconId = R.drawable.account_circle,
                    onClick = { /* TODO: Navigate to Change Log In Info */ }
                )
                HorizontalDivider(
                    color = Color(0xFF173251),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                SettingsOptionItem(
                    title = "Terms and Conditions",
                    iconId = R.drawable.article,
                    onClick = { /* TODO: Navigate to Terms and Conditions */ }
                )
                HorizontalDivider(
                    color = Color(0xFF173251),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                SettingsOptionItem(
                    title = "Log Out",
                    iconId = R.drawable.logout,
                    onClick = { /* TODO: Handle Log Out */ }
                )
                HorizontalDivider(
                    color = Color(0xFF173251),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
            }
        }

        // Bottom Navigation Bar pinned to the bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BottomNavigationBar2()
        }
    }
}

@Composable
fun SettingsOptionItem(title: String, iconId: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 1.dp, horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Icon
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color(0xFF173251),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )

            // Title Text
            Text(
                text = title,
                fontSize = 20.sp,
                color = Color(0xFF173251),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun BottomNavigationBar2() {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color(0xFF173251) // Bottom bar color
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Navigate to Home */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier.size(50.dp)
                )
            }
            IconButton(onClick = { /* Search action */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier.size(50.dp)
                )
            }
            IconButton(onClick = { /* Add content */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier.size(50.dp)
                )
            }
            IconButton(onClick = { /* Notifications */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.notifications),
                    contentDescription = "Notifications",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier.size(50.dp)
                )
            }
            IconButton(onClick = { /* Profile screen */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Profile",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsPage() {
    SettingsPage()
}
