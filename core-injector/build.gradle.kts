plugins {
    `library-android`
    id("kotlin-kapt")
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