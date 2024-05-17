pluginManagement {
    listOf(repositories, dependencyResolutionManagement.repositories).forEach {
        it.apply {
            mavenCentral()
            google()
            gradlePluginPortal()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            maven("https://androidx.dev/storage/compose-compiler/repository")
        }
    }
}

rootProject.name = "MortyComposeKMM"


include(":androidApp")
include(":shared")

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    "This project needs to be run with Java 17 or higher (found: ${JavaVersion.current()})."
}
