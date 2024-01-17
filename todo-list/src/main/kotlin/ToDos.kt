import com.lucasalfare.flexercises.databasehelper.DatabaseSingleton
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object ToDos {

  init {
    DatabaseSingleton.initialize(
      driverName = "org.sqlite.JDBC", // can be easily switched to Postgres
      location = "todo-list", // points to the current subproject/module directory
      databaseName = "todos", // cool database name
      username = "franciscolucas", // Hi, I'm Francisco Lucas :)
      password = "todo-list" // raw static password
    ) {
      transaction(Database.connect(it)) {
        SchemaUtils.createMissingTablesAndColumns(ToDosTable)
      }
    }
  }

  suspend fun create(toDoDTO: ToDoDTO) = DatabaseSingleton.dbQuery {
    ToDosTable.insertAndGetId {
      it[title] = toDoDTO.title!!
      it[content] = toDoDTO.content!!
      it[state] = toDoDTO.state!!
      it[date] = toDoDTO.date!!
    }
  }

  suspend fun getAll() = DatabaseSingleton.dbQuery {
    ToDosTable.selectAll().map {
      ToDoDTO(
        title = it[ToDosTable.title],
        content = it[ToDosTable.content],
        state = it[ToDosTable.state],
        date = it[ToDosTable.date]
      )
    }
  }

  suspend fun getByTitle(title: String) = DatabaseSingleton.dbQuery {
    ToDosTable.selectAll().where {
      ToDosTable.title eq title
    }.singleOrNull()?.let {
      ToDoDTO(
        title = it[ToDosTable.title],
        content = it[ToDosTable.content],
        state = it[ToDosTable.state],
        date = it[ToDosTable.date]
      )
    }
  }

  suspend fun getByState(state: ToDoState) = DatabaseSingleton.dbQuery {
    ToDosTable.selectAll().where {
      ToDosTable.state eq state
    }.map {
      ToDoDTO(
        title = it[ToDosTable.title],
        content = it[ToDosTable.content],
        state = it[ToDosTable.state],
        date = it[ToDosTable.date]
      )
    }
  }

  suspend fun updateByTitle(
    targetTitle: String,
    newTitle: String?,
    newContent: String?,
    newState: ToDoState?
  ) {
    val search = DatabaseSingleton.dbQuery {
      ToDosTable
        .selectAll()
        .where { ToDosTable.title eq targetTitle }
        .singleOrNull()?.let {
          ToDoDTO(
            title = it[ToDosTable.title],
            content = it[ToDosTable.content],
            state = it[ToDosTable.state],
            date = it[ToDosTable.date]
          )
        }
    }

    if (search != null) {
      DatabaseSingleton.dbQuery {
        ToDosTable.update({ ToDosTable.title eq targetTitle }) {
          it[title] = newTitle ?: search.title!!
          it[content] = newContent ?: search.content!!
          it[state] = newState ?: search.state!!
          // do not update the date, meaningless
        }
      }
    }
  }

  suspend fun deleteByTitle(targetTitle: String) {
    DatabaseSingleton.dbQuery {
      ToDosTable.deleteWhere { title eq targetTitle }
    }
  }

  suspend fun deleteByState(targetState: ToDoState) {
    DatabaseSingleton.dbQuery {
      ToDosTable.deleteWhere { state eq targetState }
    }
  }

  suspend fun deleteByDate(targetDate: Long) {
    DatabaseSingleton.dbQuery {
      ToDosTable.deleteWhere { date eq targetDate }
    }
  }

  suspend fun deleteAll() {
    DatabaseSingleton.dbQuery {
      ToDosTable.deleteAll()
    }
  }
}