plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mityushovn.miningquiz.data_settings"
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(project(":core-domain"))
}