package uni.aeh.tasktracker.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uni.aeh.tasktracker.core.data.entity.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll(): List<TaskEntity>

    @Insert
    suspend fun insert(task: TaskEntity)

    @Query("DELETE FROM task")
    suspend fun deleteAll()
}