package com.dandoor.androidApp.database
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity(tableName = "lab"): 이 클래스가 'lab'이라는 이름의 테이블과 연결됨을 나타냅니다.
@Entity(tableName = "lab")
data class Lab(
    // @PrimaryKey: 이 필드가 테이블의 고유 식별자(기본 키)임을 나타냅니다.
    @PrimaryKey(autoGenerate = true)
    val labID: Long = 0,

    val beaconPositions: String,
    val createdAt: Long,
    val alias: String
)
