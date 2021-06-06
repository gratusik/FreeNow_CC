import dependency.Libs

plugins {
    id("commons.android-library")
    id("kotlin-kapt")
}

dependencies {
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)
    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.ROOM_KTX)
    implementation(Libs.LOCAL_BROADCAST)
    kapt(dependency.AnnotationProcessorsDependencies.ROOM_COMPILER)
}
