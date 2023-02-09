buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.google.agp)
        classpath (libs.jetbrains.kotlin)
    }

}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}