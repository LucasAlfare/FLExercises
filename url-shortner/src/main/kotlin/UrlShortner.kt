private const val SUFFIX_LENGTH = 10

object UrlShortner {

  private val options = mutableListOf<String>()

  init {
    options.addAll("0123456789".map { it.toString() })
    options.addAll((65..90).map { it.toChar().toString() })
    options.addAll((97..122).map { it.toChar().toString() })
  }

  fun generateShortUrl(
    rootApiUrl: String = "http://fl-shortner.com/"
  ) = buildString {
    append(rootApiUrl)
    repeat(SUFFIX_LENGTH) {
      append(options.random())
    }
  }
}