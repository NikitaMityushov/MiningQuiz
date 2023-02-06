plugins {
    `library-android`
}

android {
    namespace = "com.mityushovn.miningquiz.core_testing"
}

dependencies {
    val mockitoVersion = "3.2.0"
    val junitVersion = "4.12"
    val junitExtVersion = "1.1.3"
    val espressoVersion = "3.4.0"
    val androidxTestVersion = "1.4.0"
    val androidArchVersion = "2.1.0"
    val coroutinesVersion = "1.6.4"

    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")
    testImplementation("androidx.arch.core:core-testing:$androidArchVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitExtVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    implementation("androidx.test.espresso:espresso-contrib:$espressoVersion")
    androidTestImplementation("androidx.test:runner:$androidxTestVersion")
    androidTestImplementation("androidx.test:rules:$androidxTestVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}