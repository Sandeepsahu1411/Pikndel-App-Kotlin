import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
    id("kotlin-kapt")
//    id ("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

// Read Maps API key from local.properties (preferred) or environment
// Example in local.properties (without quotes is fine):
// MAPS_API_KEY=AIzaSy...
val mapsApiKey: String = run {
    val props = Properties()
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { props.load(it) }
    }
    val fromLocal = props.getProperty("MAPS_API_KEY")
    val fromEnv = System.getenv("MAPS_API_KEY")
    (fromLocal ?: fromEnv ?: "").removeSurrounding("\"").trim()
}

android {
    namespace = "com.example.pikndelappkotlin"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.pikndelappkotlin"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Expose the key to AndroidManifest via placeholders and to runtime via BuildConfig
        manifestPlaceholders += mapOf("GOOGLE_MAPS_API_KEY" to mapsApiKey)
        buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    // CameraX
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")
    // ML Kit Barcode Scanning
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
//Icons
    implementation("androidx.compose.material:material-icons-extended-android:1.7.5")

// navigation
    implementation("androidx.navigation:navigation-compose:2.8.3")

//serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

//Hilt
    implementation("com.google.dagger:hilt-android:2.57.1")
    kapt("com.google.dagger:hilt-android-compiler:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

//retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

// preferences data store
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

//coil (imageViewer)
    implementation("io.coil-kt:coil-compose:2.2.2")

//lottie Animation
    implementation("com.airbnb.android:lottie-compose:6.1.0")
//
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")
//    implementation("androidx.compose.foundation:foundation:1.5.0") // or latest


    // The compose calendar library for Android
    implementation("com.kizitonwose.calendar:compose:2.6.1")

    //colorPicker
    implementation("com.github.skydoves:colorpicker-compose:1.1.2")



    //permission
    implementation("com.google.accompanist:accompanist-permissions:0.37.3")

    // Google Maps (Compose) and Location
    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")
}