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
            // 2) Logging
            library("timber", "com.jakewharton.timber:timber:5.0.1")
            // 3) Testing libraries
            library("junit", "junit:junit:4.12")
            library("mockito", "org.mockito.kotlin:mockito-kotlin:3.2.0")
            library("android-arch-core-testing", "androidx.arch.core:core-testing:2.1.0")
            library("android-core-splashscreen", "androidx.core:core-splashscreen:1.0.0-beta02")
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