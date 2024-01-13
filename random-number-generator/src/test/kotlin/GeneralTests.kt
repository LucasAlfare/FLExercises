import kotlin.test.Test
import kotlin.test.assertTrue


class GeneralTests {

  @Test
  fun `test randomNumberGenerator()`() {
    val numbers = mutableListOf<Long>()
    while (numbers.size < 10) {
      val gen = RandomNumberGenerator.getRandomNumber(maxValue = 10)
      assertTrue(gen in 0..10)
      if (!numbers.contains(gen)) {
        numbers += gen
      }
    }
  }
}