plugins {
    kotlin("jvm")
}

dependencies {
    // util
    api(project(":util"))
    api(project(":gif-codec"))
    // skiko
    api(skiko("windows-x64"))
    api(skiko("linux-x64"))
}
