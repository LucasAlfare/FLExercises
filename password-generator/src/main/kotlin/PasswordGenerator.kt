import kotlin.random.Random

internal val specialCharacters = "!@#$%¨&*(){}\\|/,.;:?~^´`[]{}-_=+ªº".toList()
internal val numbers = "0123456789".toList()
internal val upperLetters = (65..90).map { it.toChar().toString() }
internal val lowerLetters = (97..122).map { it.toChar().toString() }

object PasswordGenerator {

  fun getRandomPasswordByOptions(
    size: Int = 15,
    includeNumbers: Boolean = true,
    includeLetters: Boolean = true,
    includeSpecialCharacters: Boolean = true
  ): String {
    if (size < 3) {
      return getRandomPasswordByOptions(
        size = 3,
        includeNumbers = includeNumbers,
        includeLetters = includeLetters,
        includeSpecialCharacters = includeSpecialCharacters
      )
    }

    if (!includeNumbers && !includeLetters && !includeSpecialCharacters) {
      return getRandomPasswordByOptions(
        size = size,
        includeNumbers = false,
        includeLetters = true,
        includeSpecialCharacters = false
      )
    }

    val options = mutableListOf<Any>()
    if (includeNumbers) options.addAll(numbers)
    if (includeSpecialCharacters) options.addAll(specialCharacters)
    if (includeLetters) {
      options.addAll(upperLetters)
      options.addAll(lowerLetters)
    }

    return buildString {
      repeat(size) {
        val randomIndex = Random.nextInt(options.size)
        append(options[randomIndex])
      }
    }
  }
}