buildscript {
    val kotlinVersion: String by project

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("com.apollographql.apollo3:apollo-gradle-plugin:${Versions.apollo}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}