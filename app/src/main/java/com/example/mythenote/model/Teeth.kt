package com.example.mythenote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teeth(
    @PrimaryKey(autoGenerate = true)
    var teethId: Int? = null,
    var userId: Int? = null,
    var isCheck: Boolean = false
)