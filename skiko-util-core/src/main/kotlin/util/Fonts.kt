package top.e404.skiko.util

import org.jetbrains.skia.*
import org.jetbrains.skia.paragraph.FontCollection
import org.jetbrains.skia.paragraph.TypefaceFontProvider
import java.awt.GraphicsEnvironment
import java.io.File

object Fonts {
    val fontMgr = FontMgr.default
    val fontProvider = TypefaceFontProvider()
    val fonts = FontCollection()
        .setDynamicFontManager(fontProvider)
        .setDefaultFontManager(fontMgr)

    val defaultFont: Typeface

    init {
        val name = GraphicsEnvironment.getLocalGraphicsEnvironment().availableFontFamilyNames.first()
        defaultFont = matchFamily(name).getTypeface(0)!!
    }

    fun loadTypefaceFromDir(dir: File) {
        for (file in dir.listFiles()!!) {
            if (file.isDirectory) loadTypefaceFromDir(file)
            else {
                val face = Typeface.makeFromData(Data.makeFromBytes(file.readBytes()))
                registerTypeface(face, file.name)
            }
        }
    }

    fun loadTypefaceFromFile(file: File, index: Int = 0) {
        val typeface = Typeface.makeFromData(Data.makeFromBytes(file.readBytes()), index)
        registerTypeface(typeface, file.name)
    }

    private fun registerTypeface(typeface: Typeface, vararg aliases: String) {
        fontProvider.registerTypeface(typeface)
        for (alias in aliases) fontProvider.registerTypeface(typeface, alias)
    }


    fun matchFamily(familyName: String): FontStyleSet {
        val fa = fontProvider.matchFamily(familyName)
        if (fa.count() != 0) return fa
        return fontMgr.matchFamily(familyName)
    }

    fun loadTypeface(path: String, index: Int = 0, vararg aliases: String): Typeface {
        return Typeface.makeFromFile(path, index).also { registerTypeface(it, *aliases) }
    }
}