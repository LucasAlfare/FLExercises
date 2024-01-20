import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import ui.theme.ExercisesTheme

fun main() = application {
  Window(
    state = WindowState(position = WindowPosition(Alignment.Center)),
    onCloseRequest = { exitApplication() }
  ) {
    ExercisesTheme {
      Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
          TodoInput()
        }

        Box(modifier = Modifier.weight(3f)) {
          TodosList()
        }

        Box(modifier = Modifier.weight(0.4f)) {
          Footer()
        }
      }
    }
  }
}

@Composable
fun TodoInput() {
  var todoTitle by remember { mutableStateOf("") }

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth().padding(4.dp)
  ) {
    OutlinedTextField(
      value = todoTitle,
      label = { Text("Create a new TODO") },
      modifier = Modifier.padding(4.dp).weight(2f),
      onValueChange = {
        todoTitle = it
      }
    )

    IconButton(
      modifier = Modifier.padding(4.dp).weight(0.3f),
      onClick = {}
    ) {
      Icon(imageVector = Icons.Filled.Add, "create todo")
    }
  }
}

@Composable
fun TodosList() {
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    repeat(50) {
      item {
        TodoListItem(TodoDTO("Teste hehehe"))
        Divider()
      }
    }
  }
}

@Composable
fun TodoListItem(todo: TodoDTO) {
  var expanded by remember { mutableStateOf(false) }

  Column(
    verticalArrangement = Arrangement.Center,
    modifier = Modifier
      .fillMaxWidth()
      .height(65.dp)
      .padding(4.dp)
      .clickable { expanded = !expanded }
      .animateContentSize()
  ) {
    Text(text = todo.title!!)

    if (expanded) {
      Row {
        IconButton(onClick = {}) {
          Icon(imageVector = Icons.Filled.Delete, "delete TODO")
        }
      }
    }
  }
}

@Composable
fun Footer() {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth().padding(4.dp)
  ) {
    Text(text = "Number of TODOs: --", modifier = Modifier.padding(4.dp))

    Button(
      modifier = Modifier.padding(4.dp),
      onClick = {}
    ) {
      Text("Clear TODOs")
    }
  }
}