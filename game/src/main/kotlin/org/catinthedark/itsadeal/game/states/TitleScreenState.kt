package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.texts.RuTexts
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState

class TitleScreenState : IState {
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private var time = 0f
    private val SCREEN_TIME = 0.5f

    override fun onActivate() {
        time = 0f
        IOC.put("txt", RuTexts())
    }

    override fun onUpdate() {
        hud.batch.managed {
            it.draw(am.get(Assets.Names.TITLE, Texture::class.java), 0f, 0f)
        }
        time += Gdx.graphics.deltaTime
        if (time > SCREEN_TIME) {
            IOC.put("state", States.NEW_GAME)
        }
    }

    override fun onExit() {

    }
}
