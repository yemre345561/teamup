package com.acm431.teamup.ui.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.acm431.teamup.BottomNavigationBar
import com.acm431.teamup.data.repository.PostRepository
import com.acm431.teamup.viewmodel.AuthViewModel
import com.acm431.teamup.viewmodel.AuthViewModelFactory
import com.acm431.teamup.viewmodel.PostViewModel
import com.acm431.teamup.viewmodel.PostViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun SharePostScreen(navController: NavHostController) {
    val repository = remember { PostRepository() }
    val viewModel: PostViewModel = viewModel(factory = PostViewModelFactory(
        repository
    ))
    val authRepository = remember { com.acm431.teamup.data.repository.AuthRepository() }
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))

    val userDetails by authViewModel.userDetails.collectAsState()
    LaunchedEffect(Unit) {
        authViewModel.loadUserDetails()
    }
    val coroutineScope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var caption by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    val photos = remember { mutableStateListOf<Uri>() }
    val files = remember { mutableStateListOf<Uri>() }

    var isPosting by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { selectedPhotos ->
        photos.clear()
        photos.addAll(selectedPhotos)
    }

    val filePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { selectedFiles ->
        files.clear()
        files.addAll(selectedFiles)
    }

    Scaffold(
        topBar = { SharePostTopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Başlık
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Açıklama
            OutlinedTextField(
                value = caption,
                onValueChange = { caption = it },
                label = { Text("Caption") },
                modifier = Modifier.fillMaxWidth()
            )

            // Fotoğraf Ekleme
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column { // Added a column to stack text and button
                    Text("Photos", fontSize = 16.sp)
                    if (photos.isNotEmpty()) {
                        Text(
                            text = photos.joinToString(",") { it.lastPathSegment ?: "File" }, // Display filenames
                            fontSize = 12.sp,
                            color = Color.Gray // Slightly smaller and grayed out
                        )
                    }
                }
                Button(onClick = { photoPickerLauncher.launch("image/*") }) {
                    Text("Add Photos")
                }
            }

            // Dosya Ekleme
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column { // Added a column to stack text and button
                    Text("Files", fontSize = 16.sp)
                    if (files.isNotEmpty()) {
                        Text(
                            text = files.joinToString(",") { it.lastPathSegment ?: "File" }, // Display filenames
                            fontSize = 12.sp,
                            color = Color.Gray // Slightly smaller and grayed out
                        )
                    }
                }
                Button(onClick = { filePickerLauncher.launch("*/*") }) {
                    Text("Add Files")
                }
            }

            // Etiketler
            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                label = { Text("Tags (- separated)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gönder Butonu
            Button(
                onClick = {
                    coroutineScope.launch {
                        isPosting = true
                        val tagList = tags.split("-").map { it.trim() }.filter { it.isNotEmpty() }
                        viewModel.createPostWithMedia(
                            title = title,
                            caption = caption,
                            photos = photos,
                            files = files,
                            tags = tagList.joinToString(","),
                            userId = authRepository.getCurrentUserId() ?: "Unknown",
                            username = "${userDetails?.get("name") ?: "Unknown"} ${userDetails?.get("surname") ?: ""}",
                            userType = userDetails?.get("userType") ?: "Unknown"
                        )
                        isPosting = false
                        showSuccessDialog = true

                        kotlinx.coroutines.delay(5000)

                        // Bir önceki sayfaya dön
                        navController.navigateUp()
                    }
                },
                enabled = !isPosting,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(if (isPosting) "Posting..." else "Share Post")
            }
        }

        // Başarı Geri Bildirimi
//        if (showSuccessDialog) {
//            AlertDialog(
//                onDismissRequest = {
//                    showSuccessDialog = false
//                    navController.navigateUp()
//                },
//                title = { Text("Success") },
//                text = { Text("Your post has been successfully shared!") },
//                confirmButton = {
//                    Button(
//                        onClick = {
//                            showSuccessDialog = false
//                            navController.navigateUp()
//                        }
//                    ) {
//                        Text("OK")
//                    }
//                }
//            )
//        }
    }
}

// **Top Bar**
@Composable
fun SharePostTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF0E2C47))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("TEAM", color = Color(0xFFF8EECF), fontSize = 18.sp)
                Text("UP", color = Color(0xFFF8EECF), fontSize = 18.sp)
            }
            Image(
                painter = painterResource(id = com.acm431.teamup.R.drawable.teamup_logo2),
                contentDescription = "Logo",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}
