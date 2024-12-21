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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LogOutScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogoutScreen()
        }
    }
}

@Composable
fun HeaderWithDivider() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp) // Dış boşluklar
    ) {
        Text(
            text = "Log Out", // Başlık metni
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00264D) // Koyu mavi renk
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 2.dp), // Çizgi ile başlık arası boşluk
            thickness = 1.dp, // Çizgi kalınlığı
            color = Color(0xFFB0BEC5) // Çizgi rengi
        )
    }
}


@Composable
fun LogoutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFDF6)),
        verticalArrangement = Arrangement.SpaceBetween

                // Header Section


    ) {
        // Header Section
        Column {
            HeaderBar()
            HeaderWithDivider()
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color(0xFFFEFDF6)
            )
        }


        // Logout Message and Buttons
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Are you sure you want to",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00264D),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 0.dp)
            )
            Text(
                text = "Log Out?",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00264D),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                // Yes Button
                Button(
                    onClick = { /* TODO: Handle Yes Click */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // Arka plan rengi
                        contentColor = Color(0xFF00264D) // Metin rengi
                    ),
                    shape = RoundedCornerShape(50), // Oval kenarlar
                    modifier = Modifier
                        .defaultMinSize(minWidth = 100.dp)
                        .height(48.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(50)) // Gölge
                ) {
                    Text("Yes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                // No Button
                Button(
                    onClick = { /* TODO: Handle No Click */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF173251), // Arka plan rengi
                        contentColor = Color.White // Metin rengi
                    ),
                    shape = RoundedCornerShape(50), // Oval kenarlar
                    modifier = Modifier
                        .defaultMinSize(minWidth = 100.dp)
                        .height(48.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(50)) // Gölge
                ) {
                    Text("No", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

        }

        // Bottom Navigation Bar
        CustomBottomNavigationBar()
    }
}


@Composable
fun HeaderBar() {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF00264D))
                .padding(16.dp),
            contentAlignment = Alignment.Center // Doğru hizalama
        )
    {


        // Logo Image
        Image(painter = painterResource(id = R.drawable.teamuplogopng),
        contentDescription = "TeamUp Logo",
        modifier = Modifier.height(50.dp)
        )


    }
}

@Composable
fun CustomBottomNavigationBar() {
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

// Top-level Preview fonksiyonu
@Preview(showBackground = true)
@Composable
fun PreviewLogOutScreen() {
    LogoutScreen()
}


