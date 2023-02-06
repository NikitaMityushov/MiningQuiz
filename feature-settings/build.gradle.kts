plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.mityushovn.miningquiz.feature_settings"
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
        freeCompilerArgs = listOf("-Xjvm-default=compatibility", "-opt-in=kotlin.RequiresOptIn")
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.android.material)
    // 1) common
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

    implementation(project(":core-injector"))
    implementation(project(":core-domain"))
    implementation(project(":core-design"))
    implementation(project(":core-utils"))
    implementation(project(":data-settings"))
}
