plugins {
    `library-android`
    id("kotlin-kapt")
}

android {
    namespace = "com.mityushovn.miningquiz.feature_settings"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.android.material)
    // 1) common
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

    // 5) Test
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.androidx.junit.ext)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)

    implementation(project(":androidApp:core-injector"))
    implementation(project(":androidApp:core-domain"))
    implementation(project(":androidApp:core-design"))
    implementation(project(":androidApp:core-utils"))
    implementation(project(":androidApp:data-settings"))
}
