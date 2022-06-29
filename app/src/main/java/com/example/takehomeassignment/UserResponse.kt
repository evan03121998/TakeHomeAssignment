package com.example.takehomeassignment

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: ArrayList<User>,
    val support: Support
)