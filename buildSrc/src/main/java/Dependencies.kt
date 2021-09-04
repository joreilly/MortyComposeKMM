
object Versions {
    const val kotlinxCoroutines = "1.5.1-new-mm-dev2"
    const val koin = "3.1.2"
    const val apollo = "3.0.0-alpha03"
    const val multiplatformPaging = "0.4.1"

    const val compose = "1.0.1"
    const val nav_compose = "2.4.0-alpha06"
    const val paging_compose = "1.0.0-alpha12"
    const val accompanist = "0.16.0"
    const val coilComposeVersion = "1.3.1"

    const val junit = "4.13"
    const val testRunner = "1.3.0"
}


object AndroidSdk {
    const val min = 21
    const val compile = 30
    const val target = compile
}

object Deps {
    const val apolloRuntime = "com.apollographql.apollo3:apollo-runtime:${Versions.apollo}"
    const val multiplatformPaging = "io.github.kuuuurt:multiplatform-paging:${Versions.multiplatformPaging}"
}

object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val activity = "androidx.activity:activity-compose:${Versions.compose}"
    const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
    const val paging = "androidx.paging:paging-compose:${Versions.paging_compose}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilComposeVersion}"
}

object Koin {
    val core = "io.insert-koin:koin-core:${Versions.koin}"
    val test = "io.insert-koin:koin-test:${Versions.koin}"
    val testJUnit4 = "io.insert-koin:koin-test-junit4:${Versions.koin}"
    val android = "io.insert-koin:koin-android:${Versions.koin}"
    val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

