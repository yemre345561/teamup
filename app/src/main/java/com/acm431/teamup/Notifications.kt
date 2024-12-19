package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationsPage(navController: NavHostController) {
    Scaffold(
        topBar = { TopNavigationBar() }, // Removed navController
        bottomBar = { BottomNavigationBar(navController = navController) } // Keeps navController for bottom navigation
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFEFDF6)) // Background color
                .verticalScroll(rememberScrollState()) // Enable scrolling
        ) {
            // Notifications Header
            NotificationsHeader()

            // Notification List
            NotificationList()
        }
    }
}

@Composable
fun NotificationsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Notifications",
            fontSize = 24.sp,
            color = Color(0xFF173251),
            style = MaterialTheme.typography.headlineMedium
        )
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 2.dp,
            color = Color(0xFF173251)
        )
    }
}

@Composable
fun NotificationList() {
    val notifications = List(10) { "Notification #${it + 1}" }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        notifications.forEach { notification ->
            NotificationItem(text = notification)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NotificationItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF173251))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF8FA8BF))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}




