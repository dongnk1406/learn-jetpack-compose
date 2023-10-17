package com.example.learncompose.domain.model

data class Note(
    val title: String,
    val content: String?,
    val timestamp: Long,
)