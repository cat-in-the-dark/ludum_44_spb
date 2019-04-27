package org.catinthedark.itsadeal.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.lib.YieldUnit

class GameScreen(private val stage: Stage): YieldUnit<AssetManager, AssetManager> {
    private lateinit var am: AssetManager

    override fun onActivate(data: AssetManager) {
        am = data
    }

    override fun run(delta: Float): AssetManager? {
        return null
    }

    override fun onExit() {

    }
}
