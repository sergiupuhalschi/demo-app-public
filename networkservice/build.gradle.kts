plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}
apply { from("../base.gradle") }

android {
    namespace = "com.sergiupuhalschi.networkservice"

    defaultConfig {
        buildConfigField("String", "MOCKY_API_URL", "\"https://run.mocky.io/\"")
    }
}

dependencies {
    implementation(project(":common"))

    implementation(libs.retrofit)
    implementation(libs.okHttpLogging)
    implementation(libs.retrofitMoshiConverter)
    implementation(libs.reactiveNetwork)

    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAuth)
    implementation(libs.firebaseUiAuth)
}