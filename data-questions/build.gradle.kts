plugins {
    `library-android`
}

android {
    namespace = "com.mityushovn.miningquiz.data_questions"
}

dependencies {
    implementation(libs.timber)

    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    implementation(project(":core-domain"))
}