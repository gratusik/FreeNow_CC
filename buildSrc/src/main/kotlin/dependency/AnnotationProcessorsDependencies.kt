/*
 * Copyright 2019 vmadalin.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dependency

/**
 * Project annotation processor dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */
object AnnotationProcessorsDependencies {
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Libs.Versions.DAGGER}"
    const val DAGGER_ANDROID_COMPILER =
        "com.google.dagger:dagger-android-processor:${Libs.Versions.DAGGER}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Libs.Versions.ROOM}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Libs.Versions.GLIDE}"
}
