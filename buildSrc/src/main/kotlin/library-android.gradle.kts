import com.android.build.gradle.LibraryExtension

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

configure<LibraryExtension> {
    compileSdk = Contract.COMPILE_SDK

    namespace = "${Contract.NAMESPACE}.${
        projectDir.nameWithoutExtension.replace(
            "-",
            "_"
        )
    }" // for example "com.mityushov.miningquiz.core_utils"

    defaultConfig {
        minSdk = Contract.MIN_SDK
        targetSdk = Contract.TARGET_SDK
    }

    buildTypes {
        maybeCreate("debugShrank")
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
