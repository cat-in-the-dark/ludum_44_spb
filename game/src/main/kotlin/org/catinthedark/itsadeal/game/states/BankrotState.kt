package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.states.IState
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class BankrotState(
    private val stage: Stage,
    private val hud: Stage
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    override fun onActivate() {
        am.at<Music>(Assets.Names.Sounds.MUSIC).stop()
    }

    override fun onUpdate() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "БАНКРОТ", 77f, Const.Projection.toHud(128f))
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || IOC.atOrFail<InputAdapterHolder>("inputs").isMouseClicked) {
            IOC.put("state", States.TITLE_SCREEN)
        }
    }

    override fun onExit() {

    }
}

