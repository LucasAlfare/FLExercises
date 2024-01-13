plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "FLExercises"

include(
  ":random-number-generator",
  ":password-generator"
)

