@file:Suppress("FunctionName")

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

private val sliderRange = 3f..50f

fun main() = application {
  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center),
      size = DpSize(400.dp, 700.dp)
    ),
    onCloseRequest = { exitApplication() }
  ) {
    var includeNumbers by remember { mutableStateOf(true) }
    var includeLetters by remember { mutableStateOf(true) }
    var includeSpecialCharacters by remember { mutableStateOf(true) }
    var size by remember { mutableStateOf(15) }
    var result by remember { mutableStateOf("") }
    var sliderValue by remember { mutableStateOf(15f) }

    Column(modifier = Modifier.padding(12.dp).fillMaxSize()) {
      Column(modifier = Modifier.weight(1f)) {
        CheckboxGroup(
          values = listOf(
            CheckboxGroupItemData("Include numbers", includeNumbers),
            CheckboxGroupItemData("Include letters", includeLetters),
            CheckboxGroupItemData("Include special characters", includeSpecialCharacters)
          )
        ) { value, state ->
          when (value) {
            "Include numbers" -> {
              includeNumbers = state
            }

            "Include letters" -> {
              includeLetters = state
            }

            "Include special characters" -> {
              includeSpecialCharacters = state
            }
          }
        }

        Column {
          Text(text = "password size: ${sliderValue.toInt()}")
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = sliderRange.start.toInt().toString(),
              textAlign = TextAlign.Center,
              modifier = Modifier.weight(0.1f)
            )
            Slider(
              value = sliderValue,
              valueRange = sliderRange,
              onValueChange = { sliderValue = it },
              onValueChangeFinished = { size = sliderValue.toInt() },
              modifier = Modifier.weight(1f)
            )
            Text(
              text = sliderRange.endInclusive.toInt().toString(),
              textAlign = TextAlign.Center,
              modifier = Modifier.weight(0.1f)
            )
          }
        }
      }

      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
          result = PasswordGenerator.getRandomPassword(
            includeNumbers = includeNumbers,
            includeLetters = includeLetters,
            includeSpecialCharacters = includeSpecialCharacters,
            size = size
          )
        }
      ) {
        Text("Generate")
      }

      if (result.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
          Text(text = "Your random password is:", modifier = Modifier.weight(1f))
          TextField(
            value = result,
            readOnly = true,
            modifier = Modifier.weight(5f).fillMaxWidth(),
            onValueChange = {}
          )
        }
      }
    }
  }
}

@Composable
fun CheckboxGroup(
  values: List<CheckboxGroupItemData>,
  onCheckBoxSelected: (String, Boolean) -> Unit
) {
  if (values.isNotEmpty()) {
    val data = remember { mutableStateMapOf<String, Boolean>() }
    LaunchedEffect(true) {
      data.clear()
      values.forEach {
        data[it.text] = it.checked
      }
    }

    Column {
      data.keys.forEach {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(checked = data[it]!!, onCheckedChange = { checked ->
            data[it] = checked
            onCheckBoxSelected(it, checked)
          })
          Text(text = it)
        }
      }
    }
  }
}

data class CheckboxGroupItemData(
  val text: String,
  val checked: Boolean = false
)