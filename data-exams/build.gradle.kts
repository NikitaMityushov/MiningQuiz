plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mityushovn.miningquiz.data_exams"
    compileSdk = 31
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(libs.timber)

    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    implementation(project(":core-utils"))
    implementation(project(":core-domain"))
}