package com.abdulkadirmisto.todolist.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Task(
    val title: String,
    val date: String,
    val time: String,
    val isDone: MutableState<Boolean> = mutableStateOf(false)
)