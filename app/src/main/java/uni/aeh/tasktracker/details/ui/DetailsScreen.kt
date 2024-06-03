package uni.aeh.tasktracker.details.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch
import uni.aeh.tasktracker.Screen

@Composable
fun DetailsScreen(navController: NavController, context: Context = LocalContext.current) {
    val viewModel: DetailsViewModel = hiltViewModel()

    // By using rememberCoroutineScope(), we ensure that the coroutines launched
    // within the DetailsScreen are automatically cancelled when the screen is no longer visible
    // or active, preventing memory leaks and ensuring proper resource management.
    val coroutineScope = rememberCoroutineScope()

    val (location, setLocation) = remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.detailsFlow.collect { detailsEffect ->
                when (detailsEffect) {
                    DetailsEffect.ShowHome -> navController.navigate(Screen.Home.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Button(
            onClick = {
                getCurrentLocation(context, setLocation)
            }
        ) {
            Text(text = "Get Current Location")
        }

        if (location != null) {
            Text(text = "Latitude: ${location.latitude}")
            Text(text = "Longitude: ${location.longitude}")
        } else {
            Text(text = "You need to grant location permissions in order for this feature to work correctly")
        }

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


private fun getCurrentLocation(context: Context, setLocation: (Location?) -> Unit) {
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = LocationRequest.Builder(1000L)
        .setMinUpdateDistanceMeters(100f)
        .build()
    val cancellationTokenSource = CancellationTokenSource()

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.d("Location", "Permission not granted")
        return
    }

    fusedLocationProviderClient.getCurrentLocation(
        locationRequest.priority,
        cancellationTokenSource.token
    ).addOnSuccessListener { location: Location ->
        Log.d("Location", "Location: $location")
        val latitude = location.latitude
        val longitude = location.longitude
        // Do something with the latitude and longitude
        Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
        setLocation(location)

    }.addOnFailureListener { exception: Exception ->
        Log.d("Location", "error: $exception")
    }
}
