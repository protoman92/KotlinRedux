import java.net.URI

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    google()
    jcenter() /** This is just used for klaxon */
    mavenCentral()
    maven { url = URI("https://jitpack.io") }
  }
}

rootProject.name = "redux-jvm"
include(":common")
include(":common:common-core")
include(":common:common-thunk")
include(":common:common-saga")
include(":common:common-ui")
include(":common:common-all")
include(":android")
include(":android:android-util")
include(":android:android-ui")
include(":android:android-lifecycle")
include(":android:android-recyclerview")
include(":android:android-router")
include(":android:android-saga")
include(":android:android-livedata-saga")
include(":android:android-all")
include(":sample-android")
include(":sample-android:sample-no-android")
include(":sample-android:sample-simple")
include(":sample-android:sample-dagger")
include(":sample-android:sample-sunflower")
