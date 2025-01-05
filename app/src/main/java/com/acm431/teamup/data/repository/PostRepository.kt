package com.acm431.teamup.data.repository

import android.net.Uri
import com.acm431.teamup.data.model.Comment
import com.acm431.teamup.data.model.Notification
import com.acm431.teamup.data.model.Post
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class PostRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("posts")
    private val dbSearchHistory: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference


    suspend fun toggleSavePost(postId: String, userId: String) {
        val savedPostsRef = FirebaseDatabase.getInstance()
            .getReference("savedPosts")
            .child(userId)

        val snapshot = savedPostsRef.get().await()
        val savedPosts = snapshot.children.mapNotNull { it.key }.toMutableList()

        if (savedPosts.contains(postId)) {
            // Postu kaldır
            savedPostsRef.child(postId).removeValue().await()
        } else {
            // Postu kaydet
            savedPostsRef.child(postId).setValue(true).await()
        }
    }

    suspend fun getSavedPosts(userId: String): List<Post> {
        val savedPostsRef = FirebaseDatabase.getInstance()
            .getReference("savedPosts")
            .child(userId)

        val snapshot = savedPostsRef.get().await()
        val postIds = snapshot.children.mapNotNull { it.key }

        val posts = mutableListOf<Post>()
        for (postId in postIds) {
            val postSnapshot = database.child(postId).get().await()
            postSnapshot.getValue(Post::class.java)?.let { posts.add(it) }
        }
        return posts
    }

    private suspend fun uploadFilesToFirebaseStorage(uris: List<Uri>, folder: String): List<String> {
        val urls = mutableListOf<String>()
        for (uri in uris) {
            val fileName = uri.lastPathSegment ?: "file_${System.currentTimeMillis()}"
            val storageRef = storage.reference.child("$folder/$fileName")

            try {
                storageRef.putFile(uri).await()
                val downloadUrl = storageRef.downloadUrl.await().toString()
                urls.add(downloadUrl)
                println("File uploaded: $downloadUrl")
            } catch (e: Exception) {
                println("Failed to upload file: ${e.message}")
            }
        }
        return urls
    }

    fun getSortedComments(post: Post): List<Comment> {
        return post.comments.values
            .sortedByDescending { it.createdAt } // createdAt'e göre azalan sırala
    }
    // 📝 Post Oluşturma
    suspend fun createPostWithMedia(
        title: String,
        caption: String,
        photos: List<Uri>,
        files: List<Uri>,
        tags: String,
        userId: String,
        username: String,
        userType: String
    ): Boolean {
        return try {
            val photoUrls = uploadFilesToFirebaseStorage(photos, "photos")
            val fileUrls = uploadFilesToFirebaseStorage(files, "files")

            // Eğer fotoğraf veya dosya URL'leri boşsa işlem yapılmaz
            if (photoUrls.isEmpty() && fileUrls.isEmpty()) {
                println("Error: Photo or File URLs are empty after upload.")
                return false
            }

            val tagsList = tags.split("-").map { it.trim() }

            val postId = database.push().key ?: return false

            val post = Post(
                id = postId,
                title = title,
                caption = caption,
                photos = photoUrls,
                files = fileUrls,
                tags = tagsList,
                userId = userId,
                username = username,
                userType = userType
            )

            database.child(postId).setValue(post).await()
            println("Post successfully written to database with ID: $postId")
            true
        } catch (e: Exception) {
            println("Create Post Error: ${e.message}")
            false
        }
    }
    // Paylaşım oluştur
    suspend fun createPost(post: Post): Boolean {
        return try {
            val postId = database.push().key ?: return false
            database.child(postId).setValue(post.copy(id = postId)).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Tüm paylaşımları çek
    suspend fun getPosts(): List<Post> {
        return try {
            val snapshot = database.get().await()
            snapshot.children.mapNotNull { it.getValue(Post::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Belirli kullanıcı türüne göre paylaşımları çek
    suspend fun getPostsByUserType(userType: String): List<Post> {
        return try {
            val snapshot = database.orderByChild("userType").equalTo(userType).get().await()
            snapshot.children.mapNotNull { it.getValue(Post::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

//    suspend fun searchPosts(query: String): List<Post> {
//        return try {
//            val snapshot = database.get().await()
//            snapshot.children.mapNotNull { it.getValue(Post::class.java) }
//                .filter { post ->
//                    post.title.contains(query, ignoreCase = true) ||
//                            post.caption.contains(query, ignoreCase = true) ||
//                            post.tags.any { it.contains(query, ignoreCase = true) }
//                }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }

    private val postsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val postList = snapshot.children.mapNotNull { it.getValue(Post::class.java) }
                .sortedByDescending { it.createdAt } // createdAt'e göre azalan sırala
            _posts.value = postList
        }

        override fun onCancelled(error: DatabaseError) {
            println("Failed to listen to posts: ${error.message}")
        }
    }

    // Realtime Post Dinleme Başlat
    fun startListeningToPosts() {
        database.orderByChild("createdAt").addValueEventListener(postsListener)
    }

    // Realtime Post Dinleme Durdur
    fun stopListeningToPosts() {
        database.removeEventListener(postsListener)
    }

    // Beğeni ekle veya kaldır
//    suspend fun toggleLike(postId: String, userId: String) {
//        val likesRef = database.child(postId).child("likes")
//        val snapshot = likesRef.get().await()
//        val likes = snapshot.getValue<List<String>>() ?: emptyList()
//
//        val updatedLikes = if (likes.contains(userId)) {
//            likes - userId // Unlike
//        } else {
//            likes + userId // Like
//        }
//
//        likesRef.setValue(updatedLikes).await()
//    }

    // Beğeni Ekle
    suspend fun toggleLike(postId: String, userId: String, username: String, ownerId: String,profileImageUrl: String) {
        val postRef = database.child(postId)
        val snapshot = postRef.child("likes").get().await()
        val likes = snapshot.children.map { it.getValue(String::class.java) }.filterNotNull().toMutableList()

        if (likes.contains(userId)) {
            likes.remove(userId)
        } else {
            likes.add(userId)
            val notification = Notification(
                userId = ownerId,
                realUserId = userId,
                postId = postId,
                type = "like",
                message = "$username liked your post.",
                profileImageUrl=profileImageUrl
            )
            NotificationRepository().addNotification(notification)
        }
        postRef.child("likes").setValue(likes).await()
    }

    // Yorum Ekle
    suspend fun addComment(postId: String, comment: Comment, ownerId: String, realUserId: String, profileImageUrl: String) {
        val commentsRef = database.child(postId).child("comments").push()
        commentsRef.setValue(comment.copy(id = commentsRef.key ?: "")).await()

        val notification = Notification(
            userId = ownerId,
            realUserId=realUserId,
            postId = postId,
            type = "comment",
            message = "${comment.username} commented on your post." ,
            profileImageUrl = profileImageUrl
        )
        NotificationRepository().addNotification(notification)
    }

    suspend fun searchPosts(query: String): List<Post> {
        return try {
            val snapshot = database.get().await()
            snapshot.children.mapNotNull { it.getValue(Post::class.java) }
                .filter { post ->
                    post.title.contains(query, ignoreCase = true) ||
                            post.caption.contains(query, ignoreCase = true) ||
                            post.tags.any { it.contains(query, ignoreCase = true) }
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveSearchKeyword(userId: String, keyword: String) {
        dbSearchHistory.child("searchHistory").child(userId).child(keyword).setValue(true).await()
    }

    suspend fun getSearchHistory(userId: String): List<String> {
        return try {
            val snapshot = dbSearchHistory.child("searchHistory").child(userId).get().await()
            snapshot.children.mapNotNull { it.key }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun deleteSearchKeyword(userId: String, keyword: String) {
        dbSearchHistory.child("searchHistory").child(userId).child(keyword).removeValue().await()
    }


//    // Yorum ekle
//    suspend fun addComment(postId: String, comment: Comment) {
//        val commentsRef = database.child(postId).child("comments").push()
//        commentsRef.setValue(comment).await()
//    }
}
