package com.example.myapp

import Project
import ProjectAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.acm431.teamup.R
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)


        val projectList = listOf(
            Project("Joshua Smith", "Project Description", R.drawable.ic_pro, 234, 32),
            Project("Joshua Smith", "Student Description", R.drawable.ic_pro, 234, 32)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProjectAdapter(projectList)
    }
}
