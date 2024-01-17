import org.jetbrains.exposed.dao.id.LongIdTable

object ToDosTable : LongIdTable("Todos") {
  var title = text("title")
  var content = text("content")
  var state = enumeration<ToDoState>("state")
  var date = long("date")
}
