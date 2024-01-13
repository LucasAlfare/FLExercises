import org.jetbrains.exposed.dao.id.LongIdTable

object OriginalUrlsTable : LongIdTable("OriginalUrls") {
  val url = text("url").uniqueIndex()
}