plugins {
    `library-android`
    id("kotlin-kapt")
}

android {
    namespace = "com.mityushovn.miningquiz.game_feature"

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

    implementation(project(":androidApp:core-domain"))
    implementation(project(":androidApp:core-utils"))
    implementation(project(":androidApp:core-design"))
    implementation(project(":androidApp:core-injector"))
    implementation(project(":androidApp:data-exams"))
    implementation(project(":androidApp:data-topics"))
    implementation(project(":androidApp:data-questions"))
    implementation(project(":androidApp:data-attempts"))
    implementation(project(":androidApp:data-settings"))
}