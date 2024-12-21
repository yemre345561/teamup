package com.acm431.teamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

class SavedPostsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SavedPostsScreen()
        }
    }
}

@Composable
fun SavedPostsScreen() {
    androidx.compose.material3.Scaffold(
        bottomBar = { SavedPostsBottomNavigationBar() } // Bottom bar'ı Scaffold'a ekledik
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFEFDF6)) // Arka plan rengi
                .padding(paddingValues) // Scaffold padding'leri içerik için uygulandı
        ) {
            // Header Section
            SavedPostsHeaderBar() // Üstteki lacivert bar
            HeaderWithDivider("Saved Posts") // Başlık ve alt çizgi

            // İçerik (Boş)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved posts yet.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFB0BEC5)
                )
            }
        }
    }
}

@Composable
fun SavedPostsHeaderBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00264D)) // Lacivert arka plan
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Logo ve başlık
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.teamuplogopng),
                contentDescription = "TeamUp Logo",
                modifier = Modifier.height(50.dp)
            )
        }
    }
}

@Composable
fun HeaderWithDivider(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp) // Başlık ve çizgi arasındaki boşluklar
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00264D) // Koyu mavi renk
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp), // Çizgi ile başlık arası boşluk
            thickness = 1.dp,
            color = Color(0xFFB0BEC5)
        )
    }
}

@Composable
fun SavedPostsBottomNavigationBar() {
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
                    painter = painterResource(R.drawable.home_24dp_0d2c45_fill0_wght400_grad0_opsz24),
                    contentDescription = "Home",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier
                        .size(50.dp) // Adjust logo size
                        .background(Color(0xFF173251))
                )
            }
            IconButton(onClick = { /* Search action */ }) {
                Icon(
                    painter = painterResource(R.drawable.search_24dp_0d2c45_fill0_wght400_grad0_opsz24),
                    contentDescription = "Search",
                    tint = Color(0xFFFEFDF6),
                    modifier = Modifier
                        .size(50.dp) // Adjust logo size
                )
            }
            IconButton(onClick = { /* Add content */ }) {
                Icon(
                    painter = painterResource(R.drawable.add_circle_24dp_0d2c45_fill0_wght400_grad0_opsz24),
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
                        .background(Color(0xFF173251)), // White circular background
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.notifications_24dp_0d2c45_fill0_wght400_grad0_opsz24),
                        contentDescription = "Notifications",
                        tint = Color(0xFFFEFDF6), // Darker notification icon color
                        modifier = Modifier.size(43.dp) // Icon size remains the same
                    )
                }
            }
            IconButton(onClick = { /* Profile screen */ }) {
                Icon(
                    painter = painterResource(R.drawable.person_24dp_0d2c45_fill0_wght400_grad0_opsz24),
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
fun PreviewSavedPostsScreen() {
    SavedPostsScreen()
}
