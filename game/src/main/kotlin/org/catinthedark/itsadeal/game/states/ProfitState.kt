package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const
import org.catinthedark.itsadeal.game.InputAdapterHolder
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class ProfitState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    override fun onActivate() {
        val reward = IOC.atOr("reward", 0)
        val money = IOC.atOr("money", 0)
        IOC.put("money", money + reward)
        am.at<Sound>(Assets.Names.Sounds.PROFIT).play()
    }

    override fun onUpdate() {
        val reward = IOC.atOr("reward", 0)

        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "ПРОФИТ +$reward", 77f, Const.Projection.toHud(128f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "БАЛАНС = ${IOC.atOr("money", 0)}", Const.Projection.toHud(77f), Const.Projection.toHud(16f))
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || IOC.atOrFail<InputAdapterHolder>("inputs").isMouseClicked) {
            IOC.put("state", States.EMPTY_ROOM)
        }
    }

    override fun onExit() {

    }

}
