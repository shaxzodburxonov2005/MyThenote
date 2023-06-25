package com.example.mythenote.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithInfo (
    @Embedded val users: Users,
    @Relation(
        parentColumn = "id",
        entityColumn = "info_id"
    )
    val informationDB: List<InformationDB>
        )