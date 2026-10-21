@file:OptIn(ExperimentalSwiftExportDsl::class)

import org.jetbrains.kotlin.gradle.plugin.mpp.export.SwiftExportVisibility
import org.jetbrains.kotlin.gradle.swiftexport.ExperimentalSwiftExportDsl

// Umbrella for Swift Export's `export { swift { } }` DSL: :shared (an `api` dep here) is exported,
// along with whatever :shared exposes via `api`. Libraries Swift doesn't use directly are HIDDEN,
// which cuts them down to stubs for the few types :shared's API mentions. A function returning a
// hidden type is generated as a fatalError() stub with no warning, so check the generated Swift.
plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":shared"))
        }
    }

    export {
        swift {
            moduleName = "MortyKit"
            xcodeIntegration {
                listOf(
                    "io.insert-koin:koin-core",
                    "androidx.paging:paging-common",
                    "org.jetbrains.androidx.lifecycle:lifecycle-viewmodel",
                    "com.apollographql.apollo:apollo-runtime",
                    "com.apollographql.apollo:apollo-api",
                ).forEach { configure(it, SwiftExportVisibility.HIDDEN) }
            }
        }
    }
}
