
object Versions {
    const val kotlinVersion = "1.6.21"
    const val kotlinxCoroutines = "1.6.1"
    const val koin = "3.2.0"
    const val apollo = "3.3.0"
    const val multiplatformPaging = "0.4.7"

    const val compose = "1.2.0-rc01"
    const val composeCompiler = "1.2.0-rc01"
    const val navCompose = "2.4.2"
    const val pagingCompose = "1.0.0-alpha15"
    const val accompanist = "0.23.0"
    const val coilComposeVersion = "2.1.0"

    const val junit = "4.13"
}


object AndroidSdk {
    const val min = 21
    const val compile = 32
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
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilComposeVersion}"
}

object Koin {
    val core = "io.insert-koin:koin-core:${Versions.koin}"
    val test = "io.insert-koin:koin-test:${Versions.koin}"
    val testJUnit4 = "io.insert-koin:koin-test-junit4:${Versions.koin}"
    val android = "io.insert-koin:koin-android:${Versions.koin}"
    val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

