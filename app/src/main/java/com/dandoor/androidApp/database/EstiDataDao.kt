package com.dandoor.androidApp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EstiDataDao {
    // 특정 실험(labId)에 해당하는 모든 추정 위치 데이터를 가져오는 쿼리
    @Query("SELECT * FROM esti_data WHERE lid = :labId")
    suspend fun getEstiDataByLabId(labId: Long): List<EstiData>

    // 추정 위치 데이터를 데이터베이스에 추가하는 함수
    @Insert
    suspend fun insert(estiData: EstiData)
}