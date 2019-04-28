package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class SkipState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onActivate() {

    }

    override fun onUpdate() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "ПРОПУЩЕНО", 77f, Const.Projection.toHud(128f))
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            IOC.put("state", States.EMPTY_ROOM)
        }
    }

    override fun onExit() {

    }

}