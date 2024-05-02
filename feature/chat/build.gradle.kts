plugins {
    alias(libs.plugins.messenger.android.feature)
}

android {
    namespace = "com.tynkovski.apps.messenger.feature.chat"
}

dependencies {
    implementation(projects.core.data)
}