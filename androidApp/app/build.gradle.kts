plugins {
    `app-android`
    id("kotlin-kapt")
}

android {
    buildFeatures {
        dataBinding = true
    }
}
dependencies {
    // 1) common
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.android.constraintlayout)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime.ktx)

    // 2) Logging
    implementation(libs.timber)

    // 3) Test
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.androidx.junit.ext)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.espresso.contrib)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)

    // 4) Coroutines
    implementation(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    // 5) Navigation
    // Kotlin
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    androidTestImplementation(libs.navigation.testing)
    implementation(libs.fragment.ktx)
    debugImplementation(libs.fragment.testing)

    // 6) Dependency injection
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)

    // 7) Kotlin
    implementation(libs.kotlin.reflect)

    // 8) Firebase
//    androidTestImplementation(libs.firebase.testlab)

    // 9) WorkManager
    implementation(libs.workmanager.runtime.ktx)
    androidTestImplementation(libs.workmanager.testing)
    // 10) utils
    implementation(libs.google.guava)

    // 11) implementing the feature modules
    implementation(project(":androidApp:core-domain"))
    implementation(project(":androidApp:core-injector"))
    implementation(project(":androidApp:core-design"))
    implementation(project(":androidApp:data-questions"))
    implementation(project(":androidApp:feature-home"))
    implementation(project(":androidApp:feature-statistics"))
    implementation(project(":androidApp:feature-quizlist"))
    implementation(project(":androidApp:feature-game"))
    implementation(project(":androidApp:feature-settings"))
}

tasks.withType<Test>().configureEach {
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    setForkEvery(100)
    reports.html.required.set(false)
    reports.junitXml.required.set(false)
    if (!project.hasProperty("createReports")) {
        reports.html.required.set(false)
        reports.junitXml.required.set(false)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
}

