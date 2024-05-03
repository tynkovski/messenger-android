plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.library.jacoco)
    alias(libs.plugins.messenger.android.hilt)
    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.tynkovski.apps.messenger.core.network"
    buildFeatures {
        buildConfig = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    api(libs.kotlinx.datetime)
    api(projects.core.common)
    api(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    testImplementation(libs.kotlinx.coroutines.test)
}