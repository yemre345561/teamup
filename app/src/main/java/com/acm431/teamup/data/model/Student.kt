package com.acm431.teamup.data.model

data class Student(
    val id: String = "", // Firebase tarafından otomatik atanacak
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val university: String = "",
    val department: String = "",
    val gender: String = ""
)