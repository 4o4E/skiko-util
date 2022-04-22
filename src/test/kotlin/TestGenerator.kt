package top.e404.skiko

import kotlinx.coroutines.runBlocking
import org.junit.Test
import top.e404.skiko.frame.encodeToBytes
import top.e404.skiko.generator.ImageGenerator
import top.e404.skiko.generator.list.CardGenerator
import top.e404.skiko.generator.list.GoodNewsGenerator
import top.e404.skiko.generator.list.PornhubGenerator
import top.e404.skiko.generator.list.ShakeTextGenerator
import java.io.File

class TestGenerator {
    private val inPng = File("run/in.png").readBytes()
    private val inGif = File("run/in.gif").readBytes()
    private val outPng = File("run/out/out.png")
    private val outGif = File("run/out/out.gif")
    private fun testGenerator(generator: ImageGenerator, args: MutableMap<String, String>) {
        runBlocking {
            val frames = generator.generate(args)
            val f = if (frames.size == 1) outPng else outGif
            f.writeBytes(generator.generate(args).encodeToBytes())
        }
    }

    @Test
    fun testShakeTextGenerator() {
        testGenerator(ShakeTextGenerator, mutableMapOf(
            "text" to "哼哼哼啊啊啊啊啊啊啊啊啊啊啊啊",
            "color" to "#0f7",
            "bg" to "#1F1B1D",
            "size" to "50",
            "count" to "12"
        ))
    }

    @Test
    fun testCardGenerator() {
        testGenerator(CardGenerator, mutableMapOf("b" to "awa", "s" to "QwQ"))
    }

    @Test
    fun testPornhubGenerator() {
        testGenerator(PornhubGenerator, mutableMapOf("s1" to "awa", "s2" to "QwQ"))
    }

    @Test
    fun testGoodNewsGenerator() {
        testGenerator(GoodNewsGenerator, mutableMapOf(
            "text" to "你的账号被风控了\n你的账号被风控了\n你的账号被风控了",
            "size" to "100"
        ))
    }
}