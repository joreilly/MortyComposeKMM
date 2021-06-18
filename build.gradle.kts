buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-beta04")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
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