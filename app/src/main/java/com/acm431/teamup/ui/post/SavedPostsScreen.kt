package com.acm431.teamup.ui.post

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.acm431.teamup.BottomNavigationBar
import com.acm431.teamup.R
import com.acm431.teamup.data.model.Comment
import com.acm431.teamup.data.repository.AuthRepository
import com.acm431.teamup.ui.home.HomePostItem
import com.acm431.teamup.viewmodel.PostViewModel


//class SavedPostsActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//            SavedPostsScreen(navController)
//        }
//    }
//}

// **Main Saved Posts Screen**
@Composable
fun SavedPostsScreen(navController: NavHostController, postViewModel: PostViewModel, authRepository: AuthRepository) {
    val userId = authRepository.getCurrentUserId() ?: "Unknown"
    val savedPosts by postViewModel.savedPosts.collectAsState()
    val isLoading by postViewModel.isLoading.collectAsState()
    val posts by postViewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        postViewModel.loadSavedPosts(userId)
    }
    Scaffold(
        topBar = { SavedPostsTopBar(navController) }, // Custom Top Bar with Back Arrow
        bottomBar = { BottomNavigationBar(navController) }, // Existing Bottom Bar
        containerColor = Color(0xFFFFFCEF) // Background Color
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (savedPosts.isEmpty()) {
                Text(
                    text = "No saved posts found.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 16.sp
                )
            } else {
                LazyColumn {
                    items(posts.filter { savedPosts.contains(it.id) }) { post ->
                        LaunchedEffect(post.userId) {
                            postViewModel.loadUserProfileImage(post.userId)
                        }

                        HomePostItem(
                            post = post,
                            currentUserId = userId,
                            isSaved = true,
                            onLike = { postViewModel.toggleLike(post.id, userId, post.username, post.userId, post.photos.firstOrNull() ?: "") },
                            onComment = { commentText ->
                                val comment = Comment(userId = userId, username = post.username, comment = commentText)
                                postViewModel.addComment(post.id, comment, post.userId,authRepository.getCurrentUserId() ?: "Unknown", post.photos.firstOrNull() ?: "")
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
                            },
                            profileImageUrl = postViewModel.userProfileImages.collectAsState().value[post.userId] ?: "https://via.placeholder.com/150",

                            )
                    }
                }
            }
        }
    }
}

// **Top Bar with Back Arrow and Centered Logo and Name**
@Composable
fun SavedPostsTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E2C47)) // Dark blue background
            .height(70.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // **Back Arrow**
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // **Centered Logo and App Name**
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TEAM",
                    color = Color(0xFFF8EECF), // Light cream
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "UP",
                    color = Color(0xFFF8EECF),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.teamup_logo2),
                contentDescription = "Logo",
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.size(48.dp)) // Placeholder for symmetry
    }
}

//// **Preview**
//@Preview(showBackground = true)
//@Composable
//fun PreviewSavedPostsScreen() {
//    val navController = rememberNavController()
//    SavedPostsScreen(navController)
//}




