plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.library.jacoco)
    alias(libs.plugins.messenger.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.tynkovski.apps.messenger.core.data"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.datastore)
    api(projects.core.network)

    implementation(projects.core.notifications)

    implementation(libs.androidx.paging)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)

    testImplementation(projects.core.testing)
}