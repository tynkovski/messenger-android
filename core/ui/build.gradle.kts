plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.library.compose)
    alias(libs.plugins.messenger.android.library.jacoco)
}

android {
    namespace = "com.tynkovski.apps.messenger.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    androidTestImplementation(projects.core.testing)
}