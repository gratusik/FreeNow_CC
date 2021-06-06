import build.AndroidSDK
import build.App

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_EXTENSIONS)
    id(BuildPlugins.KOTLIN_KAPT)
}

android {
    compileSdkVersion(AndroidSDK.COMPILE_SDK_VERSION)
    buildToolsVersion = AndroidSDK.BUILD_VERSION_TOOLS

    defaultConfig {
        applicationId = App.APPLICATION_ID
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(build.Modules.Common.CORE))
    implementation(project(build.Modules.Common.UI))
    implementation(project(build.Modules.Features.CREDENTIALS))
    implementation(project(build.Modules.Features.HOME))
    implementation(dependency.Libs.KOTLIN)
    implementation(dependency.Libs.CORE_KTX)
    implementation(dependency.Libs.APPCOMPAT)
    implementation(dependency.Libs.CONSTRAINT_LAYOUT)
    implementation(dependency.Libs.MATERIAL)
    implementation(dependency.Libs.RETROFIT)
    implementation(dependency.Libs.GSON_CONVERTER)
    implementation(dependency.Libs.LOGGING_INTERCEPTOR)
    implementation(dependency.Libs.OK_HTTP)
    implementation(dependency.Libs.DAGGER)
    implementation(dependency.Libs.DAGGER_ANDROID)
    kapt(dependency.AnnotationProcessorsDependencies.DAGGER_COMPILER)
    kapt(dependency.AnnotationProcessorsDependencies.DAGGER_ANDROID_COMPILER)
}
