package com.dandoor.androidApp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScanDataDao {
    // 특정 실험(labId)에 해당하는 모든 스캔 데이터를 가져오는 쿼리
    @Query("SELECT * FROM scan_data WHERE lid = :labId")
    suspend fun getScanDataByLabId(labId: Long): List<ScanData>

    // 스캔 데이터를 데이터베이스에 추가하는 함수
    @Insert
    suspend fun insert(scanData: ScanData)
}
