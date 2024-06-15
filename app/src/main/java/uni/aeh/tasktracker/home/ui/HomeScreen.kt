package uni.aeh.tasktracker.home.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uni.aeh.tasktracker.Screen
import uni.aeh.tasktracker.core.data.model.CreateTask
import uni.aeh.tasktracker.core.ui.theme.Consts
import uni.aeh.tasktracker.core.ui.theme.task.AddTaskDialog
import uni.aeh.tasktracker.core.ui.theme.task.TaskItem
import java.util.Date


@Composable
fun HomeScreen(navController: NavHostController, context: Context = LocalContext.current) {
    val viewModel: HomeViewModel = hiltViewModel()
    val tasks by viewModel.tasks.collectAsState()
    var showAddTaskDialog by remember { mutableStateOf(false) }

    fun clickButton() {
        navController.navigate(Screen.Details.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Consts.NORMAL_SPACING)
    ) {
        Spacer(modifier = Modifier.height(Consts.NORMAL_SPACING))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskStatusChanged = { updatedTask ->
                        viewModel.onTaskStatusChanged(updatedTask)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Consts.SMALL_SPACING)
                )
                Button(
                    onClick = {
                        viewModel.showExpiredTaskNotification(task, context)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Notification")
                }

            }
        }

        Button(
            onClick = {
                val sampleTask = CreateTask(
                    title = "${tasks.size + 1}: Sample Task",
                    description = "This is a sample task",
                    dueDate = Date().time,
                    completed = false
                )
                viewModel.addTask(sampleTask)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Sample Task")
        }

        Spacer(modifier = Modifier.height(Consts.SMALL_SPACING))
        Button(
            onClick = { showAddTaskDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add New Task")
        }

        if (showAddTaskDialog) {
            AddTaskDialog(
                onDismissRequest = { showAddTaskDialog = false },
                onTaskAdded = { newTask ->
                    viewModel.addTask(newTask)
                    showAddTaskDialog = false
                }
            )
        }

        Spacer(modifier = Modifier.height(Consts.SMALL_SPACING))
        Button(
            onClick = { viewModel.deleteAll() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Delete all")
        }


        Spacer(modifier = Modifier.height(Consts.SMALL_SPACING))
        Button(
            onClick = { clickButton() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate to Details")
        }
    }
}
