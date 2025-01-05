package com.acm431.teamup.data.model

data class Investor(
    val id: String = "", // Firebase tarafından otomatik atanacak
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val company: String = "",
    val position: String = "",
    val gender: String = ""
)