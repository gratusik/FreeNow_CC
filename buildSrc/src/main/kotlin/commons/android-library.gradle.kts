package commons

import build.AndroidSDK
import build.App
import dependency.AnnotationProcessorsDependencies
import dependency.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}
android {
    compileSdkVersion(AndroidSDK.COMPILE_SDK_VERSION)
    buildToolsVersion = AndroidSDK.BUILD_VERSION_TOOLS

    defaultConfig {
        minSdkVersion(AndroidSDK.MIN_SDK_VERSION)
        targetSdkVersion(AndroidSDK.TARGET_SDK_VERSION)
        versionCode = App.VERSION_CODE
        versionName = App.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Libs.KOTLIN)
    implementation(Libs.DAGGER)
    implementation(Libs.DAGGER_ANDROID)
    implementation(dependency.Libs.RETROFIT)
    implementation(dependency.Libs.GSON_CONVERTER)
    implementation(dependency.Libs.LOGGING_INTERCEPTOR)
    implementation(dependency.Libs.OK_HTTP)
    implementation(Libs.FRESCO_IMAGE_VIEWER) {
        implementation(Libs.FRESCO)
    }
    kapt(AnnotationProcessorsDependencies.DAGGER_COMPILER)
    kapt(AnnotationProcessorsDependencies.DAGGER_ANDROID_COMPILER)
}
