package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.IOC
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class DocumentReviewState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val backButton = Button(10, 110, 55, 136, {
        IOC.put("state", States.WITH_MAN)
    })
    private val acceptButton = Button(200, 5, 255, 20, {
        IOC.put("state", States.PROFIT)
    })
    private val declineButton = Button(10, 5, 55, 20, {
        IOC.put("state", States.SKIP)
    })
    private val buttons = listOf(backButton, acceptButton, declineButton)


    override fun onActivate() {
//        personTextures = IOC.at("person") ?: throw Exception("There is no person!!")
    }

    override fun onUpdate() {
        buttons.forEach { it.update() }
        stage.batch.managed {
            //            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
//            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

//            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f) // TODO: make kivok

//            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)

            it.draw(am.at<Texture>(Assets.Names.DOCUMENT), 0f, 0f)
        }
    }

    override fun onExit() {

    }

}
