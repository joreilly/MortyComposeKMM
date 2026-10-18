pluginManagement {
    listOf(repositories, dependencyResolutionManagement.repositories).forEach {
        it.apply {
            mavenCentral()
            google()
            gradlePluginPortal()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven("https://androidx.dev/storage/compose-compiler/repository")
            // Kotlin dev builds (2.5.0-Beta2-*) for Swift Export; drop once Beta2 is on Maven Central
            maven("https://packages.jetbrains.team/maven/p/kt/dev")
        }
    }
}

rootProject.name = "MortyComposeKMM"


include(":androidApp")
include(":shared")
include(":ios-export")

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    "This project needs to be run with Java 17 or higher (found: ${JavaVersion.current()})."
}
