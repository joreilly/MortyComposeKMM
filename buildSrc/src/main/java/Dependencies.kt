
object Versions {
    const val kotlinVersion = "1.8.21"
    const val kotlinxCoroutines = "1.7.1"
    const val apollo = "3.8.1"
    const val koinCore = "3.4.0"
    const val koinAndroid = "3.4.0"
    const val koinAndroidCompose = "3.4.4"

    const val multiplatformPaging = "0.5.0"

    const val compose = "1.4.3"
    const val composeCompiler = "1.4.7"
    const val navCompose = "2.5.3"
    const val pagingCompose = "1.0.0-alpha16"
    const val accompanist = "0.30.1"
    const val coilCompose = "2.4.0"

    const val junit = "4.13"
}


object AndroidSdk {
    const val min = 21
    const val compile = 33
    const val target = compile
}

object Deps {
    const val apolloRuntime = "com.apollographql.apollo3:apollo-runtime:${Versions.apollo}"
    const val apolloNormalizedCache = "com.apollographql.apollo3:apollo-normalized-cache:${Versions.apollo}"
    const val multiplatformPaging = "io.github.kuuuurt:multiplatform-paging:${Versions.multiplatformPaging}"

    object Kotlinx {
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    }
}

object Compose {
    const val compiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val activity = "androidx.activity:activity-compose:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navCompose}"
    const val paging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilCompose}"
}

object Koin {
    val core = "io.insert-koin:koin-core:${Versions.koinCore}"
    val test = "io.insert-koin:koin-test:${Versions.koinCore}"
    val testJUnit4 = "io.insert-koin:koin-test-junit4:${Versions.koinCore}"
    val android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    val compose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
}

