plugins {
    `library-android`
    id("kotlin-parcelize")
}

android {
    namespace = "com.mityushovn.miningquiz.core_domain"
}

dependencies {
    // 1) Coroutines
    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)
    // 2) Modules
    implementation(project(":core-utils"))
}