plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":annotation"))
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.21-2.0.1")
}
