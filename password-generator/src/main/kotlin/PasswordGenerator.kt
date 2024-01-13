import kotlin.random.Random

private val specialCharacters = "!@#$%¨&*(){}\\|/,.;:?~^´`[]{}-_=+ªº".toList()
private val numbers = "0123456789".toList()
private val upperLetters = (65..90).map { it.toChar().toString() }
private val lowerLetters = (97..122).map { it.toChar().toString() }

object PasswordGenerator {

  fun getRandomPassword(
    size: Int = 15,
    includeNumbers: Boolean = true,
    includeLetters: Boolean = true,
    includeSpecialCharacters: Boolean = true
  ): String {
    if (!includeNumbers && !includeLetters && includeSpecialCharacters)
      return getRandomPassword(includeLetters = true)

    if (size == 0)
      return getRandomPassword(size = 3)

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