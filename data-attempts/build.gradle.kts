plugins {
    `library-android`
}

android {
    namespace = "com.mityushovn.miningquiz.data_attempts"
}

dependencies {
    implementation(libs.timber)

    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    implementation(project(":core-utils"))
    api(project(":core-domain"))
}