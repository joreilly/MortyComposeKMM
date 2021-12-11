import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.apollographql.apollo3")
}

// workaround for https://youtrack.jetbrains.com/issue/KT-43944
android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}


kotlin {
    android()

    val iosArm64 = iosArm64()
    val iosX64 = iosX64()
    val iosSimulatorArm64 = iosSimulatorArm64()

    targets.named<KotlinNativeTarget>("iosX64") {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {
            export("io.github.kuuuurt:multiplatform-paging-iosX64:${Versions.multiplatformPaging}")
        }
    }

    targets.named<KotlinNativeTarget>("iosArm64") {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>().configureEach {
            export("io.github.kuuuurt:multiplatform-paging-iosArm64:${Versions.multiplatformPaging}")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Deps.Kotlinx.coroutinesCore) {
                    isForce = true
                }

                // koin
                api(Koin.core)
                api(Koin.test)

                api(Deps.apolloRuntime)
                api(Deps.multiplatformPaging)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.1")
            }
        }

        val appleMain by creating {
            dependsOn(commonMain)
        }
        val appleTest by creating {
            dependsOn(commonTest)
        }

        listOf(
            iosArm64, iosX64, iosSimulatorArm64
        ).forEach {
            it.binaries.framework {
                baseName = "shared"
            }
            getByName("${it.targetName}Main") {
                dependsOn(appleMain)
            }
            getByName("${it.targetName}Test") {
                dependsOn(appleTest)
            }
        }

    }
}

android {
    compileSdk = 30
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

apollo {
    packageName.set("dev.johnoreilly.mortycomposekmm")
    generateOptionalOperationVariables.set(false)
}
