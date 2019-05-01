package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.Assets.Names.FONT_MEDIUM_BLACK
import org.catinthedark.itsadeal.game.Assets.Names.FONT_SMALL_BLACK
import org.catinthedark.itsadeal.game.Const.Projection.tohud
import org.catinthedark.itsadeal.game.testing.Autopilot
import org.catinthedark.itsadeal.game.texts.RuTexts
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.at
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class TitleScreenState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val stage: Stage by lazy { IOC.atOrFail<Stage>("stage") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val w = 55f
    private val persons: MutableList<PersonElement> = mutableListOf(
        PersonElement(RandomPersonTextures(), -2 * w),
        PersonElement(RandomPersonTextures(), -w),
        PersonElement(RandomPersonTextures(), 0f),
        PersonElement(RandomPersonTextures(), w),
        PersonElement(RandomPersonTextures(), 2 * w)
    )
    private val speed = 25f

    override fun onActivate() {
        am.music(Assets.Names.Sounds.MUSIC).stop()
        IOC.put("txt", RuTexts())
        IOC.put("showTutor", true)
        val autopilot = IOC.at<Autopilot>("autopilot")
        if (autopilot != null) {
            autopilot.reset()
        }
    }

    override fun onUpdate() {
        val txt: Texts by IOC
        updateCarusel()

        stage.batch.managed { b ->
            b.draw(am.texture(Assets.Names.ROOM), 0f, 0f)
            persons.forEach { p -> b.draw(am.texture(p.texture.body), p.x, 0f) }
            b.draw(am.texture(Assets.Names.STOL), 0f, 0f)
            b.draw(am.texture(Assets.Names.RUKI), 0f, 0f)
            persons.forEach { p ->
                b.draw(am.texture(p.texture.golova), p.x, 0f)
                b.draw(am.texture(p.texture.faces), p.x, 0f)
                b.draw(am.texture(p.texture.shlapa), p.x, 0f)
            }
            b.draw(am.texture(Assets.Names.MENU), 0f, 0f)
        }

        hud.batch.managed {
            am.font(FONT_MEDIUM_BLACK).draw(it, txt.gameName, 100f.tohud(), 40f.tohud())
            am.font(FONT_SMALL_BLACK).draw(it, txt.pressToStart, 79f.tohud(), 20f.tohud())
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            IOC.put("state", States.STORY)
        }
    }

    private fun updateCarusel() {
        persons.forEach { it.x -= speed * Gdx.graphics.deltaTime }
        if (persons.last().x <= 3 * w) {
            logger.debug("New person")
            persons.add(PersonElement(RandomPersonTextures(), persons.last().x + w))
        }
        if (persons.first().x <= -3 * w) {
            logger.debug("Delete person")
            persons.removeAt(0)
        }
    }

    override fun onExit() {

    }

    private data class PersonElement(
        val texture: PersonTextures,
        var x: Float
    )
}
