package com.dandoor.androidApp.database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// @Entity 어노테이션에 foreignKeys 속성을 추가하여 외래 키 관계를 정의합니다.
@Entity(
    tableName = "scan_data",
    foreignKeys = [ForeignKey(
        entity = Lab::class,
        parentColumns = ["labID"],
        childColumns = ["lid"],
        onDelete = ForeignKey.CASCADE // Lab 데이터가 삭제되면 관련된 ScanData도 함께 삭제됩니다.
    )]
)
data class ScanData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val lid: Long, // Lab 테이블의 labID를 참조하는 외래 키
    val timestamp: Long,
    val beaconID: String,
    val rssi: Int
)
