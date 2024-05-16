plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.apollographql.apollo3")
    id("com.google.devtools.ksp")
    alias(libs.plugins.kmpNativeCoroutines)
}

kotlin {
    jvmToolchain(17)

    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.koin.core)

                api(libs.apollo.runtime)
                implementation(libs.apollo.normalized.cache)
                implementation(libs.apollo.normalized.cache.sqlite)

                api(libs.androidx.paging.common)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    namespace = "dev.johnoreilly.mortycomposekmm.shared"
}


apollo {
    service("service") {
        packageName.set("dev.johnoreilly.mortycomposekmm")
        generateOptionalOperationVariables.set(false)
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
