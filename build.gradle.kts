import java.io.ByteArrayOutputStream
import java.time.LocalDate

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val majorVersion = 0
val minorVersion = 1
val patchVersion = determinePatchVersion()
val commitHash = determineCommitHash()

group = "dev.xhyrom.anvil2polar"
version = "$majorVersion.$minorVersion.$patchVersion+$commitHash"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("dev.hollowcube:minestom-ce:a22d769740")
    implementation("dev.hollowcube:polar:1.4.0")
}

tasks.withType<Jar> {
    val date = LocalDate.now()

    manifest {
        attributes(
            "Main-Class" to "dev.xhyrom.anvil2polar.Anvil2Polar",
            "Implementation-Title" to "Anvil2Polar",
            "Implementation-Version" to "git-Anvil2Polar-$version",
            "Implementation-Vendor" to date,
            "Specification-Title" to "Anvil2Polar",
            "Specification-Version" to "git-Anvil2Polar-$version",
            "Specification-Vendor" to date
        )
        attributes["Main-Class"] = "dev.xhyrom.anvil2polar.Anvil2Polar"
    }
}

fun determinePatchVersion(): Int {
    val tagInfo = ByteArrayOutputStream()

    return try {
        exec {
            commandLine("git", "describe", "--tags")
            standardOutput = tagInfo
        }

        val result = tagInfo.toString()

        if (result.contains("-")) result.split("-")[1].toInt() else 0
    } catch (e: Exception) {
        0
    }
}

fun determineCommitHash(): String {
    val commitHashInfo = ByteArrayOutputStream()

    try {
        exec {
            commandLine("git", "rev-parse", "--short", "HEAD")
            standardOutput = commitHashInfo
        }
    } catch (e: Exception) {
        return "unknown"
    }

    return commitHashInfo.toString().trim()
}