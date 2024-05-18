package uni.aeh.tasktracker.home.ui

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
fun HomeScreen(navController: NavController,name: String) {
    Column() {
        Button(onClick = { /*TODO*/ }) {
            
        }
        Text(
            text = "Hello $name!",
        )

    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()

    HomeScreen(navController, "Dope name")
}