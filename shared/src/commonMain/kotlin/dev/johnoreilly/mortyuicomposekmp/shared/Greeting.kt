package dev.johnoreilly.mortyuicomposekmp.shared


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
