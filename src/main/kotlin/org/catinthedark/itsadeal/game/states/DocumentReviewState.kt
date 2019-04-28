package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class Button(
    val xMin: Int,
    val yMin: Int,
    val xMax: Int,
    val yMax: Int
) {
    fun isHover(x: Int, y: Int): Boolean {
        println("x=$x, y=$y, [$xMin,$yMin ... $xMax,$yMax]")
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax
    }
}

class DocumentReviewState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val backButton = Button(10, 110, 55, 136)
    private val acceptButton = Button(200, 5, 255, 20)
    private val declineButton = Button(10, 5, 55, 20)


    override fun onActivate() {
//        personTextures = IOC.at("person") ?: throw Exception("There is no person!!")
    }

    override fun onUpdate() {
        val inputs: InputAdapterHolder = IOC.at("inputs") ?: return
        if (backButton.isHover(inputs.mouseX, inputs.mouseY)) {
            // show hover
        }
        if (acceptButton.isHover(inputs.mouseX, inputs.mouseY)) {
            // show hover
        }
        if (declineButton.isHover(inputs.mouseX, inputs.mouseY)) {
            // show hover
        }
        if (inputs.isMouseClicked) {
            if (backButton.isHover(inputs.mouseX, inputs.mouseY)) {
                IOC.put("state", States.WITH_MAN)
            }
            if (acceptButton.isHover(inputs.mouseX, inputs.mouseY)) {
                IOC.put("state", States.PROFIT)
            }
            if (declineButton.isHover(inputs.mouseX, inputs.mouseY)) {
                IOC.put("state", States.SKIP)
            }
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