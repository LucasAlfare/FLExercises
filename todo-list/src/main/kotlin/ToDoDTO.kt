data class ToDoDTO(
  val title: String?,
  val content: String? = "",
  val state: ToDoState? = ToDoState.NotStarted,
  val date: Long? = System.currentTimeMillis()
) {
  init {
    requireNotNull(title)
    require(title.isNotEmpty())
    require(date in 0..(System.currentTimeMillis()))
  }
}