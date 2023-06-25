package com.example.mythenote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InformationDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var historyDb: String? = null,
    @ColumnInfo(name = "patient_sheet")
    var patientSheet: String? = null,
    var patientData: String? = null,
    var dayPatient: String? = null,

    @ColumnInfo(name = "info_id")
    var infoId: Int,
    var imag: String? = null,
) : java.io.Serializable