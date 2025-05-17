package com.example.fundra.menu

data class AccountItem(
    val icon: Int,
    val text: String,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val social: String? = null
)
