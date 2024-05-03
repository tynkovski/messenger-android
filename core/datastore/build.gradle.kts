plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.library.jacoco)
    alias(libs.plugins.messenger.android.hilt)
}

android {
    namespace = "com.tynkovski.apps.messenger.core.datastore"

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(libs.androidx.dataStore.core)
    api(projects.core.model)

    implementation(projects.core.common)

    testImplementation(libs.kotlinx.coroutines.test)
}