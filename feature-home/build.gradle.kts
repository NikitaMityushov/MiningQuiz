plugins {
    `library-android`
    id("kotlin-kapt")
}

android {
    namespace = "com.mityushovn.miningquiz.home_feature"

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

    // Изменить на implementation после полной разбивки на модули
    api(project(":core-utils"))
    api(project(":core-design"))
}