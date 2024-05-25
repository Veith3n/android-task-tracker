package uni.aeh.tasktracker.home.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel()
class HomeViewModel @Inject constructor() : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        // Clear any resources or cancel coroutines here
    }

}