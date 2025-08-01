plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-android")
}

android {
    namespace = "com.pedro.solutions.dialysisnotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pedro.solutions.dialysisnotes"
        minSdk = 26
        targetSdk = 33
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material:1.6.8")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.runtime:runtime-livedata")

    implementation("io.insert-koin:koin-android:4.0.3")
    implementation("io.insert-koin:koin-androidx-compose:4.0.3")


    implementation("androidx.fragment:fragment-ktx:1.8.0")
    implementation("androidx.activity:activity-ktx:1.9.0")

    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:2.6.1")

    implementation("androidx.room:room-ktx:2.6.1")

    // Work manager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Lifecycle
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    // Lifecycle utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.2")
    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    // Compose navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
}