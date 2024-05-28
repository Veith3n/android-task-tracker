package uni.aeh.tasktracker.core.ui.theme.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uni.aeh.tasktracker.core.data.model.Task

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall,
                color = resolveTextColor(task)
            )
           
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyLarge,
                color = resolveTextColor(task)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Due Date: ${task.formattedDueDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = resolveTextColor(task)
            )
        }
    }

}

@Composable
private fun resolveTextColor(task: Task): Color {
    return when {
        task.isOverdue -> MaterialTheme.colorScheme.error
        task.isCompleted -> Color(0xFF388E3C)
        else -> MaterialTheme.colorScheme.onSurface
    }
}
