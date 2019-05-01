package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets.Names.FONT_MEDIUM_WHITE
import org.catinthedark.itsadeal.game.Const.Projection.tohud
import org.catinthedark.itsadeal.game.InputAdapterHolder
import org.catinthedark.itsadeal.game.font
import org.catinthedark.itsadeal.game.questionary.insertPeriodically
import org.catinthedark.itsadeal.game.states.States.NEW_GAME
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState

class StoryState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val inputs by lazy { IOC.atOrFail<InputAdapterHolder>("inputs") }

    override fun onActivate() {

    }

    override fun onUpdate() {
        val txt: Texts by IOC
        hud.batch.managed {
            am.font(FONT_MEDIUM_WHITE).draw(it, txt.story, 22f.tohud(), 120f.tohud())
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || inputs.isMouseClicked) {
            IOC.put("state", NEW_GAME)
        }
    }

    override fun onExit() {

    }
}
