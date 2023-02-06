plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.mityushovn.miningquiz.home_feature"
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=compatibility", "-opt-in=kotlin.RequiresOptIn")
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

    // Изменить на implementation после полной разбивки на модули
    api(project(":core-utils"))
    api(project(":core-design"))
}