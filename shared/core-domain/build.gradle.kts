plugins {
    `multi-library`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.datetime)
                implementation(libs.coroutines.core)
            }
        }
        val commonTest by getting
    }
}

android {
    namespace = "com.mityushovn.miningquiz.core_domain"
}