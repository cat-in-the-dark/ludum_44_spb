package org.catinthedark.itsadeal.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.states.WithManState
import org.catinthedark.itsadeal.lib.managed
import kotlin.math.roundToInt

enum class States {
    EMPTY_ROOM, WITH_MAN, DOCUMENT_REVIEW
}

class GameStateMachine(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) {
    private lateinit var personTextures: PersonTextures
    private val inputs = InputAdapterHolder(stage)

    private val renderWithMan = WithManState(stage, hud, am)

    init {
        Gdx.input.inputProcessor = inputs
        IOC.put("inputs", inputs)
        IOC.put("state", States.EMPTY_ROOM)
    }


    fun render() {
        when (IOC.at<States>("state")) {
            States.EMPTY_ROOM -> renderEmptyRoom()
            States.WITH_MAN -> renderWithMan.onUpdate()
            States.DOCUMENT_REVIEW -> renderDocumentReview()
        }
        inputs.update()
    }

    private fun renderDocumentReview() {
        if (inputs.isMouseClicked) {
            IOC.put("state", States.EMPTY_ROOM)
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
            IOC.put("state", States.WITH_MAN)
            personTextures = RandomPersonTextures()
        }

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
        }
    }

    private fun drawQuestions() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG).draw(it, "Все как договаривались?", 0f, Const.Projection.toHud(130f))
            am.at<BitmapFont>(Assets.Names.FONT_SMALL).draw(it, "Похоже у вас сегодня удачный день. Я обычно очень занят", 0f, Const.Projection.toHud(120f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG).draw(it, "Вы торопитесь?", 0f, Const.Projection.toHud(110f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG).draw(it, "Передать привет жене?", 0f, Const.Projection.toHud(90f))
        }
    }

    fun onExit() {
        Gdx.input.inputProcessor = null
    }
}
