package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class DocumentReviewState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onActivate() {
//        personTextures = IOC.at("person") ?: throw Exception("There is no person!!")
    }

    override fun onUpdate() {
        if (IOC.at<InputAdapterHolder>("inputs")?.isMouseClicked == true) {
            IOC.put("state", States.EMPTY_ROOM)
        }


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
