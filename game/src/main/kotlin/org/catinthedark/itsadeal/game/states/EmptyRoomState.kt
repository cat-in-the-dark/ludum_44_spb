package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.*
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class EmptyRoomState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val stage: Stage by lazy { IOC.atOrFail<Stage>("stage") }
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    override fun onActivate() {

    }

    override fun onUpdate() {
        if (IOC.atOrFail<InputAdapterHolder>("inputs").isMouseClicked) {
            am.at<Sound>(Assets.Names.Sounds.NEXT).play()
            IOC.atOrFail<Deffer>("deffer").register(0.75f) {
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
