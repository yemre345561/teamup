package com.acm431.teamup.data.model

data class Post(
    val id: String = "", // Automatically assigned by Firebase
    val title: String = "",
    val caption: String = "",
    val photos: List<String> = emptyList(), // URLs of uploaded photos
    val files: List<String> = emptyList(), // URLs of uploaded files
    val tags: List<String> = emptyList(), // List of tags
    val userId: String = "", // ID of the user who created the post
    val username: String = "", // Username of the user
    val userType: String = "", // Type of the user (e.g., Investor, Student)
    val createdAt: Long = System.currentTimeMillis(), // Timestamp of post creation
    val likes: List<String> = emptyList(), // List of userIds who liked the post
    val comments: Map<String, Comment> = emptyMap() // HashMap yerine Map kullan
)

data class Comment(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val comment: String = "",
    val createdAt: Long = System.currentTimeMillis()
)