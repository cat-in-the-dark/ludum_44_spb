package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class ProfitState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onActivate() {
        val money = 100 // TODO: get from person
        IOC.put("money", IOC.atOr("money", 0) + money)
    }

    override fun onUpdate() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "ПРОФИТ +100", 77f, Const.Projection.toHud(128f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "БАЛАНС = ${IOC.atOr("money", 0)}", Const.Projection.toHud(77f), Const.Projection.toHud(16f))
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            IOC.put("state", States.EMPTY_ROOM)
        }
    }

    override fun onExit() {

    }

}
