package uni.aeh.tasktracker.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DetailsScreen(navController: NavController) {
    fun onButtonClick () {
navController.navigate("home")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)) {
        Button(onClick = ::onButtonClick) {
            Text(text = "Some text")
        }

    }

}

@Preview
@Composable
private fun DetailsScreenPreview() {
    val navController = rememberNavController()

    DetailsScreen(navController)
}