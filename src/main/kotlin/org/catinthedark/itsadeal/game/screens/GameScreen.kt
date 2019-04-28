package org.catinthedark.itsadeal.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.GameStateMachine
import org.catinthedark.itsadeal.lib.YieldUnit

class GameScreen(private val stage: Stage, private val hud: Stage): YieldUnit<AssetManager, AssetManager> {
    private lateinit var am: AssetManager
    private lateinit var gameState: GameStateMachine

    override fun onActivate(data: AssetManager) {
        am = data
        gameState = GameStateMachine(stage, hud, am)
    }

    override fun run(delta: Float): AssetManager? {
        gameState.render()
        return null
    }

    override fun onExit() {
        gameState.onExit()
    }
}
