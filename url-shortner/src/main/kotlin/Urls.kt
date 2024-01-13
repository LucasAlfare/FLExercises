import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.util.IsolationLevel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Urls {

  private lateinit var hikariDataSource: HikariDataSource

  fun initialize(
    driverName: String,
    location: String,
    databaseName: String,
    username: String,
    password: String
  ) {
    hikariDataSource = createHikariDataSource(
      jdbcUrl = "jdbc:sqlite:$location/$databaseName.db",
      jdbcDriver = driverName,
      username = username,
      password = password
    )

    transaction(Database.connect(hikariDataSource)) {
      SchemaUtils.createMissingTablesAndColumns(OriginalUrlsTable, NewUrlsTable)
    }
  }

  suspend fun createNewUrl(originalUrlDTO: OriginalUrlDTO) = dbQuery {
    val originalIdRef = OriginalUrlsTable.insertAndGetId {
      it[url] = originalUrlDTO.url
    }.value

    val shortnedUrl = UrlShortner.generateShortUrl()

    NewUrlsTable.insert {
      it[newUrl] = shortnedUrl
      it[originalUrlId] = originalIdRef
    }

    NewUrlDTO(shortnedUrl)
  }

  suspend fun getOriginalUrlByNewUrl(newUrlDTO: NewUrlDTO) = dbQuery {
    NewUrlsTable.selectAll().where { NewUrlsTable.newUrl eq newUrlDTO.url }.singleOrNull()
  }?.let { newUrlSearch ->
    dbQuery {
      OriginalUrlsTable
        .selectAll()
        .where { OriginalUrlsTable.id eq newUrlSearch[NewUrlsTable.id].value }
        .firstOrNull()?.let {
          OriginalUrlDTO(
            url = it[OriginalUrlsTable.url]
          )
        }
    }
  }

//  suspend fun getOriginalUrlByNewUrl(newUrlDTO: NewUrlDTO): OriginalUrlDTO {
//    val res1 = dbQuery {
//      NewUrlsTable.selectAll().where { NewUrlsTable.newUrl eq newUrlDTO.url }.firstOrNull()
//    }
//
//    println(res1)
//
//    return OriginalUrlDTO("kkk")
//  }

  /**
   * Creates and configures a HikariCP DataSource for connecting to a PostgreSQL database.
   *
   * @param jdbcUrl The JDBC URL of the PostgreSQL database.
   * @param username The username for authenticating the database connection.
   * @param password The password for authenticating the database connection.
   *
   * @return A configured HikariDataSource instance.
   */
  private fun createHikariDataSource(
    jdbcUrl: String,
    jdbcDriver: String,
    username: String,
    password: String
  ): HikariDataSource {
    val hikariConfig = HikariConfig().apply {
      this.jdbcUrl = jdbcUrl
      this.driverClassName = jdbcDriver
      this.username = username
      this.password = password
      this.maximumPoolSize = 20
      this.isAutoCommit = true
      this.transactionIsolation = IsolationLevel.TRANSACTION_READ_COMMITTED.name
      this.validate()
    }

    // Creating a new HikariDataSource instance using the configured HikariConfig
    return HikariDataSource(hikariConfig)
  }

  private suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(
      db = Database.connect(hikariDataSource),
      context = Dispatchers.IO
    ) {
      block()
    }
}