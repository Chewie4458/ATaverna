plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ataverna"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ataverna"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.androidx.credentials)
    implementation (libs.androidx.credentials.play.services.auth)
    implementation (libs.googleid)
    implementation (libs.play.services.auth)
    implementation (libs.glide)
}