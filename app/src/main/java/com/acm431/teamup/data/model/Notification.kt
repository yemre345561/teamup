package com.acm431.teamup.data.model

data class Notification(
    val id: String = "",       // Firebase tarafından atanacak
    val userId: String = "",   // Bildirimin gideceği kullanıcı ID
    val realUserId: String = "", // Bildirimi gönderen kullanıcı id
    val postId: String = "",   // Bildirime neden olan paylaşım ID
    val type: String = "",     // "like", "comment"
    val message: String = "",  // Bildirim metni
    val createdAt: Long = System.currentTimeMillis(),
    val profileImageUrl: String? = null // Profil fotoğrafı URL'si
)