package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.Deffer
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class FailState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
): IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private var canSkip = false

    override fun onActivate() {
        am.at<Music>(Assets.Names.Sounds.MUSIC).stop()
        am.at<Sound>(Assets.Names.Sounds.JAIL).play()

        IOC.atOrFail<Deffer>("deffer").register(1.5f) { canSkip = true }
    }

    override fun onUpdate() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "ПОТРАЧЕНО", 77f, Const.Projection.toHud(128f))
        }

        if (canSkip && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || IOC.atOrFail<InputAdapterHolder>("inputs").isMouseClicked)) {
            IOC.put("state", States.EMPTY_ROOM)
            IOC.put("isGameOver", true)
        }
    }

    override fun onExit() {

    }

}
