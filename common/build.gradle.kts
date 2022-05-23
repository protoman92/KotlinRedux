import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    mavenCentral()
    maven { url = java.net.URI("https://jitpack.io") }
    maven { url = java.net.URI(project.extra["ktlintGradleMavenRepository"].toString()) }
  }

  val rootAbsolutePath = projectDir.parent
  apply(from = "$rootAbsolutePath/constants.gradle")

  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin"]}")
    /**
     * It seems that we are not able to download this plugin from gradle plugin repository, so we
     * shall use jitpack.io instead.
     */
    classpath("com.github.johnrengelman:shadow:${project.extra["shadowJar"]}")
    classpath("org.jlleitschuh.gradle:ktlint-gradle:${project.extra["ktlintGradle"]}")
  }
}

subprojects {
  apply(plugin = "kotlin")
  apply(plugin = "maven-publish")
  apply(plugin = "org.jlleitschuh.gradle.ktlint")
  group = "com.github.protoman92.KotlinRedux"
  version = "1.0-SNAPSHOT"

  tasks {
    "compileKotlin"(KotlinCompile::class) {
      kotlinOptions {
        jvmTarget = project.extra["jvmTarget"] as String
      }
    }

    "compileTestKotlin"(KotlinCompile::class) {
      kotlinOptions {
        jvmTarget = project.extra["jvmTarget"] as String
      }
    }
  }

  /**
   * If we use doLast, the task does not seem to work - therefore, we register it normally here and
   * let the execution takes place every build cycle.
   */
  val packageTestJar by tasks.registering(Jar::class) {
    description = """
Package test jar so that other projects can depend on test code from this project
("${project.name}")
    """.trimIndent()

    println("Packing test jar for project \"${project.name}\"")
    archiveFileName.set("${project.name}-test.jar")
    from(project.the(extensionType = SourceSetContainer::class)["test"].output)
  }

  val testArtifacts by configurations.creating

  artifacts {
    add(testArtifacts.name, packageTestJar)
  }

  dependencies {
    val implementation by configurations
    val testImplementation by configurations
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    testImplementation("junit:junit:${project.extra["junit"]}")
  }

  /**
   * The group and version settings must be outside the publications block, otherwise the generated
   * POM files will not have the correct groupId and version.
   */
  afterEvaluate {
    configure<PublishingExtension> {
      publications {
        create(name = project.name, type = MavenPublication::class) {
          groupId = project.group.toString()
          version = project.version.toString()
          artifactId = project.name
          from(components.getByName("kotlin"))
        }
      }
    }
  }
}

project(":common:common-core") {
  dependencies {
    val testImplementation by configurations
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.extra["kotlinCoroutines"]}")
    testImplementation(project(path = ":common:common-thunk"))
    testImplementation(project(path = ":common:common-saga"))
    testImplementation("io.reactivex.rxjava2:rxjava:${project.extra["rxJava"]}")
  }
}

project(":common:common-thunk") {
  dependencies {
    val implementation by configurations
    val testImplementation by configurations
    val testArtifacts by configurations
    implementation(project(path = ":common:common-core"))
    testImplementation(project(path = ":common:common-core"))
    testImplementation(project(path = ":common:common-core", configuration = testArtifacts.name))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.extra["kotlinCoroutines"]}")
  }
}

project(":common:common-saga") {
  dependencies {
    val api by configurations
    val implementation by configurations
    val testImplementation by configurations
    val testArtifacts by configurations
    api(project(path = ":common:common-core"))
    implementation("io.reactivex.rxjava2:rxjava:${project.extra["rxJava"]}")
    implementation("io.reactivex.rxjava2:rxkotlin:${project.extra["rxKotlin"]}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${project.extra["mockito"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.extra["kotlinCoroutines"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${project.extra["kotlinCoroutines"]}")
    testImplementation(project(path = ":common:common-core"))
    testImplementation(project(path = ":common:common-core", configuration = testArtifacts.name))
  }
}

project(":common:common-ui") {
  dependencies {
    val api by configurations
    api(project(path = ":common:common-core"))
  }
}

project(":common:common-all") {
  apply(plugin = "com.github.johnrengelman.shadow")

  val allDependencyNames = arrayListOf(
    ":common:common-core",
    ":common:common-thunk",
    ":common:common-saga",
    ":common:common-ui"
  )

  val allDependencies = allDependencyNames
    .map { dependencyName -> project(dependencyName) }

  allDependencyNames.forEach { dependencyName ->
    project.evaluationDependsOn(dependencyName)
  }

  dependencies {
    val implementation by configurations

    allDependencies.forEach { dependency ->
      implementation(dependency)
    }
  }

  tasks {
    register("replaceNormalJarWithFatJar") {
      doLast {
        val jarDirectory = "${project.buildDir.absolutePath}/libs"
        val jarPrefix = "${project.name}-${project.version}"
        val finalJarName = "${jarPrefix}.jar"
        File("$jarDirectory/$finalJarName").delete()
        File("$jarDirectory/$jarPrefix-all.jar").renameTo(File("$jarDirectory/$finalJarName"))
      }
    }

    "shadowJar"(ShadowJar::class) {
      duplicatesStrategy = DuplicatesStrategy.EXCLUDE

      dependencies {
        /**
         * Exclude Kotlin stdlib:
         * https://thelyfsoshort.io/kotlin-reflection-shadow-jars-minimize-9bd74964c74
         */
        exclude(dependency("org.jetbrains.kotlin:.*"))
        exclude(dependency("org.jetbrains:annotations:.*"))
        exclude("org/intellij/lang/annotations/**/*")
      }

      dependsOn(project.tasks["clean"])
    }

    /** Include all dependencies' jar contents into the final jar archive */
    "jar"(Jar::class) {
      duplicatesStrategy = DuplicatesStrategy.EXCLUDE

      allDependencies.forEach { dependency ->
        val filesToInclude = dependency.configurations
          .getByName("archives").artifacts.files.files
          .map { file -> zipTree(file) }

        from(filesToInclude)
      }
    }
  }
}
