plugins {
    `library-android`
}

android {
    namespace = "com.mityushovn.miningquiz.core_testing"
}

dependencies {
    implementation(libs.junit)
    implementation(libs.mockito)
    implementation(libs.android.arch.core.testing)
    implementation(libs.androidx.junit.ext)
    implementation(libs.espresso.core)
    implementation(libs.espresso.contrib)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.test)
}