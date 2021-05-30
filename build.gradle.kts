buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
            content {
                includeModule("com.google.dagger", "hilt-android-gradle-plugin")
            }
        }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-beta03")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")

        // https://github.com/google/dagger/issues/2631
        classpath("com.google.dagger:hilt-android-gradle-plugin:HEAD-SNAPSHOT")

        classpath("com.apollographql.apollo:apollo-gradle-plugin:${Versions.apollo}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}