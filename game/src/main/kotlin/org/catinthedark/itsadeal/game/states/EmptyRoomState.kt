package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Assets.Names.Sounds.NEXT
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.game.sound
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.Deffer
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class EmptyRoomState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val stage: Stage by lazy { IOC.atOrFail<Stage>("stage") }
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private var clicked = false
    private val doorBtn = Button(45, 48, 105, 125, onClick = { callNext() })

    private fun callNext() {
        if (clicked) return
        val deffer: Deffer by IOC
        am.sound(NEXT).play()
        deffer.register(0.75f) {
            IOC.put("state", States.WITH_MAN)
        }
    }

    override fun onActivate() {
        clicked = false
    }

    override fun onUpdate() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            callNext()
        }

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.ROOM), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
        }

        moneyHud(stage, hud, am)
        doorBtn.update()
    }

    override fun onExit() {
        generatePerson()
    }

}
