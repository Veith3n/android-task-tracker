package uni.aeh.tasktracker.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uni.aeh.tasktracker.Screen
import uni.aeh.tasktracker.core.data.model.CreateTask
import java.util.Date


@Composable
fun HomeScreen(navController: NavHostController, name: String) {
    val viewModel: HomeViewModel = hiltViewModel()
    val tasks by viewModel.tasks.collectAsState()


    fun clickButton() {
        navController.navigate(Screen.Details.route)
    }


    Column {
        tasks.forEach { task ->
            Text(text = task.title)
        }

        Button(onClick = {
            val sampleTask =
                CreateTask(
                    "${tasks.size + 1}: Sample Task",
                    "This is a sample task",
                    Date().time
                )

            viewModel.addTask(sampleTask)

        }) {
            Text(text = "Add Sample Task")
        }

        Button(onClick = { clickButton() }) {
            Text(
                text = "Navigate to details",
            )
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()

    HomeScreen(navController, "Dope name")
}