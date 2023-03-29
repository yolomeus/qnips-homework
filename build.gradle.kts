import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.7.20"
    id("org.jetbrains.compose")
}

group = "com.fabian"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("androidx.compose.material:material-icons-extended:1.3.1")
            }
        }
        val ktorVersion = "2.2.4"
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "qnips"
            packageVersion = "1.0.0"
        }
    }
}
