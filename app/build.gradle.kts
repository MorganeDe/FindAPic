plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.findapic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.findapic"
        minSdk = 27
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.okhttpBom))
    implementation(libs.okhttp)
    implementation(libs.okhttpLoggingInterceptor)

    implementation(libs.retrofit)
    implementation(libs.kotlinSerializationJson)
    implementation(libs.retrofitKotlinSerializationConverter)

    implementation(platform(libs.koinBom))
    implementation(libs.koinCore)
    implementation(libs.koinAndroid)

    implementation(libs.coil.compose)
    implementation(libs.coil)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.viewModel)
    implementation(libs.koin.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    testImplementation(libs.assertTruth)
    testImplementation(libs.mockk)
    testImplementation(libs.mockkAndroid)
    testImplementation(libs.mockkAgent)
    testImplementation(libs.coroutinesTesting)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}