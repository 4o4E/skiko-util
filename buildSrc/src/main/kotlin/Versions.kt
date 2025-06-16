object Versions {
    const val GROUP = "top.e404.skiko-util"
    const val VERSION = "1.1.1-SNAPSHOT"
    const val KOTLIN = "2.1.21"
    const val KOTLINX_SERIALIZATION = "1.8.1"
    const val SKIKO = "0.9.2"
    const val KTOR = "2.3.13"
}

fun kotlinx(id: String, version: String = Versions.KOTLIN) = "org.jetbrains.kotlinx:kotlinx-$id:$version"
fun skiko(module: String, version: String = Versions.SKIKO) = "org.jetbrains.skiko:skiko-awt-runtime-$module:$version"
fun ktor(module: String, version: String = Versions.KTOR) = "io.ktor:ktor-$module:$version"
