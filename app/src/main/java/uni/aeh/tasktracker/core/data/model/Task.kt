package uni.aeh.tasktracker.core.data.model

import java.util.Date

interface CreateTaskDto {
    val title: String
    val description: String
    val dueDate: Long
    val completed: Boolean?
}

data class CreateTask(
    override val title: String,
    override val description: String,
    override val dueDate: Long,
    override val completed: Boolean? = false
) : CreateTaskDto


data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: Long,
    val completed: Boolean
) {
    val formattedDueDate: String
        get() = Date(dueDate).toString()
}