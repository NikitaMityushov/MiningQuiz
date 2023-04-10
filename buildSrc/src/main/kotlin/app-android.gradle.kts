import common.Contract
import gradle.kotlin.dsl.accessors._ec5312ae47488e8d285aabe2e0babffa.android
import gradle.kotlin.dsl.accessors._ec5312ae47488e8d285aabe2e0babffa.kotlinOptions

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Contract.COMPILE_SDK

    defaultConfig {
        applicationId = Contract.APP_ID
        minSdk = Contract.MIN_SDK
        targetSdk = Contract.TARGET_SDK
        versionCode = Contract.VERSION_CODE
        versionName = Contract.VERSION_NAME
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
        freeCompilerArgs = listOf(
            "-Xjvm-default=compatibility",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}