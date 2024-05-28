package uni.aeh.tasktracker.core.ui.theme.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import uni.aeh.tasktracker.core.data.model.CreateTask
import uni.aeh.tasktracker.core.data.time.mapDateToBeRepresentedCorrectlyInDeviceTimezone
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    onDismissRequest: () -> Unit,
    onTaskAdded: (CreateTask) -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = Date().time,
            initialDisplayMode = DisplayMode.Input
        )
    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0
    )
    val scrollState = rememberScrollState()


    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isTimePickerVisible by remember { mutableStateOf(false) }

    fun handleAddTask() {
        val selectedDate =
            datePickerState.selectedDateMillis!!

        val selectedTime = if (isTimePickerVisible) {
            timePickerState.hour * 3600000L + timePickerState.minute * 60000L
        } else {
            0L
        }

        onTaskAdded(
            CreateTask(
                title = title,
                description = description,
                dueDate = mapDateToBeRepresentedCorrectlyInDeviceTimezone(selectedDate) + selectedTime,
                completed = false
            )
        )
        onDismissRequest()
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Add New Task") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(16.dp))
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isTimePickerVisible,
                        onCheckedChange = { isTimePickerVisible = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Set Time")
                }
                if (isTimePickerVisible) {
                    TimePicker(
                        state = timePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },

        confirmButton = {
            Button(
                onClick = { handleAddTask() },
                enabled = title.isNotBlank() && description.isNotBlank()
            ) {
                Text("Add Task")
            }
        },

        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },

        modifier = modifier
    )
}