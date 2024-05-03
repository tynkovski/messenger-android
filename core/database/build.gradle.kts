plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.library.jacoco)
    alias(libs.plugins.messenger.android.hilt)
    alias(libs.plugins.messenger.android.room)
}

android {
    namespace = "com.tynkovski.apps.messenger.core.database"
}

dependencies {
    api(projects.core.model)

    implementation(libs.kotlinx.datetime)

    androidTestImplementation(projects.core.testing)
}