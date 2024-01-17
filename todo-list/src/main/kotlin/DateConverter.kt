import java.text.SimpleDateFormat

val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")

fun Long.toDateString() = formatter.format(this)