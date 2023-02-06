plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mityushovn.miningquiz.core_design"
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31
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
    }
}

dependencies {
    // 1) Material components
    implementation(libs.android.material)
    // 2) Splash screen
    api(libs.android.core.splashscreen)
}