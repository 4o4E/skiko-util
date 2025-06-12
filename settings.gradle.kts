pluginManagement {
    plugins {
        id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "skiko-util"
include(
    ":skiko-util-ksp",
    ":skiko-util-core",
    ":skiko-util-gif-codec",
    ":skiko-util-draw",
    ":skiko-util-util",
    ":skiko-util-bdf-parser",
)
