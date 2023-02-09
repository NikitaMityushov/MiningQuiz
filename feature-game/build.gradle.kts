plugins {
    `library-android`
    id("kotlin-kapt")
}

android {

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

    implementation(project(":core-domain"))
    implementation(project(":core-utils"))
    implementation(project(":core-design"))
    implementation(project(":core-injector"))
    implementation(project(":data-exams"))
    implementation(project(":data-topics"))
    implementation(project(":data-questions"))
    implementation(project(":data-attempts"))
    implementation(project(":data-settings"))
}