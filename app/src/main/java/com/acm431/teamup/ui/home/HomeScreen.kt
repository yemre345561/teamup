package com.acm431.teamup.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.acm431.teamup.BottomNavigationBar
import com.acm431.teamup.TopNavigationBar
import com.acm431.teamup.data.model.Post
import com.acm431.teamup.data.repository.PostRepository
import com.acm431.teamup.viewmodel.PostViewModel
import com.acm431.teamup.viewmodel.PostViewModelFactory
import coil.compose.AsyncImage
import com.acm431.teamup.data.model.Comment
import com.acm431.teamup.data.repository.AuthRepository
import com.acm431.teamup.viewmodel.AuthViewModel
import com.acm431.teamup.viewmodel.AuthViewModelFactory

@Composable
fun HomeScreen(navController: NavHostController) {

    val postRepository = remember { PostRepository() }

    val authRepository = remember { AuthRepository() }
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(postRepository))
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))

    val userDetails by authViewModel.userDetails.collectAsState()
    val posts by postViewModel.posts.collectAsState()
    val isLoading by postViewModel.isLoading.collectAsState()
    val savedPosts by postViewModel.savedPosts.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.loadUserDetails()
        postViewModel.loadSavedPosts(authRepository.getCurrentUserId() ?: "Unknown")
    }

    Scaffold(
        topBar = { TopNavigationBar() },
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color(0xFFF9F5E7)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn {
                    items(posts) { post ->
                        LaunchedEffect(post.userId) {
                            postViewModel.loadUserProfileImage(post.userId)
                        }

                        HomePostItem(
                            post = post,
                            currentUserId = authRepository.getCurrentUserId() ?: "Unknown",
                            profileImageUrl = postViewModel.userProfileImages.collectAsState().value[post.userId] ?: "https://via.placeholder.com/150",
                            isSaved = savedPosts.contains(post.id),
                            onLike = { postViewModel.toggleLike(post.id, authRepository.getCurrentUserId() ?: "Unknown","${userDetails?.get("name") ?: "Unknown"} ${userDetails?.get("surname") ?: ""}",post.userId,userDetails?.get("profileImageUrl") ?: "https://picsum.photos/200") },
                            onComment = { commentText ->
                                val comment = Comment(userId = authRepository.getCurrentUserId() ?: "Unknown", username = "${userDetails?.get("name") ?: "Unknown"} ${userDetails?.get("surname") ?: ""}", comment = commentText)
                                postViewModel.addComment(post.id, comment,post.userId,authRepository.getCurrentUserId() ?: "Unknown",
                                    userDetails?.get("profileImageUrl") ?: "https://picsum.photos/200"
                                )
                            },
                            onSave = {
                                postViewModel.toggleSavePost(
                                    post.id,
                                    authRepository.getCurrentUserId() ?: "Unknown"
                                )
                            },
                            onNavigateToProfile = { userId ->
                                if (userId == authRepository.getCurrentUserId()) {
                                    navController.navigate("profile")
                                } else {
                                    navController.navigate("profile/$userId")
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun HomePostItem(
    post: Post,
    currentUserId: String,
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
                    IconButton(onClick = { isCommentsVisible = !isCommentsVisible }) {
                        Icon(Icons.Default.Chat, contentDescription = "Comment", tint = Color.Gray)
                    }
                    Text(text = "${post.comments.size} Comments", color = Color.White)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.Link,
                            contentDescription = "Save",
                            tint = if (isSaved) Color.Yellow else Color.Gray
                        )
                    }
                    Text(if (isSaved) "Saved" else "Save", color = Color.White)
                }
            }

            if (isCommentsVisible) {
                CommentSection(
                    comments = post.comments,
                    onComment = onComment
                )
            }
        }
    }
}

@Composable
fun FileListSection(files: List<String>) {
    val context = LocalContext.current

    if (files.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Files:",
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.LightGray
            )
            files.forEach { fileUrl ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl))
                            context.startActivity(intent)
                        }
                        .padding(vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Link, contentDescription = "File", tint = Color.Cyan)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = fileUrl,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        color = Color.Cyan,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// **Time Ago Function**
@Composable
fun timeAgo(createdAt: Long): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - createdAt

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes minutes ago"
        else -> "Just now"
    }
}

@Composable
fun CommentSection(
    comments: Map<String, Comment>,
    onComment: (String) -> Unit
) {
    var commentText by remember { mutableStateOf("") }
    val sortedComments = remember { comments.values.sortedByDescending { it.createdAt } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Comments",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .heightIn(max = 200.dp)
                .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            items(sortedComments) { comment ->
                CommentItem(comment = comment)
                Divider(color = Color.Gray, thickness = 0.5.dp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Yeni Yorum Ekleme
        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            placeholder = { Text("Add a comment", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Cyan,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Cyan
            )
        )

        Button(
            onClick = {
                onComment(commentText)
                commentText = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Send", color = Color.White)
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column {
            Text(
                text = comment.username,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = comment.comment,
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
            Text(
                text = timeAgo(comment.createdAt),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}