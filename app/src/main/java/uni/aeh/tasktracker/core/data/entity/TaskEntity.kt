package uni.aeh.tasktracker.core.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo() val title: String,
    @ColumnInfo() val description: String,
    @ColumnInfo() val dueDate: Long,
    @ColumnInfo(defaultValue = "false") val completed: Boolean = false,
) {
}

