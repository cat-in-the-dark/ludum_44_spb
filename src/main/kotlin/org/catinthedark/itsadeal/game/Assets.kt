package org.catinthedark.itsadeal.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import javax.naming.Name

object Assets {
    private const val RUSSIAN_CHARACTERS = DEFAULT_CHARS +
        "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
        "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"


    fun load(): AssetManager {
        return AssetManager().apply {
            load(Names.FONT, BitmapFont::class.java)
            Names.textures.forEach { load(it, Texture::class.java) }
            Names.FACES.forEach { load(it, Texture::class.java) }
            Names.SHLAPY.forEach { load(it, Texture::class.java) }
            Names.GOLOVA.forEach { load(it, Texture::class.java) }
            Names.Sounds.all.forEach { load(it, Sound::class.java) }
            load(Names.Sounds.MUSIC, Music::class.java)

            val resolver = InternalFileHandleResolver()
            setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
            setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
            val params_big = FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                fontParameters.size = 32
                fontParameters.characters = RUSSIAN_CHARACTERS
                fontFileName = "fonts/cyrfont.ttf"
            }
            val params_small = FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                fontParameters.size = 24
                fontParameters.characters = RUSSIAN_CHARACTERS
                fontFileName = "fonts/cyrfont.ttf"
            }
            load(Names.FONT_BIG, BitmapFont::class.java, params_big)
            load(Names.FONT_SMALL, BitmapFont::class.java, params_small)
        }
    }

    object Names {
        val FONT = "fonts/tahoma-10.fnt"
        val FONT_BIG = "font_big.ttf"
        val FONT_SMALL = "font_small.ttf"
        val LOGO = "textures/logo.png"
        val TITLE = "textures/title.png"
        val STOL = "textures/STOL.png"
        val BODY01 = "textures/BODY01.png"
        val RUKI = "textures/RUKI.png"
        val DOCUMENT = "textures/document.png"
        val ROOM = "textures/KOMANTA.png"
        val MENU = "textures/MENU.png"

        val textures = listOf(
            LOGO,
            TITLE,
            STOL,
            BODY01,
            RUKI,
            DOCUMENT,
            ROOM,
            MENU
        )

        object Sounds {
            val NEXT = "sounds/Next_ru.mp3"
            val HOVER = "sounds/Chose.mp3"
            val REJECT_DOC = "sounds/Doc_reject.mp3"
            val ACCEPT_DOC = "sounds/Doc_sign.mp3"
            val JAIL = "sounds/arrest_lock.mp3"
            val PROFIT = "sounds/Score_add.mp3"
            val UNPROFIT = "sounds/Score_min.mp3"
            val ACHIEVE = "sounds/Score_achive.mp3"
            val MUSIC = "sounds/its_a_deal_music.mp3"

            val all = listOf(NEXT, HOVER, REJECT_DOC, ACCEPT_DOC, JAIL, PROFIT, UNPROFIT, ACHIEVE)
        }

        //
        val FACES = listOf(
            "textures/FACES/FACES1.png",
            "textures/FACES/FACES2.png",
            "textures/FACES/FACES3.png",
            "textures/FACES/FACES4.png",
            "textures/FACES/FACES5.png",
            "textures/FACES/FACES6.png"
        )

        //
        val SHLAPY = listOf(
            "textures/SHLYAPA/SHLYAPA1.png",
            "textures/SHLYAPA/SHLYAPA2.png",
            "textures/SHLYAPA/SHLYAPA3.png",
            "textures/SHLYAPA/SHLYAPA4.png",
            "textures/SHLYAPA/SHLYAPA5.png",
            "textures/SHLYAPA/SHLYAPA6.png"
        )

        //
        val GOLOVA = listOf(
            "textures/GOLOVA/GOLOVA1.png",
            "textures/GOLOVA/GOLOVA2.png",
            "textures/GOLOVA/GOLOVA3.png",
            "textures/GOLOVA/GOLOVA4.png",
            "textures/GOLOVA/GOLOVA5.png",
            "textures/GOLOVA/GOLOVA6.png"
        )
    }
}

data class PersonTextures(
    val golova: String,
    val shlapa: String,
    val faces: String,
    val body: String
)

fun RandomPersonTextures() = PersonTextures(
    golova = Assets.Names.GOLOVA.random(),
    shlapa = Assets.Names.SHLAPY.random(),
    faces = Assets.Names.FACES.random(),
    body = Assets.Names.BODY01
)

inline fun <reified T> AssetManager.at(name: String): T {
    return get(name, T::class.java)
}
