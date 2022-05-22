import java.io.ByteArrayOutputStream
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.LinkMapping

buildscript {
  val rootAbsolutePath = projectDir.absolutePath
  apply(from = "$rootAbsolutePath/constants.gradle")

  repositories {
    mavenCentral()
  }

  dependencies {
    classpath("org.jetbrains.dokka:dokka-gradle-plugin:${project.extra["dokka"]}")
  }
}


apply(plugin = "org.jetbrains.dokka")

val rootAbsolutePath: String = projectDir.absolutePath

tasks {
  "dokka"(DokkaTask::class) {
    moduleName = project.extra["dokkaOutputDir"].toString()
    outputFormat = project.extra["dokkaOutputFormat"].toString()
    outputDirectory = rootAbsolutePath
    includeNonPublic = true
    skipEmptyPackages = true

    val validProjects = subprojects.filter { p -> !p.name.contains("sample") }

    sourceDirs = files(validProjects.flatMap { p -> arrayListOf(
      File(p.projectDir, "/src/main/java"),
      File(p.projectDir, "/src/main/kotlin")
    ) })

    validProjects.forEach { p ->
      val path = if (p.name.startsWith("android")) {
        File(p.projectDir, "/src/main/java")
      } else {
        File(p.projectDir, "/src/main/kotlin")
      }

      val relativePath = File(rootAbsolutePath)
        .toPath()
        .relativize(path.toPath())
        .toString()

      linkMapping(delegateClosureOf<LinkMapping> {
        dir = path.absolutePath
        url = "https://github.com/protoman92/KotlinRedux/tree/master/$relativePath"
        suffix = "#L"
      })
    }
  }
}

allprojects {
  apply(from = "$rootAbsolutePath/constants.gradle")
}

subprojects {
  configurations.all {
    // Make sure the latest JitPack releases are used.
    resolutionStrategy.cacheChangingModulesFor(24, "hours")
  }
}

val clean by tasks.registering(type = Delete::class) {
  description = "Delete all build folders in all projects"

  doLast {
    allprojects {
      delete(project.buildDir)
    }
  }
}

tasks {
  register(name = "rebuildLibJars") {
    description = "Rebuild all lib jars and aars"
    println("Rebuilding all lib jars and aars")

    doLast {
      arrayListOf(
        "clean",
        ":common:common-all:jar",
        ":android:android-all:bundleReleaseAar",
      ).forEach { command ->
        exec {
          workingDir = File(rootAbsolutePath)
          commandLine("./gradlew.bat", command)
          standardOutput = ByteArrayOutputStream()
        }
      }
    }
  }
}
