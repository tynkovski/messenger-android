plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.library.compose)
    alias(libs.plugins.messenger.android.hilt)
}

android {
    namespace = "com.tynkovski.apps.messenger.core.testing"
}

dependencies {
    api(kotlin("test"))
    api(libs.androidx.compose.ui.test)
    api(projects.core.data)
    api(projects.core.model)
    api(projects.core.notifications)

    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.datetime)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
}