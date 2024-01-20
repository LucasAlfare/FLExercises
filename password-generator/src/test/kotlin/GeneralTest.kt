import kotlin.test.Test
import kotlin.test.assertTrue

class GeneralTest {

  @Test
  fun `test password generator()`() {
    val gen = PasswordGenerator.getRandomPasswordByOptions(size = 10)
    assertTrue(gen.length == 10)

    gen.forEach {
      assertTrue(
        specialCharacters.contains(it) ||
                numbers.contains(it) ||
                upperLetters.contains(it.toString()) ||
                lowerLetters.contains(it.toString())
      )
    }
  }
}