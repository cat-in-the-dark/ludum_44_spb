package org.catinthedark.itsadeal.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.lib.YieldUnit
import org.catinthedark.itsadeal.lib.managed

class TitleScreen(private val stage: Stage): YieldUnit<AssetManager, AssetManager> {
    private lateinit var am: AssetManager

    private var time = 0f
    private val SCREEN_TIME = 0.5f

    override fun onActivate(data: AssetManager) {
        am = data
        time = 0f
    }

    override fun run(delta: Float): AssetManager? {
        stage.batch.managed {
            it.draw(am.get(Assets.Names.TITLE, Texture::class.java), 0f, 0f)
        }
        time += delta
        if (time > SCREEN_TIME) return am
        return null
    }

    override fun onExit() {

    }
}
