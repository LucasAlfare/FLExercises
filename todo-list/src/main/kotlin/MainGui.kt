import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import ui.theme.ExercisesTheme

fun main() = application {
  val coroutineScope = rememberCoroutineScope()

  coroutineScope.launch {
    MainUiState.getFiltered() += ToDos.getAll()
  }

  Window(
    state = WindowState(position = WindowPosition(Alignment.Center)),
    onCloseRequest = { exitApplication() }
  ) {
    ExercisesTheme {
      Row(modifier = Modifier.fillMaxSize()) {
        ToDosList()
      }
    }
  }
}

@Composable
fun ToDosList() {
  LazyColumn {
    MainUiState.getFiltered().forEach {
      item {
        ToDoItem(it)
        Divider()
      }
    }
  }
}

@Composable
fun ToDoItem(toDo: ToDoDTO) {
  val coroutineScope = rememberCoroutineScope()
  Column {
    Row {
      Column {
        Text(toDo.title!!)
        Text(toDo.date!!.toDateString())
      }
      Icon(
        imageVector = when (toDo.state) {
          ToDoState.Completed -> Icons.Filled.Check
          ToDoState.NotStarted -> Icons.Filled.List
          ToDoState.Started -> Icons.Filled.Warning
          else -> Icons.Filled.Lock
        },
        contentDescription = ""
      )
    }

    Row {
      Text("Mark as:")
      Button(onClick = {}) {
        Text("Not Started")
      }

      Button(onClick = {}) {
        Text("Started Only")
      }

      Button(onClick = {
        coroutineScope.launch {
          ToDos.updateByTitle(toDo.title!!, null, null, ToDoState.Completed)
        }
      }) {
        Text("Completed")
      }
    }

    Text(toDo.content!!)
  }
}

object MainUiState {
  private val filtered = mutableStateListOf<ToDoDTO>()

  fun getFiltered() = filtered
}