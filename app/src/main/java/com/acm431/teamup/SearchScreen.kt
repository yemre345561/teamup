package com.acm431.teamup.ui.search

import SearchViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.acm431.teamup.BottomNavigationBar
import com.acm431.teamup.TopNavigationBar
import com.acm431.teamup.data.model.Comment
import com.acm431.teamup.data.model.Post
import com.acm431.teamup.data.repository.AuthRepository
import com.acm431.teamup.data.repository.PostRepository
import com.acm431.teamup.ui.home.HomePostItem
import com.acm431.teamup.ui.home.timeAgo
import com.acm431.teamup.viewmodel.AuthViewModel
import com.acm431.teamup.viewmodel.AuthViewModelFactory
import com.acm431.teamup.viewmodel.PostViewModel
import com.acm431.teamup.viewmodel.PostViewModelFactory
import com.acm431.teamup.viewmodel.SearchViewModelFactory

@Composable
fun SearchScreen(navController: NavHostController) {
    // Repository ve ViewModel Tanımları
    val postRepository = remember { PostRepository() }
    val authRepository = remember { AuthRepository() }
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(postRepository))
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModelFactory(postRepository))

    // State Değişkenleri
    val userDetails by authViewModel.userDetails.collectAsState()
    val searchText = remember { mutableStateOf("") }
    val searchResults by searchViewModel.searchResults.collectAsState()
    val searchHistory by searchViewModel.searchHistory.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    val savedPosts by postViewModel.savedPosts.collectAsState()

    // Kullanıcı Detayları Yükle
    LaunchedEffect(Unit) {
        authViewModel.loadUserDetails()
        postViewModel.loadSavedPosts(authRepository.getCurrentUserId() ?: "Unknown")
    }

    Scaffold(
        topBar = { TopNavigationBar() },
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color(0xFFFFFBEF)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // **Search Bar**
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { query -> searchText.value = query },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.value.isNotEmpty()) {
                            searchViewModel.performSearch(searchText.value, authRepository.getCurrentUserId() ?: "Unknown")
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // **Keywords Section**
            if (searchHistory.isNotEmpty()) {
                Text(
                    text = "Keywords",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                searchHistory.forEach { keyword ->
                    SearchHistoryItem(
                        keyword = keyword,
                        onClick = { searchViewModel.performSearch(keyword, authRepository.getCurrentUserId() ?: "Unknown") },
                        onDelete = { searchViewModel.deleteSearchKeyword(authRepository.getCurrentUserId() ?: "Unknown", keyword) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // **Search Results**
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                if (searchResults.isEmpty()) {
                    Text(
                        text = "No results found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn {
                        items(searchResults) { post ->
                            LaunchedEffect(post.userId) {
                                postViewModel.loadUserProfileImage(post.userId)
                            }
                            HomePostItem(
                                post = post,
                                currentUserId = authRepository.getCurrentUserId() ?: "Unknown",
                                profileImageUrl = postViewModel.userProfileImages.collectAsState().value[post.userId] ?: "https://via.placeholder.com/150",
                                isSaved = savedPosts.contains(post.id),
                                onLike = {
                                    postViewModel.toggleLike(
                                        post.id,
                                        authRepository.getCurrentUserId() ?: "Unknown",
                                        "${userDetails?.get("name") ?: "Unknown"} ${userDetails?.get("surname") ?: ""}",
                                        post.userId,
                                        userDetails?.get("profileImageUrl") ?: "https://picsum.photos/200"
                                    )
                                },
                                onComment = { commentText ->
                                    val comment = Comment(
                                        userId = authRepository.getCurrentUserId() ?: "Unknown",
                                        username = "${userDetails?.get("name") ?: "Unknown"} ${userDetails?.get("surname") ?: ""}",
                                        comment = commentText
                                    )
                                    postViewModel.addComment(
                                        post.id,
                                        comment,
                                        post.userId,
                                        realUserId = authRepository.getCurrentUserId() ?: "Unknown",
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
                                },
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun SearchHistoryItem(keyword: String, onClick: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .clickable { onClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = keyword, style = MaterialTheme.typography.bodyMedium)
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Delete")
        }
    }
}

fun formatTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val difference = now - timestamp
    return when {
        difference < 60_000 -> "Just now"
        difference < 3_600_000 -> "${difference / 60_000} minutes ago"
        difference < 86_400_000 -> "${difference / 3_600_000} hours ago"
        else -> "${difference / 86_400_000} days ago"
    }
}

