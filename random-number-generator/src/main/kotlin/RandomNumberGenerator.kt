import kotlin.random.Random

object RandomNumberGenerator {
  fun getRandomNumber(minValue: Long = 0, maxValue: Long = Long.MAX_VALUE) =
    Random.nextLong((maxValue - minValue) + 1) + minValue
}