package com.dandoor.androidApp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// @Database 어노테이션: 이 클래스가 Room 데이터베이스임을 선언합니다.
// entities = [...]: 이 데이터베이스가 관리할 모든 테이블(엔티티)의 목록입니다.
// version = 1: 데이터베이스의 버전입니다.
@Database(entities = [Lab::class, ScanData::class, EstiData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // 이 데이터베이스를 통해 접근할 DAO들을 선언합니다.
    abstract fun labDao(): LabDao
    abstract fun scanDataDao(): ScanDataDao
    abstract fun estiDataDao(): EstiDataDao

    // companion object: 클래스 인스턴스 없이 접근할 수 있는 멤버를 정의합니다.
    companion object {
        // @Volatile: 이 변수가 여러 스레드에서 접근될 때 항상 최신 값을 유지하도록 보장합니다.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // 데이터베이스 인스턴스를 가져오는 함수 (싱글턴 패턴)
        fun getDB(context: Context): AppDatabase {
            // 이미 인스턴스가 있으면 즉시 반환합니다.
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            // 인스턴스가 없으면, 여러 스레드가 동시에 접근하는 것을 막고 새로 생성합니다.
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dandoor_database" // 데이터베이스 파일의 이름
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}