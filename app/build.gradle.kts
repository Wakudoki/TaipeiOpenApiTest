plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.cathaybkhomework"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cathaybkhomework"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.material)
    implementation (libs.androidx.lifecycle.viewmodel.compose)


    // Fragment
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.fragment.compose)
    debugImplementation(libs.androidx.fragment.testing)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.material)
    implementation(libs.navigation.fragment.ktx)

    // koin
    implementation(libs.io.koin.core)
    implementation(libs.io.koin.android)
    implementation(libs.io.koin.androidx.compose)

    // OkHttp、network
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //data store
    implementation(libs.androidx.datastore.preferences)

    // debug
    debugImplementation(libs.okhttp3.logging.interceptor)

    //Glide
    implementation(libs.compose)

    //Webview
    implementation(libs.androidx.webkit)

    // testing retrofit responses
    testImplementation (libs.mockwebserver)
    testImplementation (libs.mockito.kotlin)

    // Koin testing tools
    testImplementation( libs.koin.test)
    // Needed JUnit version
    testImplementation( libs.koin.test.junit4)
}