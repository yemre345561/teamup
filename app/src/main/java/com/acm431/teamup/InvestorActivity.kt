package com.acm431.teamup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.content.Intent

class InvestorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeamUpAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(text = "Welcome to the Investor Page!", style = MaterialTheme.typography.h4)

                        Button(
                            onClick = {
                                // Navigate back to MainActivity
                                val intent = Intent(this@InvestorActivity, MainActivity::class.java)
                                startActivity(intent)
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(text = "Go Back")
                        }
                    }
                }
            }
        }
    }
}
