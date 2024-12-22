package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SharePostScreen(navController: NavHostController) {
    Scaffold(
        topBar = { SharePostTopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        SharePostContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun SharePostTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "TEAM UP",
                color = Color.White,
                fontSize = 20.sp
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF274472))
    )
}

@Composable
fun SharePostContent(modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var caption by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9F5E7))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = caption,
            onValueChange = { caption = it },
            label = { Text("Caption") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Photos", fontSize = 16.sp)
            Button(onClick = { /* Handle adding photos */ }) {
                Text("Add")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Files", fontSize = 16.sp)
            Button(onClick = { /* Handle adding files */ }) {
                Text("Add")
            }
        }

        OutlinedTextField(
            value = tags,
            onValueChange = { tags = it },
            label = { Text("Tags") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /* Handle post submission */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Post")
        }
    }
}
