
object Versions {
    const val kotlin = "1.5.10"
    const val kotlinxCoroutines = "1.5.0-native-mt"
    const val hilt = "2.35"
    const val apollo = "2.5.8"
    const val multiplatformPaging = "0.3.7"

    const val compose = "1.0.0-beta08"
    const val nav_compose = "2.4.0-alpha02"
    const val paging_compose = "1.0.0-alpha10"
    const val accompanist = "0.11.0"

    const val junit = "4.13"
    const val testRunner = "1.3.0"
}


object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

object Deps {
    const val apolloRuntime = "com.apollographql.apollo:apollo-runtime-kotlin:${Versions.apollo}"
    const val multiplatformPaging = "com.kuuuurt:multiplatform-paging:${Versions.multiplatformPaging}"
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
    const val accompanist= "com.google.accompanist:accompanist-coil:${Versions.accompanist}"
}



