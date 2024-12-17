package com.acm431.teamup

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.view.Gravity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Root layout - LinearLayout
        val linearLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(16, 16, 16, 16)
        }

        // Student Button
        val btnStudent = Button(this).apply {
            text = "Go to Student Page"
            setOnClickListener {
                Toast.makeText(this@MainActivity, "Going to Student Page", Toast.LENGTH_SHORT).show()
                // Add logic to navigate to Student Activity
            }
        }

        // Investor Button
        val btnInvestor = Button(this).apply {
            text = "Go to Investor Page"
            setOnClickListener {
                Toast.makeText(this@MainActivity, "Going to Investor Page", Toast.LENGTH_SHORT).show()
                // Add logic to navigate to Investor Activity
            }
        }

        // Add buttons to the LinearLayout
        linearLayout.addView(btnStudent)
        linearLayout.addView(btnInvestor)

        // Set the layout to the activity
        setContentView(linearLayout)
    }
}
