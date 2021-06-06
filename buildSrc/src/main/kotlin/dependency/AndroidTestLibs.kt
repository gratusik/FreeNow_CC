package dependency

object AndroidTestLibs {

    object Versions {
        const val JUNIT = "1.1.2"
        const val JUNIT_EXT = "1.1.2"
        const val ESPRESSO = "3.3.0"
        const val ESPRESSO_OKHTTP = "1.0.0"
        const val FRAGMENT_TESTING = "1.3.3"
        const val HILT = "2.2.2"
        const val CORE = "1.3.0"
        const val CORE_TESTING = "2.1.0"
        const val TEST_RUNNER = "1.3.0"
        const val TEST_RULES = "1.3.0"
        const val TEST_CORE = "1.3.0"
        const val MOCK_WEB_SERVER = "4.9.1"
        const val TRUTH = "1.1.2"
        const val MOCKITO = "3.9.0"
        const val COROUTINES = "1.4.3"
        const val ROBOELECTRIC = "4.5.1"
        const val RETROFIT = "2.6.0"
        const val RETROFIT_GSON_CONVERTER = "2.4.0"
        const val RETROFIT_COROUTINE_CONVERTER = "0.9.2"
        const val GSON = "2.8.5"
        const val NAVIGATION = "2.3.5"
    }

    object Exclude {
        const val MOCKITO = "org.mockito"
    }

    // Junit
    const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Versions.JUNIT_EXT}"

    // Espresso
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
    const val ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intent:${Versions.ESPRESSO}"
    const val ESPRESSO_ACCESSIBILITY =
        "androidx.test.espresso:espresso-accessibility:${Versions.ESPRESSO}"
    const val ESPRESSO_WEB = "androidx.test.espresso:espresso-web:${Versions.ESPRESSO}"
    const val ESPRESSO_OKHTTP_IDLING =
        "com.jakewharton.espresso:okhttp3-idling-resource:${Versions.ESPRESSO_OKHTTP}"

    // Fragment
    const val FRAGMENT_TESTING = "androidx.fragment:fragment-testing:${Versions.FRAGMENT_TESTING}"

    // Hilt
    const val HILT_TEST = "com.google.dagger:hilt-android-testing:${Versions.HILT}"
    const val HILT_TEST_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"

    // core
    const val CORE = "androidx.test:core-ktx:${Versions.CORE}"
    const val CORE_TESTING = "androidx.arch.core:core-testing:${Versions.CORE_TESTING}"

    // x test
    const val TEST_RUNNER = "androidx.test:runner:${Versions.TEST_RUNNER}"
    const val TEST_RULES = "androidx.test:rules:${Versions.TEST_RULES}"
    const val TEST_CORE = "androidx.test:core:${Versions.TEST_CORE}"

    // mock web server
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${Versions.MOCK_WEB_SERVER}"

    // truth
    const val TRUTH = "com.google.truth:truth:${Versions.TRUTH}"
    const val TRUTH_EXTENSION =
        "com.google.truth.extensions:truth-java8-extension:${Versions.TRUTH}"

    // mockito
    const val MOCKITO_CORE = "org.mockito:mockito-core:${Versions.MOCKITO}"
    const val MOCKITO_ANDROID = "org.mockito:mockito-android:${Versions.MOCKITO}"

    // coroutine
    const val COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"

    // roboelectric
    const val ROBOELECTRIC = "org.robolectric:robolectric:${Versions.ROBOELECTRIC}"

    // retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val RETROFIT_GSON_CONVERTER =
        "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_GSON_CONVERTER}"
    const val RETROFIT_COROUTINE_CONVERTER =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.RETROFIT_COROUTINE_CONVERTER}"

    // navigation
    const val NAVIGATION = "androidx.navigation:navigation-testing:${Versions.NAVIGATION}"
}
