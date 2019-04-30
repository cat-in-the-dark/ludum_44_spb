package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const.Projection.tohud
import org.catinthedark.itsadeal.game.InputAdapterHolder
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.game.font
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.getValue
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class BankrotState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    override fun onActivate() {
        am.at<Music>(Assets.Names.Sounds.MUSIC).stop()
    }

    override fun onUpdate() {
        val money: Int by IOC

        hud.batch.managed {
            am.font(Assets.Names.FONT_BIG).draw(it, "Вам конец!", 77f.tohud(), 128f.tohud())
            am.font(Assets.Names.FONT_BIG)
                .draw(it, "Вы не можете заплатить за крышу", 77f.tohud(), 26f.tohud())
            am.font(Assets.Names.FONT_BIG)
                .draw(it, "БАЛАНС = $money у.е.", 77f.tohud(), 16f.tohud())
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || IOC.atOrFail<InputAdapterHolder>("inputs").isMouseClicked) {
            IOC.put("state", States.TITLE_SCREEN)
        }
    }

    override fun onExit() {

    }
}

