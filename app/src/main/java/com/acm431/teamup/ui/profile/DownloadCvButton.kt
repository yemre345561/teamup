package com.acm431.teamup.ui.profile

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.acm431.teamup.data.model.User
import kotlinx.coroutines.launch

@Composable
fun DownloadCvButton(user : User) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            if (!user.cvUrl.isNullOrEmpty()) {
                coroutineScope.launch {
                        downloadCvFromStorage(context, user.cvUrl)
                }
            } else {
                Toast.makeText(context, "CV url is empty", Toast.LENGTH_SHORT).show()
            }
        },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = "Download CV",
            color = if (user.userType == "Investor") Color(0xFF0E2C47) else Color(0xFF173251)
        )
    }
}

fun downloadCvFromStorage(context: Context, cvUrl: String) {
    try {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(cvUrl)

        val request = DownloadManager.Request(uri).apply {
            setTitle("Downloading CV")
            setDescription("Downloading your CV from Firebase Storage")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "user_cv.pdf")
            setAllowedOverMetered(true)
            setAllowedOverRoaming(true)
        }

        downloadManager.enqueue(request)

        Toast.makeText(context, "CV Download Started", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Failed to download CV: ${e.message}", Toast.LENGTH_LONG).show()
    }
}