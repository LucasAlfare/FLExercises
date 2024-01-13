@file:Suppress("FunctionName")

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val DividerAlpha = 0.12f

suspend fun main() = application {
  // Initialized the database
  Urls.initialize(
    driverName = "org.sqlite.JDBC", // can be easily switched to Postgres
    location = "url-shortner", // points to the current subproject/module directory
    databaseName = "urls", // cool database name
    username = "franciscolucas", // Hi, I'm Francisco Lucas :)
    password = "url-shortner-password" // raw static password
  )

  // Shows up the auxiliary GUI app
  Window(
    title = "URL Shortner",
    onCloseRequest = { exitApplication() },
    state = WindowState(size = DpSize(600.dp, 400.dp), position = WindowPosition(Alignment.Center))
  ) {
    val coroutineScope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxSize().padding(8.dp)) {
      Column(modifier = Modifier.weight(1f).padding(4.dp)) {
        var inputUrl by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxWidth()) {
          TextField(
            value = inputUrl,
            label = { Text("Original URL") },
            modifier = Modifier.height(50.dp),
            onValueChange = { inputUrl = it }
          )

          Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
              coroutineScope.launch {
                val shortnedUrl = Urls.createNewUrl(OriginalUrlDTO(inputUrl))
                result = shortnedUrl.url
              }
            }
          ) {
            Text("short it!")
          }
        }

        if (result.isNotEmpty()) {
          Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
              value = result,
              readOnly = true,
              label = { Text("Shortned URL") },
              modifier = Modifier.fillMaxSize(),
              onValueChange = {}
            )
          }
        }
      }

      VerticalDivider()

      Column(modifier = Modifier.weight(1f).padding(4.dp)) {
        var inputNewUrl by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        TextField(
          value = inputNewUrl,
          label = { Text("Shortned URL") },
          modifier = Modifier.height(50.dp),
          onValueChange = { inputNewUrl = it }
        )

        Button(
          modifier = Modifier.fillMaxWidth(),
          onClick = {
            coroutineScope.launch(context = Dispatchers.IO) {
              val originalUrl = Urls.getOriginalUrlByNewUrl(NewUrlDTO(inputNewUrl))
              result =
                originalUrl?.url ?: "Was not to found any original URL associated with the short URL you provided."
            }
          }
        ) {
          Text("get original URL!")
        }

        if (result.isNotEmpty()) {
          TextField(
            value = result,
            readOnly = true,
            label = { Text("The original URL") },
            modifier = Modifier.fillMaxSize(),
            onValueChange = {}
          )
        }
      }
    }
  }
}

@Composable
fun VerticalDivider(
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colors.onSurface.copy(alpha = DividerAlpha),
  thickness: Dp = 1.dp
) {
  Box(
    modifier
      .fillMaxHeight()
      .width(thickness)
      .background(color = color)
  )
}