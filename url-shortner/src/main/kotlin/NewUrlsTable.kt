import org.jetbrains.exposed.dao.id.LongIdTable

object NewUrlsTable : LongIdTable("NewUrls") {
  val newUrl = text("new_url").uniqueIndex()
  val originalUrlId = long("original_url_id").references(OriginalUrlsTable.id)
}