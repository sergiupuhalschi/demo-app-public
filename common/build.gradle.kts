plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}
apply { from("../base.gradle") }

android {
    namespace = "com.sergiupuhalschi.common"

    buildFeatures {
        buildConfig = true
    }
}