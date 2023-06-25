package com.example.mythenote.model

import android.icu.text.IDNA.Info
import androidx.room.Embedded
import androidx.room.Relation

data class InformationWithTeeth (
    @Embedded
    val informationDB: InformationDB,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val teeths: List<Teeth>
)