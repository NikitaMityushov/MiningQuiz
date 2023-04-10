import common.Contract
import gradle.kotlin.dsl.accessors._50f31d4cbcc144836b91e7fac2864e43.kotlinOptions
import gradle.kotlin.dsl.accessors._70d7307cc4ee43dee6ebafc3a7557b45.android
import gradle.kotlin.dsl.accessors._70d7307cc4ee43dee6ebafc3a7557b45.kotlin
import org.gradle.kotlin.dsl.kotlin

plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    android()
//    jvm("desktop")
}

android {
    compileSdk = Contract.COMPILE_SDK

    defaultConfig {
        minSdk = Contract.MIN_SDK
        targetSdk = Contract.TARGET_SDK
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
