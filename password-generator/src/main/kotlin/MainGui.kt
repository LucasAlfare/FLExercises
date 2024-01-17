@file:Suppress("FunctionName")

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

private val sliderRange = 3f..50f

fun main() = application {
  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center), size = DpSize(300.dp, 500.dp)
    ),
    onCloseRequest = { exitApplication() }
  ) {
    var includeNumbers by remember { mutableStateOf(true) }
    var includeLetters by remember { mutableStateOf(true) }
    var includeSpecialCharacters by remember { mutableStateOf(true) }
    var size by remember { mutableStateOf(15) }
    var result by remember { mutableStateOf(PasswordGenerator.getRandomPassword()) }
    var sliderValue by remember { mutableStateOf(15f) }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxSize()
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.weight(0.8f).fillMaxWidth().padding(8.dp)
      ) {
        Box(
          modifier = Modifier
            .border(width = 2.dp, color = MaterialTheme.colors.primary, shape = RoundedCornerShape(12))
            .fillMaxWidth()
        ) {
          Text(
            text = result,
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.align(Alignment.Center).padding(8.dp)
          )
        }

        IconButton(
          onClick = {
            // TODO: pout result in clipboard
          },
          modifier = Modifier
            .align(Alignment.End)
            .padding(8.dp)
            .size(16.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "",
            tint = MaterialTheme.colors.primary.copy(alpha = 0.4f)
          )
        }
      }

      Box(modifier = Modifier.weight(0.2f)) {
        IconButton(onClick = {
          result = PasswordGenerator.getRandomPassword(
            size = size,
            includeNumbers = includeNumbers,
            includeLetters = includeLetters,
            includeSpecialCharacters = includeSpecialCharacters
          )
        }) {
          Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = "",
            modifier = Modifier.size(24.dp)
          )
        }
      }

      Column(modifier = Modifier.weight(1f).padding(8.dp).fillMaxWidth()) {
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

        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(checked = includeNumbers, onCheckedChange = {
            includeNumbers = it
          })
          Text("Include numbers")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(checked = includeLetters, onCheckedChange = {
            includeLetters = it
          })
          Text("Include letters")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(checked = includeSpecialCharacters, onCheckedChange = {
            includeSpecialCharacters = it
          })
          Text("Include special characters")
        }
      }
    }
  }
}