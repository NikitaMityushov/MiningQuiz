import com.android.build.gradle.LibraryExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

configure<LibraryExtension> {
    compileSdk = Contract.COMPILE_SDK

    defaultConfig {
        minSdk = Contract.MIN_SDK
        targetSdk = Contract.TARGET_SDK
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
        freeCompilerArgs = listOf(
            "-Xjvm-default=compatibility",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}
