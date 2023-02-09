plugins {
    `library-android`
}

dependencies {
    implementation(libs.timber)

    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    implementation(project(":core-domain"))
}