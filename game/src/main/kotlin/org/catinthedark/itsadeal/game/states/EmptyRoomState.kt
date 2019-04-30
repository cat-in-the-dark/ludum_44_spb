package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Assets.Names.Sounds.NEXT
import org.catinthedark.itsadeal.game.InputAdapterHolder
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.game.sound
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
    private val inputs: InputAdapterHolder by lazy { IOC.atOrFail<InputAdapterHolder>("inputs") }
    private val deffer: Deffer by lazy { IOC.atOrFail<Deffer>("deffer") }
    private var clicked = false


    override fun onActivate() {
        clicked = false
    }

    override fun onUpdate() {
        if ((inputs.isMouseClicked || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && !clicked) {
            clicked = true
            am.sound(NEXT).play()
            deffer.register(0.75f) {
                IOC.put("state", States.WITH_MAN)
            }
        }

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.ROOM), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
        }

        moneyHud(stage, hud, am)
    }

    override fun onExit() {
        generatePerson()
    }

}
