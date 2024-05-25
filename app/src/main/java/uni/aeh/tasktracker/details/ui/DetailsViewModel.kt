package uni.aeh.tasktracker.details.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class DetailsViewModel @Inject constructor() : ViewModel() {
    val state = MutableStateFlow("unit")

    private val _detailsEffect = MutableSharedFlow<DetailsEffect>()
    val detailsFlow = _detailsEffect.asSharedFlow()

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

    fun buttonClicked(navController: NavController) {
        navController.navigate("home")
    }
}

sealed interface DetailsEffect {
    data object ShowHome : DetailsEffect
}