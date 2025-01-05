package com.acm431.teamup.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val userType: String = "",
    val email: String = "",
    val profileImageUrl: String = "", // Profil fotoğrafı URL'si
    val cvUrl: String = "", // CV PDF URL'si
    val extraInfo1: String = "",
    val extraInfo2: String = "",
    val posts: List<String> = emptyList() // Kullanıcının gönderdiği postların ID'leri
)