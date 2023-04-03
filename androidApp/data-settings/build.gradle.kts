plugins {
    `library-android`
}

android {
    namespace = "com.mityushovn.miningquiz.data_settings"
}

dependencies {
    implementation(project(":androidApp:core-domain"))
}