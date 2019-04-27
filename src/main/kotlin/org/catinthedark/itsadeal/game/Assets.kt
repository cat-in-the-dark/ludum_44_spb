package org.catinthedark.itsadeal.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import kotlin.random.Random

object Assets {
    fun load(): AssetManager {
        return AssetManager().apply {
            val textures = listOf(
                Names.LOGO,
                Names.TITLE,
                Names.STOL,
                Names.BODY01,
                Names.RUKI,
                Names.DOCUMENT
            )

            load(Names.FONT, BitmapFont::class.java)
            textures.forEach { load(it, Texture::class.java) }
            Names.FACES.forEach { load(it, Texture::class.java) }
            Names.SHLAPY.forEach { load(it, Texture::class.java) }
            Names.GOLOVA.forEach { load(it, Texture::class.java) }
        }
    }

    object Names {
        val FONT = "fonts/tahoma-10.fnt"
        val LOGO = "textures/logo.png"
        val TITLE = "textures/title.png"
        val STOL = "textures/STOL.png"
        val BODY01 = "textures/BODY01.png"
        val RUKI = "textures/RUKI.png"
        val DOCUMENT = "textures/document.png"

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
