package uni.aeh.tasktracker.home.ui

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uni.aeh.tasktracker.MainActivity
import uni.aeh.tasktracker.core.data.TaskRepositoryImpl
import uni.aeh.tasktracker.core.data.model.CreateTaskDto
import uni.aeh.tasktracker.core.data.model.Task
import uni.aeh.tasktracker.core.ui.theme.Consts
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

    fun showExpiredTaskNotification(task: Task, context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, Consts.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
            .setContentTitle("Task Expired")
            .setContentText("${task.title} has expired")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(task.id, builder.build())
        }
    }

}
