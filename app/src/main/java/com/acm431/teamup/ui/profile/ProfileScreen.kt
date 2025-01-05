package com.acm431.teamup.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.acm431.teamup.BottomNavigationBar
import com.acm431.teamup.R
import com.acm431.teamup.data.model.Comment
import com.acm431.teamup.data.model.Post
import com.acm431.teamup.data.repository.AuthRepository
import com.acm431.teamup.data.repository.PostRepository
import com.acm431.teamup.data.repository.ProfileRepository
import com.acm431.teamup.ui.home.FileListSection
import com.acm431.teamup.ui.home.timeAgo
import com.acm431.teamup.viewmodel.PostViewModel
import com.acm431.teamup.viewmodel.PostViewModelFactory
import com.acm431.teamup.viewmodel.ProfileViewModel
import com.acm431.teamup.viewmodel.ProfileViewModelFactory

@Composable
fun ProfileScreen(navController: NavHostController, userId: String?) {
    val repository = remember { ProfileRepository() }
    val postRepository = remember { PostRepository() }
    val viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(repository))
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(postRepository))
    val authRepository = remember { AuthRepository() }

    val currentUserId = authRepository.getCurrentUserId() ?: "Unknown"
    val userProfile by viewModel.userProfile.collectAsState()
    val userPosts by viewModel.userPosts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadProfileImage(currentUserId, it)
        }
    }

    LaunchedEffect(userId) {
        val targetUserId = userId ?: currentUserId
        viewModel.loadUserProfile(targetUserId)
    }

    Scaffold(
        topBar = { ProfileTopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        } else {
            userProfile?.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color(0xFFFFFCEF))
                        .padding(16.dp)
                ) {
                    // 🧑‍🦱 **Profil Bilgileri**
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = user.profileImageUrl.ifEmpty { "https://via.placeholder.com/150" },
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "${user.name} ${user.surname}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = user.userType,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // 📷 **Profil Fotoğrafı Değiştir (Yalnızca kendi profilinde göster)**
                    if (currentUserId == userId || userId == null) {
                        Button(
                            onClick = { imagePickerLauncher.launch("image/*") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            Text("Change Profile Photo")
                        }
                    }

                    // 📄 **CV Bölümü**
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (user.userType == "Investor") Color(0xFF0E2C47) else Color(0xFF18517E)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "CV",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Divider(
                                color = Color.White.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = user.extraInfo1,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = user.extraInfo2,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                                DownloadCvButton(user)
                            }
                        }
                    }

                    // 📝 **Postlar Bölümü**
                    Text(
                        text = "Posts",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )

                    // **Postlar için LazyColumn**
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 8.dp)
                    ) {
                        items(userPosts) { post ->
                            LaunchedEffect(post.userId) {
                                postViewModel.loadUserProfileImage(post.userId)
                            }
                            ProfilePost(
                                post = post,
                                isSaved = true,
                                onLike = { },
                                onComment = { },
                                onSave = { },
                                onNavigateToProfile = { id -> navController.navigate("profile/$id") },
                                profileImageUrl = postViewModel.userProfileImages.collectAsState().value[post.userId] ?: "https://via.placeholder.com/150"
                            )
                        }
                    }
                }
            }
        }
    }
}

// **Top Navigation Bar (Centered Logo and App Name with Settings Icon)**
@Composable
fun ProfileTopBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp) // Height for the top bar
            .background(Color(0xFF0E2C47)) // Dark blue background
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // **Centered Content (Logo and App Name)**
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TEAM",
                    color = Color(0xFFF8EECF), // Light cream color
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "UP",
                    color = Color(0xFFF8EECF), // Light cream color
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.width(8.dp)) // Space between text and logo
            Image(
                painter = painterResource(id = R.drawable.teamup_logo2), // Replace with your logo
                contentDescription = "Team Up Logo",
                modifier = Modifier.size(50.dp), // Adjust size
                contentScale = ContentScale.Fit
            )
        }

        // **Settings Icon (Top-Right Corner)**
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Filled.Settings, // Settings icon
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}


@Composable
fun ProfilePost(
    post: Post,
    profileImageUrl: String,
    isSaved: Boolean,
    onLike: () -> Unit,
    onComment: (String) -> Unit,
    onSave: () -> Unit,
    onNavigateToProfile: (String) -> Unit // Yeni parametre
) {
    var isCommentsVisible by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }

    // **userType'a Göre Renk Belirleme**
    val cardBackgroundColor = when (post.userType) {
        "Investor" -> Color(0xFF0E2C47)
        "Student" -> Color(0xFF173251)
        else -> Color.White
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // **Header Row**
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onNavigateToProfile(post.userId) } // Navigasyon eklendi
                ) {
                    AsyncImage(
                        model = profileImageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = post.username,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White // Card arka planına uyumlu olması için
                        )
                        Text(
                            text = post.userType,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.LightGray
                        )
                        Text(
                            text = timeAgo(post.createdAt),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.LightGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // **Title**
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            // **Caption**
            Text(
                text = post.caption,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // **Images Section**
            if (post.photos.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(post.photos.size) { index ->
                        AsyncImage(
                            model = post.photos[index],
                            contentDescription = "Post Image",
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // **Files Section**
            FileListSection(post.files)

            // **Tags Section**
            if (post.tags.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    post.tags.forEach { tag ->
                        Text(
                            text = "#$tag",
                            color = Color.Cyan,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // **Like, Comment ve Save Buttons**
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onLike) {
                        Icon(Icons.Default.Favorite, contentDescription = "Like", tint = Color.Red)
                    }
                    Text("${post.likes.size} Likes", color = Color.White)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Chat, contentDescription = "Comment", tint = Color.Gray)
                    }
                    Text(text = "${post.comments.size} Comments", color = Color.White)
                }
            }
        }
    }
}

// **Reusable Info Card Component**
@Composable
fun ProfileInfoCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                color = Color(0xFF274472)
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

// **Reusable Post Item Component**
@Composable
fun ProfilePostItem(
    username: String,
    userType: String,
    timeAgo: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("$username - $userType", fontWeight = FontWeight.Bold)
            Text(timeAgo, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(description)
        }
    }
}













