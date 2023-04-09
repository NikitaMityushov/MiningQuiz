plugins {
    `app-desktop`
}

group = "com.mityushovn.miningquiz.app"
version = "0.1"

sourceSets.main {
    java.srcDirs("src/jvmMain/kotlin")
}

sourceSets.test {
    java.srcDirs("src/jvmTest/kotlin")
}

dependencies {
    implementation(compose.desktop.common)
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.mityushovn.miningquiz.app.MainKt"
    }
}
