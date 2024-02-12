plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}
apply { from("../base.gradle") }

android {
    namespace = "com.sergiupuhalschi.storage"
}

dependencies {

    implementation(libs.dataStore)
    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)
}