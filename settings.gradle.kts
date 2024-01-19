plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "FLExercises"

include(
  ":data-base-helper",
  ":exercises-theme",
  ":random-number-generator",
  ":password-generator",
  ":url-shortner",
  ":todo-list"
)