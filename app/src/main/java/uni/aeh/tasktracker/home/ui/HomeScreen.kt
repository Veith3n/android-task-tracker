package uni.aeh.tasktracker.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uni.aeh.tasktracker.Screen


@Composable
fun HomeScreen(navController: NavHostController, name: String) {
    val homeViewModel: HomeViewModel = hiltViewModel()


    fun clickButton() {
        navController.navigate(Screen.Details.route)
    }

    Column {
        Button(onClick = { clickButton() }) {
            Text(
                text = "Hello $name!",
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