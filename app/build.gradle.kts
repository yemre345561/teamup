plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") // No version specified here!
}



android {
    namespace = "com.acm431.teamup"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.acm431.teamup"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf("-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    // Core Libraries
    implementation("androidx.compose.material:material-icons-extended:1.0.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")

    // **Firebase BOM - Fixed Version**
    implementation(platform("com.google.firebase:firebase-bom:33.7.0")) // Use stable BOM version
    implementation("com.google.firebase:firebase-analytics")             // Firebase Analytics
    implementation("com.google.firebase:firebase-database-ktx")          // Firebase Database
    implementation("com.google.firebase:firebase-auth-ktx")              // Firebase Auth

    // Removed this line because it duplicates BOM version declaration and has a typo
    // implementation ("platform('com.google.firebase:firebase-bom:32.1.1")

    // Jetpack Compose Libraries
    implementation(platform("androidx.compose:compose-bom:2023.10.01")) // BOM for Compose version alignment
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation") // Foundation library for KeyboardOptions
    implementation("androidx.compose.material3:material3") // Material 3 Components
    implementation("androidx.compose.material:material") // Material 1 for fallback compatibility
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // Testing Dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}




