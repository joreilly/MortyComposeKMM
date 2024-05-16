# MortyCompose

![kotlin-version](https://img.shields.io/badge/kotlin-1.9.20-blue)


Kotlin Multiplatform sample that demonstrates use of GraphQL + Jetpack Compose and SwiftUI (based on https://github.com/Dimillian/MortyUI SwiftUI project).
Makes use of [Apollo library's Kotlin Multiplatform support](https://www.apollographql.com/docs/android/essentials/get-started-multiplatform/) and is also included as one of the samples for that project.


Related Posts:
* [Jetpack Compose and GraphQL, a very merry combination!](https://johnoreilly.dev/posts/jetpack-compose-graphql/)


![Characters Android Screenshot](/art/characters_screenshot.png?raw=true)


The project also now makes use of the KMP support now provided by the Jetpack
Paging library.


## iOS App

A small SwiftUI iOS app that uses same shared Kotlin Multiplatform code is in the `iosApp` folder
 (shows Characters screen using more or less same SwiftUI code that's in https://github.com/Dimillian/MortyUI)

![Characters iOS Screenshot](/art/characters_screenshot_ios.png?raw=true)
