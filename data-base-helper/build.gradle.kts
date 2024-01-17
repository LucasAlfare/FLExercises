plugins {
  kotlin("jvm") version "1.9.21"
}

group = "com.lucasalfare.flexercises"
version = "1.0-SNAPSHOT"

repositories {
  google()
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
  implementation("org.jetbrains.exposed:exposed-core:0.46.0")
  implementation("org.jetbrains.exposed:exposed-jdbc:0.46.0")
  implementation("com.zaxxer:HikariCP:5.1.0")
  implementation("org.xerial:sqlite-jdbc:3.44.1.0") //can be switched to Postgres

  testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}