plugins {
    `app-desktop`
    application
}

group = "com.mityushovn.miningquiz.app"
version = "0.1"

sourceSets.main {
    java.srcDirs("src/jvmMain/kotlin")
}

application {
    mainClass.set("com.mityushovn.miningquiz.app.MainKt") // The main class of the application
}
