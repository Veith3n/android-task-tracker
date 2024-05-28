package uni.aeh.tasktracker.core.data.model

import java.text.DateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
        get() {
            val dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT,
                Locale.getDefault()
            )
            dateFormat.timeZone = TimeZone.getDefault()

            return dateFormat.format(Date(dueDate))
        }
}