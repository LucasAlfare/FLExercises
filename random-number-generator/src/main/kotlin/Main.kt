import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center),
      size = DpSize(600.dp, 400.dp)
    ),
    onCloseRequest = { exitApplication() }
  ) {
    var min by remember { mutableStateOf("0") }
    var max by remember { mutableStateOf("10") }
    var result by remember { mutableStateOf("") }
    var log by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "minimum value (inclusive):", modifier = Modifier.weight(1f))
        TextField(
          value = min,
          modifier = Modifier.weight(2f),
          onValueChange = {
            runCatching {
              if (it.isEmpty()) {
                min = "0"
              } else {
                if (max.isNotEmpty()) {
                  val theMinNumber = it.filter { c -> c.isDigit() }.toLong()
                  if (theMinNumber > max.toLong()) {
                    max = (theMinNumber + 1).toString()
                    min = theMinNumber.toString()
                  } else {
                    min = theMinNumber.toString()
                  }
                }
              }
            }.onSuccess {
              log = ""
            }.onFailure {
              log = "The minimum value is too large."
            }
          }
        )
      }

      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "maximum value (inclusive):", modifier = Modifier.weight(1f))
        TextField(
          value = max,
          modifier = Modifier.weight(2f),
          onValueChange = {
            if (it.isEmpty()) {
              max = "0"
            } else {
              runCatching {
                if (min.isNotEmpty()) {
                  val theMaxNumber = it.filter { c -> c.isDigit() }.toLong()
                  if (theMaxNumber < min.toLong()) {
                    min = (theMaxNumber - 1).toString()
                    max = theMaxNumber.toString()
                  } else {
                    max = theMaxNumber.toString()
                  }
                }
              }.onSuccess {
                log = ""
              }.onFailure {
                log = "The maximum value is too large."
              }
            }
          })
      }

      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
          result = RandomNumberGenerator.getRandomNumber(min.toLong(), max.toLong()).toString()
        }
      ) {
        Text("generate!")
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.weight(1f).padding(4.dp)) {
          if (result.isNotEmpty()) {
            Column {
              Text("Result:")
              TextField(value = result, onValueChange = {}, readOnly = true, modifier = Modifier.fillMaxSize())
            }
          }
        }

        Box(modifier = Modifier.weight(1f)) {
          if (log.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(4.dp)) {
              Text(text = log, modifier = Modifier.align(Alignment.BottomEnd))
            }
          }
        }
      }
    }
  }
}