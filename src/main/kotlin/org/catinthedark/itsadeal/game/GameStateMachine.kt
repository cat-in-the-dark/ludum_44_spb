package org.catinthedark.itsadeal.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.lib.managed

enum class States {
    EMPTY_ROOM, WITH_MAN, DOCUMENT_REVIEW
}

class GameStateMachine(
    private val stage: Stage,
    private val am: AssetManager
) {
    private lateinit var personTextures: PersonTextures
    private var state: States = States.EMPTY_ROOM
    private val inputs = InputAdapterHolder()

    fun render() {
        Gdx.input.inputProcessor = inputs
        when (state) {
            States.EMPTY_ROOM -> renderEmptyRoom()
            States.WITH_MAN -> renderWithMan()
            States.DOCUMENT_REVIEW -> renderDocumentReview()
        }
        inputs.update()
    }

    private fun renderDocumentReview() {
        if (inputs.isMouseClicked) {
            state = States.EMPTY_ROOM
        }


        stage.batch.managed {
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f) // TODO: make kivok

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)

            it.draw(am.at<Texture>(Assets.Names.DOCUMENT), 0f, 0f)
        }
    }

    private fun renderEmptyRoom() {
        if (inputs.isMouseClicked) {
            val x = Gdx.input.x.toFloat()
            val y = Gdx.input.y.toFloat()
            val vec = Vector3(x, y, 0f)
            stage.camera.unproject(vec)
//            println(vec)
            state = States.WITH_MAN
            personTextures = RandomPersonTextures()
        }

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
        }
    }

    private fun renderWithMan() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            personTextures = RandomPersonTextures()
        }
        if (inputs.isMouseClicked) {
            state = States.DOCUMENT_REVIEW
        }

        stage.batch.managed {
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f) // TODO: make kivok

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)
        }
    }
}

class InputAdapterHolder: InputAdapter() {
    var isMouseClicked = false

    fun update() {
        isMouseClicked = false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer == Input.Buttons.LEFT) {
//            val x = Const.Projection.calcX(screenX)
//            val y = Const.Projection.calcY(screenY)
            isMouseClicked = true
            println("screenX: $screenX screenY: $screenY pointer: $pointer button: $button")
        }

        return true
    }
}
