package dependency

object Libs {
    object Versions {
        const val KOTLIN = "1.4.32"
        const val KOTLIN_VERSION = "1.5.10"
        const val CORE_KTX = "1.5.0"
        const val APPCOMPAT = "1.2.0"
        const val ACTIVITY = "1.2.2"
        const val MATERIAL = "1.4.0-beta01"
        const val PALETTE = "1.0.0"
        const val CONSTRAINT_LAYOUT = "2.0.4"
        const val FRAGMENT_KTX = "1.2.5"
        const val NAVIGATION = "2.3.5"
        const val NAVIGATION_ARCH = "1.0.0"
        const val LIFECYCLE = "2.3.1"
        const val LIFECYCLE_EXTENSION = "2.2.0"
        const val RETROFIT = "2.9.0"
        const val LOGGING_INTERCEPTOR = "5.0.0-alpha.2"
        const val COROUTINES = "1.4.3"
        const val ROOM = "2.3.0"
        const val GLIDE = "4.12.0"
        const val WORKER = "2.5.0"
        const val TOAST = "3.2.0"
        const val DAGGER = "2.35"
        const val SPEED_DIAL = "2.0.0"
        const val FRESCO_IMAGE_VIEWER = "0.5.0"
        const val FRESCO = "1.10.0"
        const val LOCAL_BROADCAST = "1.0.0"
    }

    // libs
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val ACTIVITY = "androidx.activity:activity-ktx:${Versions.ACTIVITY}"

    // material
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val PALETTE = "androidx.palette:palette-ktx:${Versions.PALETTE}"

    // constraint layout
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    // fragment - navigation host
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_DYNAMIC_FEATURE =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.NAVIGATION}"
    const val NAVIGATION_ARCH_FRAGMENT =
        "android.arch.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_ARCH}"
    const val NAVIGATION_ARCH_UI =
        "android.arch.navigation:navigation-ui-ktx:${Versions.NAVIGATION_ARCH}"

    // lifecycle
    const val LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_EXTENSIONS =
        "androidx.lifecycle:lifecycle-extensions:${Versions.LIFECYCLE_EXTENSION}"

    // dagger
    const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val DAGGER_ANDROID = "com.google.dagger:dagger-android-support:${Versions.DAGGER}"

    // retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR}"
    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${Versions.LOGGING_INTERCEPTOR}"

    // coroutines
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    // room
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

    // glide
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"

    // work manager
    const val WORKER = "androidx.work:work-runtime:${Versions.WORKER}"
    const val WORKER_EXT = "androidx.work:work-runtime-ktx:${Versions.WORKER}"

    // Toast
    const val TOAST = "com.pranavpandey.android:dynamic-toasts:${Versions.TOAST}"

    // speed dial FAB
    const val SPEED_DIAL = "com.github.kobakei:MaterialFabSpeedDial:${Versions.SPEED_DIAL}"

    // Fresco Image Viewer
    const val FRESCO_IMAGE_VIEWER =
        "com.github.stfalcon:frescoimageviewer:${Versions.FRESCO_IMAGE_VIEWER}"
    const val FRESCO = "com.facebook.fresco:fresco:${Versions.FRESCO}"

    // Local Broadcast Manager
    const val LOCAL_BROADCAST =
        "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.LOCAL_BROADCAST}"
}
