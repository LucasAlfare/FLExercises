import com.lucasalfare.flexercises.databasehelper.DatabaseSingleton
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Todos {

  init {
    DatabaseSingleton.initialize(
      driverName = "org.sqlite.JDBC", // can be easily switched to Postgres
      location = "todo-list", // points to the current subproject/module directory
      databaseName = "todos", // cool database name
      username = "franciscolucas", // Hi, I'm Francisco Lucas :)
      password = "todo-list" // raw static password
    ) {
      transaction(Database.connect(it)) {
        SchemaUtils.createMissingTablesAndColumns(TodosTable)
      }
    }
  }

  suspend fun create(toDoDTO: TodoDTO) = DatabaseSingleton.dbQuery {
    TodosTable.insertAndGetId {
      it[title] = toDoDTO.title!!
      it[content] = toDoDTO.content!!
      it[state] = toDoDTO.state!!
      it[date] = toDoDTO.date!!
    }
  }

  suspend fun getAll() = DatabaseSingleton.dbQuery {
    TodosTable.selectAll().map {
      TodoDTO(
        title = it[TodosTable.title],
        content = it[TodosTable.content],
        state = it[TodosTable.state],
        date = it[TodosTable.date]
      )
    }
  }

  suspend fun getByTitle(title: String) = DatabaseSingleton.dbQuery {
    TodosTable.selectAll().where {
      TodosTable.title eq title
    }.singleOrNull()?.let {
      TodoDTO(
        title = it[TodosTable.title],
        content = it[TodosTable.content],
        state = it[TodosTable.state],
        date = it[TodosTable.date]
      )
    }
  }

  suspend fun getByState(state: TodoState) = DatabaseSingleton.dbQuery {
    TodosTable.selectAll().where {
      TodosTable.state eq state
    }.map {
      TodoDTO(
        title = it[TodosTable.title],
        content = it[TodosTable.content],
        state = it[TodosTable.state],
        date = it[TodosTable.date]
      )
    }
  }

  suspend fun updateByTitle(
    targetTitle: String,
    newTitle: String?,
    newContent: String?,
    newState: TodoState?
  ) {
    val search = DatabaseSingleton.dbQuery {
      TodosTable
        .selectAll()
        .where { TodosTable.title eq targetTitle }
        .singleOrNull()?.let {
          TodoDTO(
            title = it[TodosTable.title],
            content = it[TodosTable.content],
            state = it[TodosTable.state],
            date = it[TodosTable.date]
          )
        }
    }

    if (search != null) {
      DatabaseSingleton.dbQuery {
        TodosTable.update({ TodosTable.title eq targetTitle }) {
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
      TodosTable.deleteWhere { title eq targetTitle }
    }
  }

  suspend fun deleteByState(targetState: TodoState) {
    DatabaseSingleton.dbQuery {
      TodosTable.deleteWhere { state eq targetState }
    }
  }

  suspend fun deleteByDate(targetDate: Long) {
    DatabaseSingleton.dbQuery {
      TodosTable.deleteWhere { date eq targetDate }
    }
  }

  suspend fun deleteAll() {
    DatabaseSingleton.dbQuery {
      TodosTable.deleteAll()
    }
  }
}