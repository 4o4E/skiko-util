object Versions {
    const val group = "top.e404.skiko-util"
    const val version = "1.1.1-SNAPSHOT"
    const val kotlin = "2.1.21"
    const val skiko = "0.9.2"
}

fun kotlinx(id: String, version: String = Versions.kotlin) = "org.jetbrains.kotlinx:kotlinx-$id:$version"
fun skiko(module: String, version: String = Versions.skiko) = "org.jetbrains.skiko:skiko-awt-runtime-$module:$version"
