package uni.aeh.tasktracker.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uni.aeh.tasktracker.core.data.dao.TaskDao
import uni.aeh.tasktracker.core.data.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
