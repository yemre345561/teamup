package com.example.teamup
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivityInvestor : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeamUpApp2()
        }
    }
}

@Composable
fun TeamUpApp2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCEF))
    ) {

        UpNavigationBar2()

        Spacer(modifier = Modifier.height(16.dp))

        // Post List
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            repeat(2) { // 2 ilan
                PostCard2()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }


        BottomNavigationBar2()
    }
}

@Composable
fun UpNavigationBar2() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D2C45))
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "TeamUp Logo",
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun PostCard2() {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF18507E))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                )
                Column {
                    Text("Joshua Smith", fontWeight = FontWeight.Bold, color = Color(0xFF3DC0D1))
                    Text("Investor", color = Color.White, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("2h Ago", color = Color.White, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Student Description Placeholder Placeholder Placeholder Placeholder",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Image(
                    painter = painterResource(id = R.drawable.ilan),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1.5f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ilan2),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1.5f),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                IconButton(onClick = { /* TODO: Like action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = "Like",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text("234", color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { /* TODO: Comment action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.comment),
                        contentDescription = "Comment",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text("32", color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* TODO: Bookmark action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "Bookmark",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar2() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D2C45))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = { /* TODO: Home action */ }) {
            Icon(painter = painterResource(id = R.drawable.home), contentDescription = "Home", tint = Color.White)
        }
        IconButton(onClick = { /* TODO: Search action */ }) {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = "Search", tint = Color.White)
        }
        IconButton(onClick = { /* TODO: Add action */ }) {
            Icon(painter = painterResource(id = R.drawable.add), contentDescription = "Add", tint = Color.White)
        }
        IconButton(onClick = { /* TODO: Notifications action */ }) {
            Icon(painter = painterResource(id = R.drawable.notifications), contentDescription = "Notifications", tint = Color.White)
        }
        IconButton(onClick = { /* TODO: Profile action */ }) {
            Icon(painter = painterResource(id = R.drawable.person), contentDescription = "Profile", tint = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTeamUpApp2() {
    TeamUpApp2()
}
