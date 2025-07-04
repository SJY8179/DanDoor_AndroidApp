package com.dandoor.androidApp.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "esti_data",
    foreignKeys = [ForeignKey(
        entity = Lab::class,
        parentColumns = ["labID"],
        childColumns = ["lid"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class EstiData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val lid: Long, // Lab 테이블의 labID를 참조하는 외래 키
    val real_pos: String,
    val esti_pos: String
)