package uni.aeh.tasktracker.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uni.aeh.tasktracker.core.data.TaskRepositoryImpl
import uni.aeh.tasktracker.core.data.model.CreateTaskDto
import uni.aeh.tasktracker.core.data.model.Task
import javax.inject.Inject

@HiltViewModel()
class HomeViewModel @Inject constructor(private val taskRepository: TaskRepositoryImpl) :
    ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()


    init {
        viewModelScope.launch {
            _tasks.value = taskRepository.getTasks()
        }
    }

    fun addTask(task: CreateTaskDto) {
        viewModelScope.launch {
            try {
                taskRepository.addTask(task)

                _tasks.value = taskRepository.getTasks()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error adding task", e)
            }
        }
    }

    fun onTaskStatusChanged(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.updateTask(task)

                _tasks.value = taskRepository.getTasks()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error updating task", e)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                taskRepository.deleteAll()
                _tasks.value = taskRepository.getTasks()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error deleting tasks", e)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        // Clear any resources or cancel coroutines here
    }

}
