plugins {
    `library-android`
    id("kotlin-kapt")
}

android {
    namespace = "com.mityushovn.miningquiz.core_injector"
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