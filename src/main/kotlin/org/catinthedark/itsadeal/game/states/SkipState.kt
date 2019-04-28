package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.Deffer
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class SkipState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onActivate() {
        IOC.put("money", IOC.atOr("money", 0) - Const.Balance.SKIP_COST)
        am.at<Sound>(Assets.Names.Sounds.UNPROFIT).play()
        if (isBankrot()) {
            IOC.at<Deffer>("deffer")?.register(3f) {
                IOC.put("state", States.BANKROT)
            }
        }
    }

    override fun onUpdate() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "ПРОПУЩЕНО", Const.Projection.toHud(77f), Const.Projection.toHud(128f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "БАЛАНС = ${IOC.atOr("money", 0)}", Const.Projection.toHud(77f), Const.Projection.toHud(16f))
        }

        if (!isBankrot()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                IOC.put("state", States.EMPTY_ROOM)
            }
        }
    }

    override fun onExit() {

    }

    private fun isBankrot(): Boolean {
        return IOC.atOr("money", 0) <= 0
    }

}
