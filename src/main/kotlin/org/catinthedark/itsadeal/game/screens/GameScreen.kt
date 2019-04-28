package org.catinthedark.itsadeal.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.YieldUnit

class GameScreen(private val stage: Stage, private val hud: Stage) : YieldUnit<AssetManager, AssetManager> {
    private lateinit var am: AssetManager
    private lateinit var gameState: GameStateMachine

    override fun onActivate(data: AssetManager) {
        am = data
        gameState = GameStateMachine(stage, hud, am)

        val m = am.at<Music>(Assets.Names.Sounds.MUSIC)
        m.isLooping = true
        m.play()
    }

    override fun run(delta: Float): AssetManager? {
        gameState.render()
        if (IOC.atOr("isGameOver", false)) {
            return am
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            return am
        }
        return null
    }

    override fun onExit() {
        gameState.onExit()
        am.at<Music>(Assets.Names.Sounds.MUSIC).stop()
    }
}
