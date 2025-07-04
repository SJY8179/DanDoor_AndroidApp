package com.dandoor.androidApp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// AppDatabase가 필요로 하는 모든 클래스들의 경로를 알려줍니다.
// 만약 Lab, ScanData, EstiData 파일이 database 폴더 밖에 있다면, 그 실제 경로로 수정해야 합니다.
import com.dandoor.androidApp.database.EstiData
import com.dandoor.androidApp.database.ScanData
import com.dandoor.androidApp.database.EstiDataDao
import com.dandoor.androidApp.database.LabDao
import com.dandoor.androidApp.database.ScanDataDao

@Database(entities = [Lab::class, ScanData::class, EstiData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun labDao(): LabDao
    abstract fun scanDataDao(): ScanDataDao
    abstract fun estiDataDao(): EstiDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDB(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dandoor_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
