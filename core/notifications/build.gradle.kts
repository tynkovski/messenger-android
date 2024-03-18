plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.hilt)
}

android {
    namespace = "com.tynkovski.apps.messenger.core.notifications"
}

dependencies {
    api(projects.core.model)

    implementation(projects.core.common)

    compileOnly(platform(libs.androidx.compose.bom))
    compileOnly(libs.androidx.compose.runtime)
}