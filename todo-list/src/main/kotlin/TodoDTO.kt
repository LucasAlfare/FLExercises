data class TodoDTO(
  val title: String?,
  val content: String? = "",
  val state: TodoState? = TodoState.NotStarted,
  val date: Long? = System.currentTimeMillis()
) {
  init {
    requireNotNull(title)
    require(title.isNotEmpty())
    require(date in 0..(System.currentTimeMillis()))
  }
}