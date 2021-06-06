plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    google()
    maven(url = "https://plugins.gradle.org/m2/")
}
kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
object PluginsVersions {
    const val GRADLE_ANDROID = "4.1.1"
    const val KOTLIN = "1.4.30"
    const val NAVIGATION = "2.3.5"
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION}")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle.kts.kts files
}
