package com.acm431.teamup

// Required Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.input.ImeAction


@Composable
fun SearchScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }
    val searchHistory = remember { mutableStateListOf<String>() }
    val searchResults = remember { mutableStateListOf<SearchResult>() }

    Scaffold(
        topBar = { TopNavigationBar() }, // Top Bar
        bottomBar = { BottomNavigationBar(navController) }, // Bottom Bar
        containerColor = Color(0xFFFFFBEF) // Background color
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // **Search Bar**
            OutlinedTextField(
                value = searchText,
                onValueChange = { query -> searchText = query },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.isNotEmpty() && !searchHistory.contains(searchText)) {
                            searchHistory.add(searchText)
                            searchResults.clear()
                            searchResults.addAll(performSearch(searchText))
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // **Search History**
            for (keyword in searchHistory) {
                SearchHistoryItem(
                    keyword = keyword,
                    onDelete = { searchHistory.remove(keyword) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // **Search Results**
            if (searchResults.isEmpty()) {
                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                for (result in searchResults) {
                    SearchResultItem(
                        username = result.username,
                        userType = result.userType,
                        timeAgo = result.timeAgo,
                        description = result.description,
                        likes = 234,
                        comments = 32
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// **Search History Item Component**
@Composable
fun SearchHistoryItem(keyword: String, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = keyword, style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}

// **Search Result Item Component**
@Composable
fun SearchResultItem(
    username: String,
    userType: String,
    timeAgo: String,
    description: String,
    likes: Int,
    comments: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // **Header Row**
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = username, style = MaterialTheme.typography.bodyLarge)
                    Text(text = userType, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Text(
                    text = timeAgo,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // **Description**
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // **Actions Row**
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = Color.Red
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "Comment",
                        tint = Color.Blue
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "Save",
                        tint = Color.Green
                    )
                }
            }
        }
    }
}

// **Sample Data**
data class SearchResult(
    val username: String,
    val userType: String,
    val timeAgo: String,
    val description: String
)

fun performSearch(query: String): List<SearchResult> {
    return if (query.isNotEmpty()) {
        listOf(
            SearchResult("Joshua Smith", "Investor", "2h ago", "Looking for a co-founder for my startup."),
            SearchResult("Alice Johnson", "Designer", "5h ago", "Looking for freelance design opportunities."),
            SearchResult("Charlie Lee", "Developer", "1d ago", "Searching for a full-stack project to collaborate on.")
        ).filter { it.username.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true) }
    } else {
        emptyList()
    }
}






