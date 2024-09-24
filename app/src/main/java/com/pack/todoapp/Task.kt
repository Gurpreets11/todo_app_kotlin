package com.pack.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var name: String,
    var detail: String,
    var startDate: Long,
    var endDate: Long,
    val creationDate: Long = System.currentTimeMillis()
)
