package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.Deffer
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class EmptyRoomState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

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
    }

    override fun onExit() {
        generatePerson()
    }

}
