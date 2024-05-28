package uni.aeh.tasktracker.home.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uni.aeh.tasktracker.Screen
import uni.aeh.tasktracker.core.data.model.CreateTask
import uni.aeh.tasktracker.core.ui.theme.task.TaskItem
import java.util.Date


@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val tasks by viewModel.tasks.collectAsState()

    fun clickButton() {
        navController.navigate(Screen.Details.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { clickButton() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate to Details")
        }
    }
}
