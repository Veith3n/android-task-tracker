package uni.aeh.tasktracker.details.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uni.aeh.tasktracker.Screen
import javax.inject.Inject

@HiltViewModel()
class DetailsViewModel @Inject constructor() : ViewModel() {
    val state = MutableStateFlow("unit")

    private val _detailsEffect = MutableSharedFlow<DetailsEffect>()
    val detailsFlow = _detailsEffect.asSharedFlow()

    private val _location = MutableStateFlow<Location?>(null)
    val location = _location.asStateFlow()

    fun buttonClicked() {
        // creates scoped coroutine
        viewModelScope.launch {
            try {
                _detailsEffect.emit(DetailsEffect.ShowHome)
                Log.i("DetailsViewModel", "Effect emitted: ShowHome")
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Failed to emit effect: $e")

            }
        }
    }

    fun getCurrentLocation(context: Context) {
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
            _location.update { location }
        }.addOnFailureListener { exception: Exception ->
            Log.d("Location", "error: $exception")
        }
    }

    fun buttonClicked(navController: NavController) {
        navController.navigate(Screen.Home.route)
    }
}

sealed interface DetailsEffect {
    data object ShowHome : DetailsEffect
}