import com.android.build.gradle.AppExtension
import com.android.builder.model.ApiVersion
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

buildscript {
  repositories {
    google()
    mavenCentral()
  }

  val rootAbsolutePath = projectDir.parent
  apply(from = "$rootAbsolutePath/android/constants.gradle")
  apply(from = "$rootAbsolutePath/sample-android/constants.gradle")

  dependencies {
    classpath("com.android.tools.build:gradle:${project.extra["gradle"]}")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin"]}")
  }
}

val rootAbsolutePath: String = projectDir.parent

subprojects {
  apply(plugin = "com.android.application")
  apply(plugin = "kotlin-android")
  apply(plugin = "kotlin-android-extensions")
  apply(from = "$rootAbsolutePath/android/constants.gradle")
  apply(from = "$rootAbsolutePath/sample-android/constants.gradle")
}

fun Project.dependOnLibJar(vararg dependencyNames: String) {
  data class DependencyDetails(
    val name: String,
    val outputDir: String,
    val outputGlob: String,
    val outputTask: String,
  )

  dependencyNames
    .map { dependencyName ->
      val dependency = project(dependencyName)

      if (dependency.hasProperty("android")) {
        DependencyDetails(
          name = dependencyName,
          outputDir = "${dependency.buildDir.absolutePath}/outputs/aar",
          outputGlob = "*-release.aar",
          outputTask = "bundleReleaseAar",
        )
      } else {
        DependencyDetails(
          name = dependencyName,
          outputDir = "${dependency.buildDir.absolutePath}/libs",
          outputGlob = "*.jar",
          outputTask = "jar",
        )
      }
    }.forEach { dependencyDetails ->
      afterEvaluate {
        tasks {
          "compileDebugKotlin" {
            doFirst {
              if (!File(dependencyDetails.outputDir).exists()) {
                throw Exception(
                  """
                  Must run task \"${dependencyDetails.name}:${dependencyDetails.outputTask}\"
                  first before proceeding with the build
                  """.trimIndent())
              }
            }
          }
        }
      }

      dependencies {
        val implementation by configurations

        implementation(fileTree(
          dependencyDetails.outputDir,
        ).include(dependencyDetails.outputGlob))
      }
    }
}

configure(arrayListOf(
  project(":sample-android:sample-no-android")
)) {
  dependOnLibJar(":common:common-all")
}

configure(subprojects - project(":sample-android:sample-no-android")) {
  configure<AppExtension> {
    defaultConfig {
      multiDexEnabled = true
    }
  }

  dependOnLibJar(
    ":common:common-all",
    ":android:android-all",
  )

  dependencies {
    val implementation by configurations
    implementation("androidx.multidex:multidex:${project.extra["multidex"]}")
    implementation("io.reactivex.rxjava2:rxjava:${project.extra["rxJava"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${project.extra["kotlinCoroutines"]}")
  }
}

data class ApiVersionImpl(private val apiLevel: Int) : ApiVersion {
  override fun getApiLevel(): Int {
    return this.apiLevel
  }

  override fun getCodename(): String? {
    return null
  }

  override fun getApiString(): String {
    return this.apiLevel.toString()
  }
}

