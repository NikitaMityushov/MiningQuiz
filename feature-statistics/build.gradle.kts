plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        abortOnError = false
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    // 1) Common
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    androidTestImplementation(libs.navigation.testing)
    implementation(libs.fragment.ktx)
    debugImplementation(libs.fragment.testing)

    // 2) Dependency injection
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)

    implementation(libs.kotlin.reflect)

    // 3) Logging
    implementation(libs.timber)

    // 4) Coroutines
    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    // 5) Test
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.androidx.junit.ext)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)

    // implementation of core modules
    implementation(project(":core-utils"))
    implementation(project(":core-design"))
    implementation(project(":core-testing"))
    implementation(project(":data-attempts"))
    implementation(project(":core-injector"))
}