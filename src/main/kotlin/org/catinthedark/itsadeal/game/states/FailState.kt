package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const
import org.catinthedark.itsadeal.game.IOC
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class FailState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
): IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onActivate() {

    }

    override fun onUpdate() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "ПОТРАЧЕНО", 77f, Const.Projection.toHud(128f))
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            IOC.put("state", States.EMPTY_ROOM)
            IOC.put("isGameOver", true)
        }
    }

    override fun onExit() {

    }

}
