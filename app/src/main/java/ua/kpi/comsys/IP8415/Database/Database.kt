package ua.kpi.comsys.IP8415.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 2)
abstract class Database : RoomDatabase() {
    abstract fun getDao() : Dao
}