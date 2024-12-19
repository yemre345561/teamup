package com.acm431.teamup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun NotificationsPage() {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color(0xFFFEFDF6))
        ) { }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top Section with Logo and Background Color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF173251)) // Set background color
                .padding(top = 0.dp, bottom = 0.dp), // Add spacing
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF173251))
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.teamup_logo),
                    contentDescription = "TeamUp Logo",
                    modifier = Modifier.height(50.dp)
                )
            }
        }

        // Notifications Header
        NotificationsHeader()

        // Notification List
        NotificationList()

    }
}

@Composable
fun NotificationsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
    ) {
        // Title Text
        Text(
            text = "Notifications",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF173251)
        )
        // Underline
        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp),
            thickness = 2.dp,
            color = Color(0xFF173251)
        )
    }
}

@Composable
fun NotificationList() {
    // Placeholder list of notifications
    val notifications = List(7) { "Placeholder uploaded an update" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        notifications.forEach { notification ->
            NotificationItem(notification)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun NotificationItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center // Center the notification box horizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Adjust the width of the notification box
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF173251)) // Background color for the box
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically // Align elements vertically
        ) {
            // Circular Image/Icon on the Left
            Box(
                modifier = Modifier
                    .size(40.dp) // Size of the circle
                    .clip(CircleShape)
                    .background(Color(0xFF8FA8BF)) // Color for the circular icon
            )

            Spacer(modifier = Modifier.width(8.dp)) // Add space between the icon and text

            // Notification Text
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f) // Take remaining space
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color(0xFF173251) // Bottom bar color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Navigate to Home */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier
                        .size(50.dp) // Adjust logo size
                        .background(Color(0xFF173251))
                )
            }
            IconButton(onClick = { /* Search action */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier
                        .size(50.dp) // Adjust logo size
                )
            }
            IconButton(onClick = { /* Add content */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier
                        .size(50.dp) // Adjust logo size
                )
            }
            IconButton(onClick = { /* Notification screen */ },
                modifier = Modifier
                    .size(56.dp) // Make the white circle larger
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp) // Make the white circle larger
                        .clip(CircleShape)
                        .background(Color(0xFFFEFDF6)), // White circular background
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.notifications),
                        contentDescription = "Notifications",
                        tint = Color(0xFF173251), // Darker notification icon color
                        modifier = Modifier.size(43.dp) // Icon size remains the same
                    )
                }
            }
            IconButton(onClick = { /* Profile screen */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Profile",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier
                        .size(50.dp) // Adjust logo size
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationsPage() {
    NotificationsPage()
}