configure(subprojects - project(":sample-android:sample-sunflower")) {
  configure<AppExtension> {
    compileSdkVersion = "android-${project.extra["compileSdk"]}"

    defaultConfig {
      applicationId = "org.swiften.redux.android.sample"
      minSdkVersion = ApiVersionImpl(apiLevel = project.extra["minSdk"] as Int)
      targetSdkVersion = ApiVersionImpl(apiLevel = project.extra["targetSdk"] as Int)
      versionCode(project.extra["versionCode"] as Int)
      versionName = project.extra["versionName"] as String

      testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
      getByName("release") {
        isMinifyEnabled = false
        proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      }
    }
  }

  dependencies {
    val implementation by configurations
    val testImplementation by configurations
    val debugImplementation by configurations
    implementation(fileTree("libs").include("*.jar"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${project.extra["kotlin"]}")
    implementation("androidx.constraintlayout:constraintlayout:${project.ext["constraintlayout"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${project.ext["kotlinCoroutines"]}")
    implementation("com.beust:klaxon:5.0.1")
    testImplementation("junit:junit:${project.ext["junit"]}")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:${project.ext["leakCanary"]}")
  }

  apply(from = "$rootAbsolutePath/sample-android/skipped.gradle")
}

configure(arrayListOf(
  project(":sample-android:sample-dagger")
)) {
  apply(plugin = "kotlin-kapt")

  dependencies {
    val implementation by configurations
    val kapt by configurations
    implementation("com.google.dagger:dagger:${project.extra["dagger"]}")
    kapt("com.google.dagger:dagger-compiler:${project.extra["dagger"]}")
  }
}

configure(arrayListOf(
  project(":sample-android:sample-sunflower"))
) {
  /*
   * Copyright 2018 Google LLC
   *
   * Licensed under the Apache License, Version 2.0 (the "License");
   * you may not use this file except in compliance with the License.
   * You may obtain a copy of the License at
   *
   *     https://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing, software
   * distributed under the License is distributed on an "AS IS" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   * See the License for the specific language governing permissions and
   * limitations under the License.
   */
  apply(from = "${project.projectDir}/constants.gradle")
  apply(plugin = "kotlin-kapt")

  configure<AppExtension> {
    compileSdkVersion = "android-${project.extra["compileSdk"]}"

    defaultConfig {
      applicationId = "com.google.samples.apps.sunflower"
      minSdkVersion = ApiVersionImpl(apiLevel = 18)
      targetSdkVersion = ApiVersionImpl(apiLevel = project.extra["targetSdk"] as Int)
      versionCode(project.extra["versionCode"] as Int)
      versionName = project.extra["versionName"] as String

      multiDexEnabled = true
      testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
      vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
      getByName("release") {
        isMinifyEnabled = false
        proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      }
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
    }
  }

  configure<AndroidExtensionsExtension> {
    isExperimental = true
  }

//  android {
//    compileSdkVersion = project.ext.compileSdk
//
//    defaultConfig {
//      applicationId = "com.google.samples.apps.sunflower"
//      minSdkVersion 18
//      targetSdkVersion project.ext.targetSdk
//
//      multiDexEnabled = true
//      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//      versionCode = 1
//      versionName = "0.1.6"
//      vectorDrawables.useSupportLibrary = true
//    }
//
//    buildTypes {
//      release {
//        minifyEnabled = false
//        proguardFiles getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
//      }
//    }
//
//    compileOptions {
//      sourceCompatibility JavaVersion.VERSION_1_8
//      targetCompatibility JavaVersion.VERSION_1_8
//    }
//  }

//  androidExtensions {
//    experimental = true
//  }

  dependencies {
    val implementation by configurations
    val testImplementation by configurations
    val debugImplementation by configurations
    val androidTestImplementation by configurations
    val kapt by configurations

    implementation("io.reactivex.rxjava2:rxandroid:${project.ext["rxAndroid"]}")
    implementation("android.arch.navigation:navigation-fragment-ktx:${project.extra["navigationVersion"]}")
    implementation("android.arch.navigation:navigation-ui-ktx:${project.extra["navigationVersion"]}")
    implementation("android.arch.work:work-runtime-ktx:${project.extra["workVersion"]}")
    implementation("androidx.constraintlayout:constraintlayout:${project.extra["constraintlayout"]}")
    implementation("androidx.core:core-ktx:${project.extra["ktxVersion"]}")
    implementation("androidx.room:room-runtime:${project.extra["roomVersion"]}")
    kapt("androidx.room:room-compiler:${project.extra["roomVersion"]}")
    implementation("com.squareup.picasso:picasso:${project.extra["picasso"]}")
    implementation("com.google.android.material:material:${project.extra["materialVersion"]}")
    implementation("com.google.code.gson:gson:${project.extra["gsonVersion"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.extra["kotlin"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${project.extra["kotlinCoroutines"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.extra["kotlinCoroutines"]}")

    // Testing dependencies
    androidTestImplementation("androidx.arch.core:core-testing:${project.extra["androidxArchCoreTest"]}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${project.extra["espresso"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${project.extra["espresso"]}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${project.extra["espresso"]}")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:${project.extra["uiAutomatorVersion"]}")
    testImplementation("junit:junit:${project.extra["junit"]}")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:${project.extra["leakCanary"]}")
  }

  apply(from = "$rootAbsolutePath/sample-android/skipped.gradle")
}
