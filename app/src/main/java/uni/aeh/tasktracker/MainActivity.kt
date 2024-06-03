package uni.aeh.tasktracker


import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import uni.aeh.tasktracker.core.ui.theme.TaskTrackerTheme
import uni.aeh.tasktracker.details.ui.DetailsScreen
import uni.aeh.tasktracker.home.ui.HomeScreen

enum class Screen(val route: String) {
    Home("home"),
    Details("details")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {
    private val LOCATION_PERMISSION_REQUEST_CODE = 123

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Both permissions granted, proceed with your app logic
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Permissions denied, handle accordingly
        }
    }

    private fun requestLocationPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (permissionsToRequest.isNotEmpty()) {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE,
                    *permissionsToRequest.toTypedArray()
                )
                    .setRationale("Location permission is required for local weather to work.")
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("Cancel")
                    .build()
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TaskTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        composable(Screen.Details.route) { DetailsScreen(navController) }
                    }

                }
            }
        }

        requestLocationPermissions()
    }
}
