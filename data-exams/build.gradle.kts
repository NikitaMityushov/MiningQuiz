plugins {
    `library-android`
}

android {
    namespace = "com.mityushovn.miningquiz.data_exams"
}

dependencies {
    implementation(libs.timber)

    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    implementation(project(":core-utils"))
    implementation(project(":core-domain"))
}