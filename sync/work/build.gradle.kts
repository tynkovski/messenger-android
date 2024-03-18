
plugins {
    alias(libs.plugins.messenger.android.library)
    alias(libs.plugins.messenger.android.library.jacoco)
    alias(libs.plugins.messenger.android.hilt)
}

android {
    namespace = "com.tynkovski.apps.messenger.sync"
}

dependencies {
    ksp(libs.hilt.ext.compiler)

    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    implementation(projects.core.data)

    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(projects.core.testing)
}
