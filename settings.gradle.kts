// enables Version Catalog feature for Gradle 7.4
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            // 1) Common android libraries
            library("androidx-core-ktx", "androidx.core:core-ktx:1.7.0")
            library("androidx-appcompat", "androidx.appcompat:appcompat:1.4.1")
            library("android-material", "com.google.android.material:material:1.5.0")
            library("android-constraintlayout", "androidx.constraintlayout:constraintlayout:2.1.3")
            library("lifecycle-livedata", "androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
            library("lifecycle-viewmodel", "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
            library("lifecycle-runtime-ktx", "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
            library("androidx-recyclerview", "androidx.recyclerview:recyclerview:1.2.1")
            // 2) Logging
            library("timber", "com.jakewharton.timber:timber:5.0.1")
            // 3) Testing libraries
            library("junit", "junit:junit:4.12")
            library("mockito", "org.mockito.kotlin:mockito-kotlin:3.2.0")
            library("android-arch-core-testing", "androidx.arch.core:core-testing:2.1.0")
            library("android-core-splashscreen", "androidx.core:core-splashscreen:1.0.0-beta02")
            library("androidx-junit-ext", "androidx.test.ext:junit:1.1.3")
            library("espresso-core", "androidx.test.espresso:espresso-core:3.4.0")
            library("espresso-contrib", "androidx.test.espresso:espresso-contrib:3.4.0")
            library("androidx-test-runner", "androidx.test:runner:1.4.0")
            library("androidx-test-rules", "androidx.test:rules:1.4.0")
            // 4) Coroutines
            library("coroutines-android", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
            library("coroutines-test", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
            // 5) Navigation component
            library("navigation-fragment-ktx", "androidx.navigation:navigation-fragment-ktx:2.3.5")
            library("navigation-ui-ktx", "androidx.navigation:navigation-ui-ktx:2.3.5")
            library("navigation-testing", "androidx.navigation:navigation-testing:2.3.5")
            library("fragment-ktx", "androidx.fragment:fragment-ktx:1.4.0")
            library("fragment-testing", "androidx.fragment:fragment-testing:1.4.0")
            // 6) Dagger
            library("dagger-android", "com.google.dagger:dagger-android:2.42")
            library("dagger-android-support", "com.google.dagger:dagger-android-support:2.42")
            library("dagger-compiler", "com.google.dagger:dagger-compiler:2.42")
            // 7) Kotlin
            library("kotlin-reflect", "org.jetbrains.kotlin:kotlin-reflect:1.5.21")
            // 8) Firebase
            library("firebase-testlab", "com.google.firebase:testlab-instr-lib:0.2")
            // 9) WorkManager
            library("workmanager-runtime-ktx", "androidx.work:work-runtime-ktx:2.7.1")
            library("workmanager-testing", "androidx.work:work-testing:2.7.1")
            // 10) utils
            library("google-guava", "com.google.guava:guava:27.0-android")
            // !!! для работы тестов в WorkManager 2.7.1 подходит только Guava 27, тк есть конфликт по ListenableFuture
        }
    }
}

rootProject.name = "MiningQuiz"
include(":app")
include(":core-injector")
include(":feature-home")
include(":feature-quizlist")
include(":feature-statistics")
include(":core-utils")
include(":core-design")
include(":data-attempts")
include(":feature-game")
include(":core-domain")
include(":data-questions")
include(":data-exams")
include(":data-topics")
include(":core-testing")
include(":feature-settings")
include(":data-settings")