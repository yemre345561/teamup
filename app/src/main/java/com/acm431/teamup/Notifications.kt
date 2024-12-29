package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@Composable
fun NotificationsPage(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCEF)) // Background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // **Custom Top Navigation Bar**
            TopNavigationBar()

            // Header Section
            NotificationsHeader()

            // Notification List
            NotificationList()
        }

        // **Custom Bottom Navigation Bar**
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomNavigationBar(navController = navController)
        }
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
        // Divider Replacement: Spacer and Box
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp) // Same thickness as Divider
                .background(Color(0xFF173251)) // Same color as before
        )
    }
}

@Composable
fun NotificationList() {
    // Placeholder notifications
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
        contentAlignment = Alignment.Center // Center horizontally and vertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Set width for notification item
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF173251)) // Background color for the box
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular Image/Icon on the Left
            Box(
                modifier = Modifier
                    .size(40.dp) // Size of the circle
                    .clip(CircleShape)
                    .background(Color(0xFF8FA8BF)) // Circle color
            )

            Spacer(modifier = Modifier.width(8.dp))

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



