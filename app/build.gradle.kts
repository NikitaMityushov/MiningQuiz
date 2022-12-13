plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.mityushovn.miningquiz"
        minSdk = 24
        targetSdk = 31
        versionCode = 5
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        abortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=compatibility")
    }


    buildFeatures {
        dataBinding = true
    }
}
dependencies {
    // 1) common
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.android.constraintlayout)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime.ktx)

    // 2) Logging
    implementation(libs.timber)

    // 3) Test
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.androidx.junit.ext)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.espresso.contrib)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)

    // 4) Coroutines
    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    // 5) Navigation
    // Kotlin
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    androidTestImplementation(libs.navigation.testing)
    implementation(libs.fragment.ktx)
    debugImplementation(libs.fragment.testing)

    // 6) Dependency injection
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)

    // 7) Kotlin
    implementation(libs.kotlin.reflect)

    // 8) Firebase
//    androidTestImplementation(libs.firebase.testlab)

    // 9) WorkManager
    implementation(libs.workmanager.runtime.ktx)
    androidTestImplementation(libs.workmanager.testing)
    // 10) utils
    implementation(libs.google.guava)

    // 11) implementing the feature modules
    implementation(project(":core-domain"))
    implementation(project(":core-injector"))
    implementation(project(":core-design"))
    implementation(project(":data-questions"))
    implementation(project(":feature-home"))
    implementation(project(":feature-statistics"))
    implementation(project(":feature-quizlist"))
    implementation(project(":feature-game"))
    implementation(project(":feature-settings"))
}

