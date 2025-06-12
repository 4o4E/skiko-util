import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import kotlin.apply

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("kapt") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
    `maven-publish`
    `java-library`
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = Versions.group
    version = Versions.version

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        // kotlin
        implementation(kotlinx("coroutines-core-jvm", "1.10.2"))
    }
}

val local = Properties().apply {
    val file = projectDir.resolve("local.properties")
    if (file.exists()) file.bufferedReader().use { load(it) }
}

val nexusUsername get() = local.getProperty("nexus.username") ?: ""
val nexusPassword get() = local.getProperty("nexus.password") ?: ""

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.gradle.java-library")

    kotlin {
        jvmToolchain(11)
    }

    java {
        withSourcesJar()
    }

    afterEvaluate {
        publishing.publications.create<MavenPublication>("java") {
            from(components["kotlin"])
            artifact(tasks.getByName("sourcesJar"))
            artifactId = project.name
            groupId = Versions.group
            version = Versions.version
        }
    }

    publishing {
        repositories {
            maven {
                name = "snapshot"
                url = uri("http://e404.top:8081/repository/maven-snapshots/")
                isAllowInsecureProtocol = true
                credentials {
                    username = nexusUsername
                    password = nexusPassword
                }
            }
        }
    }

    tasks {
        assemble {
            doLast {
                val jar = rootProject.projectDir.resolve("jar")
                jar.mkdir()
                println("==== copy ====")
                for (file in project.buildDir.resolve("libs").listFiles() ?: emptyArray()) {
                    if ("source" in file.name) continue
                    println("正在复制`${file.path}`")
                    file.copyTo(jar.resolve(file.name), true)
                }
            }
        }
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

tasks.assemble {
    subprojects.forEach {
        dependsOn(it.tasks.assemble)
    }
}