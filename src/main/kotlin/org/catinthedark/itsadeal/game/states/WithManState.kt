package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.exceptions.InvalidAnswerException
import org.catinthedark.itsadeal.game.questionary.Person
import org.catinthedark.itsadeal.game.questionary.insertPeriodically
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.Deffer
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class WithManState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val log = LoggerFactory.getLogger(WithManState::class.java)

    private var pin = 0

    private var blink = false
    private var localDeffer: Deffer? = null

    private val personBtn = Button(96, 54, 144, 128, onClick = {
        // Ok button
        IOC.put("state", States.WITH_MAN_QUESTION)
    })

    private val docBtn = Button(0, 0, 256, 44, onClick = {
        // Ok button
        IOC.put("state", States.DOCUMENT_REVIEW)
    })

    override fun onActivate() {
        pin = 0
        localDeffer = Deffer()
        doBlink()
    }

    private fun doBlink() {
        localDeffer?.register(listOf(0.3f, 1f, 1f, 1.2f, 1.8f, 2f, 3f).random()) {
            blink = true
            localDeffer?.register(0.15f) {
                blink = false
                doBlink()
            }
        }
    }

    override fun onUpdate() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (pin == 111) {
                generatePerson()
                IOC.put("money", IOC.atOr("money", 0) + Const.Balance.generateReward(IOC.atOr("money", 0)))
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            pin += 100
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            pin += 10
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            pin += 1
        }

        val personTextures = IOC.atOrFail<PersonTextures>("personTextures")

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.ROOM), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            if (blink) {
                it.draw(am.at<Texture>(personTextures.faces), 0f, 0f)
            } else {
                it.draw(am.at<Texture>(personTextures.faces), 0f, 1f)
            }

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.DOCUMENT_LEJIT), 0f, 0f)
        }

        moneyHud(stage, hud, am)

        personBtn.update()
        docBtn.update()
        localDeffer?.update(Gdx.graphics.deltaTime)
    }

    override fun onExit() {
        localDeffer = null
    }

}
