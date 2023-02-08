import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

configure<BaseAppModuleExtension> {
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
            isShrinkResources = true
            isCrunchPngs = true
            isMinifyEnabled = true
            // enable ProGuard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isShrinkResources = false
            isMinifyEnabled = false
        }

        maybeCreate("debugShrank").apply {
            initWith(getByName("debug"))
            isShrinkResources = true
            isCrunchPngs = true
            isMinifyEnabled = true
            isDebuggable = true
            // enable ProGuard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        abortOnError = false
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xjvm-default=compatibility",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}