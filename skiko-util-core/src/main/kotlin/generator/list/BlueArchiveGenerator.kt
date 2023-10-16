package top.e404.skiko.generator.list

import org.jetbrains.skia.*
import org.jetbrains.skia.paragraph.ParagraphBuilder
import org.jetbrains.skia.paragraph.ParagraphStyle
import org.jetbrains.skia.paragraph.TextStyle
import org.jetbrains.skia.svg.SVGDOM
import top.e404.skiko.frame.Frame
import top.e404.skiko.generator.ImageGenerator
import top.e404.skiko.util.*

object BlueArchiveGenerator : ImageGenerator {
    private val families = arrayOf("GlowSansSC-Normal-Heavy.otf", "RoGSanSrfStd-Bd.otf")
    private const val FONT_SIZE = 80F
    private const val ICON_HEIGHT = 80F
    private val bg by lazy {
        SVGDOM(Data.makeFromBytes(getJarFile(this.javaClass, "statistic/blue_archive/bg.svg")))
    }
    private val cross by lazy {
        SVGDOM(Data.makeFromBytes(getJarFile(this.javaClass, "statistic/blue_archive/cross.svg")))
    }
    private val ring by lazy {
        SVGDOM(Data.makeFromBytes(getJarFile(this.javaClass, "statistic/blue_archive/ring.svg")))
    }

    override suspend fun generate(args: MutableMap<String, String>): MutableList<Frame> {
        val l = args["l"] ?: ""
        val r = args["r"] ?: ""
        val x = args["x"]?.toFloatOrNull() ?: 0F
        val y = args["y"]?.toFloatOrNull() ?: 0F
        val lStyle = TextStyle()
            .setFontFamilies(families)
            .setColor(0xff128afa.toInt())
            .setFontStyle(FontStyle(FontWeight.BOLD, FontWidth.NORMAL, FontSlant.OBLIQUE))
            .setFontSize(FONT_SIZE)
            .setBackground(Paint().apply { color = Colors.WHITE.argb })
        val rStyle = TextStyle()
            .setFontFamilies(families)
            .setColor(0xff2b2b2b.toInt())
            .setFontStyle(FontStyle(FontWeight.BOLD, FontWidth.NORMAL, FontSlant.OBLIQUE))
            .setFontSize(FONT_SIZE)
            .setBackground(Paint().apply { color = Colors.WHITE.argb })
        val w = ParagraphBuilder(ParagraphStyle(), Fonts.fonts)
            .pushStyle(lStyle)
            .addText(l)
            .build()
            .layout(10000F)
            .maxIntrinsicWidth
        val paragraph = ParagraphBuilder(ParagraphStyle(), Fonts.fonts)
            .pushStyle(lStyle)
            .addText(l)
            .popStyle()
            .pushStyle(rStyle)
            .addText(r)
            .build()
            .layout(10000F)
        val image = Surface.makeRasterN32Premul(
            (paragraph.maxIntrinsicWidth + 2 * FONT_SIZE).toInt(),
            (paragraph.height + ICON_HEIGHT + 2 * FONT_SIZE).toInt()
        ).fill(Colors.WHITE.argb).withCanvas {
            save()
            translate(w - FONT_SIZE * 4.5F, FONT_SIZE * -4F)
            ring.render(this)
            restore()
            paragraph.paint(this, FONT_SIZE, FONT_SIZE + ICON_HEIGHT)
            translate(w - FONT_SIZE * 4.5F + x, FONT_SIZE * -4F + y)
            bg.render(this)
            cross.render(this)
        }
        return mutableListOf(Frame(0, image))
    }
}