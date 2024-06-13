package uni.aeh.tasktracker.details.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uni.aeh.tasktracker.Screen

@Composable
fun DetailsScreen(navController: NavController, context: Context = LocalContext.current) {
    val viewModel: DetailsViewModel = hiltViewModel()

    // By using rememberCoroutineScope(), we ensure that the coroutines launched
    // within the DetailsScreen are automatically cancelled when the screen is no longer visible
    // or active, preventing memory leaks and ensuring proper resource management.
    val coroutineScope = rememberCoroutineScope()

    val location = viewModel.location.collectAsState()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.detailsFlow.collect { detailsEffect ->
                when (detailsEffect) {
                    DetailsEffect.ShowHome -> navController.navigate(Screen.Home.route)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCurrentLocation(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Button(
            onClick = {
                viewModel.getCurrentLocation(context)
            }
        ) {
            Text(text = "Get Current Location")
        }

        location.value?.let { location ->
            Text(text = "Latitude: ${location.latitude}")
            Text(text = "Longitude: ${location.longitude}")
        } ?: run {
            if (!hasLocationPermissions(context)) {
                Text(text = "Fetching cords")
            } else {
                Text(text = "You need to grant location permissions in order for this feature to work correctly")
            }
        }


        Button(onClick = viewModel::buttonClicked) {
            Text(text = "details effect")
        }

        Button(onClick = { viewModel.buttonClicked(navController) }) {
            Text(text = "passed nav")
        }
    }
}

fun hasLocationPermissions(context: Context): Boolean {
    return (ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED
            )
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    val navController = rememberNavController()

    DetailsScreen(navController)
}