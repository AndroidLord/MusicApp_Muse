plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.musicappmuse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.musicappmuse"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // compose viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    // extended icons
    implementation ("androidx.compose.material:material-icons-extended:1.0.1")
    // media player
    implementation ("androidx.core:core:1.7.0")
    implementation ("androidx.media:media:1.5.0")
    implementation ("com.google.android.exoplayer:exoplayer:2.17.0")
    implementation ("com.google.android.exoplayer:extension-mediasession:2.17.0")

    val hilt_version = "2.37"
    val kotlin_coroutines_version = "1.5.0"

    // Hilt - dependency injection
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
//    implementation ('androidx.hilt:hilt-lifecycle-viewmodel:1.2.0')
//    kapt ('androidx.hilt:hilt-compiler:1.2.0')

    // coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")

    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}