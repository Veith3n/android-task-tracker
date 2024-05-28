package uni.aeh.tasktracker.core.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uni.aeh.tasktracker.core.data.dao.TaskDao
import uni.aeh.tasktracker.core.data.entity.TaskEntity
import uni.aeh.tasktracker.core.data.model.CreateTaskDto
import uni.aeh.tasktracker.core.data.model.Task
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(createTaskDto: CreateTaskDto)
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {

    override suspend fun getTasks(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDao.getAll().map { taskEntity ->
                Task(
                    id = taskEntity.id,
                    title = taskEntity.title,
                    description = taskEntity.description,
                    dueDate = taskEntity.dueDate,
                    completed = taskEntity.completed
                )
            }
        }
    }


    override suspend fun addTask(createTaskDto: CreateTaskDto) {
        return withContext(Dispatchers.IO) {
            val newTask = TaskEntity(
                title = createTaskDto.title,
                description = createTaskDto.description,
                dueDate = createTaskDto.dueDate,
                completed = createTaskDto.completed ?: false
            )

            taskDao.insert(newTask)
        }
    }
}