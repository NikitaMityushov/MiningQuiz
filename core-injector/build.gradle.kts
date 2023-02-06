plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.mityushovn.miningquiz.core_injector"
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
    implementation(libs.fragment.ktx)
    debugImplementation(libs.fragment.testing)

    // Dependency injection
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)

    // Kotlin
    implementation(libs.kotlin.reflect)
}