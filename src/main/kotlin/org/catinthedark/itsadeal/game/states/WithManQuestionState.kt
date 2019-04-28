package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.managed

class WithManQuestionState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val buttons = listOf(
        Button(0,0,1,1) { // First
            IOC.put("state", States.WITH_MAN_ANSWER)
        },
        Button(0,0,1,1) { // Second
            IOC.put("state", States.WITH_MAN_ANSWER)
        },
        Button(0,0,1,1) { // Third
            IOC.put("state", States.WITH_MAN_ANSWER)
        }
    )

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

        buttons.forEach { it.update() }

        showQuestions()
    }

    private fun showQuestions() {

    }

    override fun onExit() {

    }

}
