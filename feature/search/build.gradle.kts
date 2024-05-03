plugins {
    alias(libs.plugins.messenger.android.feature)
    alias(libs.plugins.messenger.android.library.compose)
    alias(libs.plugins.messenger.android.library.jacoco)
}

android {
    namespace = "com.tynkovski.apps.messenger.feature.search"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)

    testImplementation(projects.core.testing)

    androidTestImplementation(projects.core.testing)
}