package org.catinthedark.itsadeal.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.lib.YieldUnit
import org.catinthedark.itsadeal.lib.managed

class SplashScreen(
    private val stage: Stage
): YieldUnit<Unit, AssetManager> {
    lateinit var am: AssetManager

    override fun onActivate(data: Unit) {
        am = Assets.load()
    }

    override fun run(delta: Float): AssetManager? {
        if (am.update()) {
            return am
        } else {
            Gdx.app.log("SplashScreen", "Loading assets...${am.progress}")
        }
        if (am.isLoaded(Assets.Names.LOGO, Texture::class.java)) {
            stage.batch.managed {
                it.draw(am.get(Assets.Names.LOGO, Texture::class.java), 0f, 0f)
            }
        }
        stage.draw()

        return null
    }

    override fun onExit() {

    }
}
