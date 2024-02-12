@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}
apply { from("../base.gradle") }

android {
    namespace = "com.sergiupuhalschi.repository"
}

dependencies {

    implementation(project(":networkservice"))
    implementation(project(":storage"))
    implementation(project(":common"))
}