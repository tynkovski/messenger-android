import com.tynkovski.apps.messenger.MessengerBuildType

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.messenger.android.application)
    alias(libs.plugins.messenger.android.application.compose)
    alias(libs.plugins.messenger.android.application.flavors)
    alias(libs.plugins.messenger.android.application.jacoco)
    alias(libs.plugins.messenger.android.hilt)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.roborazzi)
    // id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = "com.tynkovski.apps.messenger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tynkovski.apps.messenger"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = MessengerBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            applicationIdSuffix = MessengerBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    // feature-modules here
    implementation(projects.feature.auth)
    implementation(projects.feature.contacts)
    implementation(projects.feature.chats)
    implementation(projects.feature.settings)
    implementation(projects.feature.chat)
    implementation(projects.feature.user)

    // core modules here
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.sync.work)

    // libs here
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.window.core)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.coil.kt)

    ksp(libs.hilt.compiler)

    // tests here
    debugImplementation(libs.androidx.compose.ui.testManifest)

    kspTest(libs.hilt.compiler)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.work.testing)

    testDemoImplementation(libs.robolectric)
    testDemoImplementation(libs.roborazzi)

    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.hilt.android.testing)
}