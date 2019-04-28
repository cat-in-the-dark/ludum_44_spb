package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.managed

class WithManAnswerState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val okBtn = Button(0, 0, 1, 1) {
        // Ok button
        IOC.put("state", States.WITH_MAN_QUESTION)
    }

    private val anyBtn = Button(0, 0, 144, 256) {
        if (!okBtn.isClicked) {
            IOC.put("state", States.WITH_MAN)
        }
    }


    override fun onActivate() {

    }

    override fun onUpdate() {
        val personTextures = IOC.atOrFail<PersonTextures>("personTextures")

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.ROOM), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f) // TODO: make kivok

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)

            it.draw(am.at<Texture>(Assets.Names.MENU), 0f, 0f)
        }

        okBtn.update()
        anyBtn.update()
    }

    override fun onExit() {

    }
}
