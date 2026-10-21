plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.apollo)
    alias(libs.plugins.ksp)
}

kotlin {
    // Swift module name and package flattening for this module, picked up by :ios-export.
    @OptIn(org.jetbrains.kotlin.gradle.swiftexport.ExperimentalSwiftExportDsl::class)
    export {
        swift {
            moduleName = "Shared"
            rootPackage = "dev.johnoreilly.mortycomposekmm"
        }
    }

    jvmToolchain(17)

    android {
        namespace = "dev.johnoreilly.mortycomposekmm.shared"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }

        androidResources { enable = true }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.koin.core)

                api(libs.apollo.runtime)
                implementation(libs.apollo.normalized.cache)
                implementation(libs.apollo.normalized.cache.sqlite)

                implementation(libs.androidx.paging.common)
                implementation(libs.lifecycle.viewmodel)
            }
        }
    }
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
