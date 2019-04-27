package org.catinthedark.itsadeal.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.catinthedark.itsadeal.game.ItsaDealGame

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true")
        LwjglApplication(ItsaDealGame(), LwjglApplicationConfiguration().apply {
            title = "It's a deal"
            width = 1024
            height = 576
        })
    }
}
