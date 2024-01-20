import org.jetbrains.exposed.dao.id.LongIdTable

object TodosTable : LongIdTable("Todos") {
  var title = text("title")
  var content = text("content")
  var state = enumeration<TodoState>("state")
  var date = long("date")
}
