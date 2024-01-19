import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import ui.theme.ExercisesTheme

fun main() = application {
  Window(
    state = WindowState(
      position = WindowPosition(Alignment.Center),
      size = DpSize(300.dp, 500.dp)
    ),
    onCloseRequest = { exitApplication() }
  ) {
    ExercisesTheme {
      var gen by remember { mutableStateOf(0L) }
      var min by remember { mutableStateOf(0L) }
      var max by remember { mutableStateOf(10L) }

      Box(modifier = Modifier.fillMaxSize()) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.align(Alignment.TopCenter).padding(8.dp)
        ) {
          Text(text = gen.toString(), fontSize = 45.sp)
          IconButton(onClick = {
            gen = RandomNumberGenerator.getRandomNumber(minValue = min, maxValue = max)
          }) {
            Icon(
              imageVector = Icons.Filled.Refresh,
              contentDescription = "",
              modifier = Modifier.size(45.dp)
            )
          }
        }

        Row(
          horizontalArrangement = Arrangement.SpaceAround,
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .align(Alignment.BottomCenter)
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Minimum:")
            NumericTextField(value = min, modifier = Modifier.width(75.dp)) {
              if (it > max) max = it + 1
              min = it
            }
          }

          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Maximum:")
            NumericTextField(value = max, modifier = Modifier.width(75.dp)) {
              if (it < max) min = it - 1
              max = it
            }
          }
        }
      }
    }
  }
}

@Composable
fun NumericTextField(
  value: Long = 0L,
  minimumValue: Long = Long.MIN_VALUE,
  maximumValue: Long = Long.MAX_VALUE,
  modifier: Modifier = Modifier,
  onLessMinimumValueEntered: (Long) -> Unit = {},
  onHigherMaximumValueEntered: (Long) -> Unit = {},
  onValueChange: (Long) -> Unit = {}
) {
  // here we don't use "remember" because we don't want to store to make it update based on outside events
  val numValue = mutableStateOf(value.toString())
  OutlinedTextField(
    value = numValue.value,
    modifier = modifier,
    onValueChange = {
      val filteredString = it.filter { c -> c.isDigit() }
      if (filteredString.isNotEmpty()) {
        val number = filteredString.toLong()
        if (number < minimumValue) {
          onLessMinimumValueEntered(number)
        } else if (number > maximumValue) {
          onHigherMaximumValueEntered(number)
        } else {
          numValue.value = number.toString()
          onValueChange(number)
        }
      } else {
        numValue.value = ""
        onValueChange(0L)
      }
    }
  )
}