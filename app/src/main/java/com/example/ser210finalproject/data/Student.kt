package com.example.ser210finalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val userId: String, // QU ID
    val email: String,
    val mealpointBalance: Int
)

