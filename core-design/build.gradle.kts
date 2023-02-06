plugins {
    `library-android`
}

android {
    namespace = "com.mityushovn.miningquiz.core_design"
}

dependencies {
    // 1) Material components
    implementation(libs.android.material)
    // 2) Splash screen
    api(libs.android.core.splashscreen)
}