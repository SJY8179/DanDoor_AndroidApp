package com.dandoor.androidApp.database


// Lab 클래스가 어디에 있는지 경로를 알려줍니다.
import androidx.room.Dao
import androidx.room.Query

@Dao
interface LabDao {

    // @Query: Room 라이브러리에게 이 함수가 어떤 SQL 쿼리를 실행할지 알려줍니다.
    // "SELECT * FROM lab ORDER BY createdAt DESC" : lab 테이블의 모든 데이터를 createdAt(생성시간) 기준으로 내림차순(최신순) 정렬하여 가져옵니다.
    @Query("SELECT * FROM lab ORDER BY createdAt DESC")
    // suspend: 이 함수는 코루틴 내에서 호출되어야 하는 비동기 함수임을 나타냅니다.
    // DB 작업처럼 오래 걸리는 작업을 백그라운드에서 처리하여 UI 멈춤을 방지합니다.
    suspend fun getAllLabs(): List<Lab>
}
