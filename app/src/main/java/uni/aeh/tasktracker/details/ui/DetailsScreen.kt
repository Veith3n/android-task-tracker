package uni.aeh.tasktracker.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(navController: NavController) {
    val viewModel: DetailsViewModel = hiltViewModel()

    // By using rememberCoroutineScope(), we ensure that the coroutines launched
    // within the DetailsScreen are automatically cancelled when the screen is no longer visible
    // or active, preventing memory leaks and ensuring proper resource management.
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.detailsFlow.collect { detailsEffect ->
                when (detailsEffect) {
                    DetailsEffect.ShowHome -> navController.navigate("home")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Button(onClick = viewModel::buttonClicked) {
            Text(text = "details effect")
        }

        Button(onClick = { viewModel.buttonClicked(navController) }) {
            Text(text = "passed nav")
        }
    }

}

@Preview
@Composable
private fun DetailsScreenPreview() {
    val navController = rememberNavController()

    DetailsScreen(navController)
}