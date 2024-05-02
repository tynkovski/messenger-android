plugins {
    alias(libs.plugins.messenger.android.feature)
}

android {
    namespace = "com.tynkovski.apps.messenger.feature.contacts"
}

dependencies {
    implementation(projects.core.data)
}