# MortyCompose

![kotlin-version](https://img.shields.io/badge/kotlin-2.0.0-blue)


Kotlin Multiplatform sample that demonstrates use of GraphQL + Jetpack Compose and SwiftUI (based on https://github.com/Dimillian/MortyUI SwiftUI project).
Uses [Apollo library's Kotlin Multiplatform support](https://www.apollographql.com/docs/android/essentials/get-started-multiplatform/) and is also included as one of the samples for that project.

The project also now makes use of the KMP support now provided by the Jetpack Paging library.


Related Posts:
* [Jetpack Compose and GraphQL, a very merry combination!](https://johnoreilly.dev/posts/jetpack-compose-graphql/)


## Android App

![Characters Android Screenshot](/art/characters_screenshot.png?raw=true)




## iOS App

A small SwiftUI iOS app that uses same shared Kotlin Multiplatform code is in the `iosApp` folder
 (shows Characters screen using more or less same SwiftUI code that's in https://github.com/Dimillian/MortyUI)

![Characters iOS Screenshot](/art/characters_screenshot_ios.png?raw=true)
