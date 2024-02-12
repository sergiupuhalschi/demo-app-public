@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.firebaseCrashlytics)
}
apply { from("../base.gradle") }

android {
    namespace = "com.sergiupuhalschi.presentation"

    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    implementation(project(":repository"))
    implementation(project(":common"))

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycleRuntimeKtx)

    implementation(platform(libs.composeBom))
    implementation(libs.material3)
    implementation(libs.material)
    implementation(libs.uiTooling)
    implementation(libs.uiToolingPreview)
    implementation(libs.activityCompose)
    implementation(libs.ui)
    implementation(libs.viewModel)
    implementation(libs.icons)
    implementation(libs.coil)

    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseCrashlytics)
    implementation(libs.firebaseUiAuth)

    implementation(platform(libs.koinBom))
    implementation(libs.koinAndroid)
    implementation(libs.koinCompose)
    implementation(libs.koinNavigation)
}