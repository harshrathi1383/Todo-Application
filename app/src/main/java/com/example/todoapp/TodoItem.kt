package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem (
    var title: String,
    var description: String,
    var category: String,
    var date: Long,
    var time: Long,
    var isCompleted: Long = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)