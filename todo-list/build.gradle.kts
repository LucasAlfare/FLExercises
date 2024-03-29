import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("jvm") version "1.9.21"
  id("org.jetbrains.compose") version "1.5.11"
}

group = "com.lucasalfare.flexercises"
version = "1.0-SNAPSHOT"

repositories {
  google()
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
  implementation(compose.desktop.currentOs)
  implementation("org.jetbrains.compose.material3:material3-desktop:1.5.11")

  implementation(project(":exercises-theme"))

  implementation("org.jetbrains.exposed:exposed-core:0.46.0")
  implementation("org.jetbrains.exposed:exposed-jdbc:0.46.0")
  implementation("org.xerial:sqlite-jdbc:3.44.1.0") //can be switched to Postgres
  implementation("com.zaxxer:HikariCP:5.1.0")

  implementation(project(":data-base-helper"))

  testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}

compose.desktop {
  application {
    mainClass = "MainGuiKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "Todo-List"
      packageVersion = "1.0.0"

      windows {
        menu = true
        // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
        upgradeUuid = "61DAB35E-17CB-43B0-81D5-B30E1C0830FA"
      }
    }
  }
}