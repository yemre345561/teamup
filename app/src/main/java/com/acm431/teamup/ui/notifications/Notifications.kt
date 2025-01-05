package com.acm431.teamup.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.acm431.teamup.BottomNavigationBar
import com.acm431.teamup.TopNavigationBar
import com.acm431.teamup.data.repository.NotificationRepository
import com.acm431.teamup.ui.home.timeAgo
import com.acm431.teamup.viewmodel.AuthViewModel
import com.acm431.teamup.viewmodel.AuthViewModelFactory
import com.acm431.teamup.viewmodel.NotificationViewModel
import com.acm431.teamup.viewmodel.NotificationViewModelFactory

@Composable
fun NotificationsPage(navController: NavHostController) {
    val repository = remember { NotificationRepository() }
    val viewModel: NotificationViewModel = viewModel(factory = NotificationViewModelFactory(repository))
    val authRepository = remember { com.acm431.teamup.data.repository.AuthRepository() }
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val notifications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNotifications(authRepository.getCurrentUserId() ?: "Unknown")
    }

    Scaffold(
        topBar = { TopNavigationBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (isLoading) {
                Text("Loading notifications...", color = Color.Gray)
            } else {
                LazyColumn {
                    items(notifications) { notification ->
                        NotificationItem(notification.message,profileImageUrl = notification.profileImageUrl,notification.createdAt )// Profil fotoğrafı URL'si burada
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(message: String, profileImageUrl: String?, createdAt: Long) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF18517E), RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profil Fotoğrafı
        if (!profileImageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = profileImageUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF8FA8BF), CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            // Bildirim Mesajı
            Text(
                text = message,
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Bildirim Zamanı
            Text(
                text = timeAgo(createdAt),
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}