package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Assets.Names.BUTTON_SMALL
import org.catinthedark.itsadeal.game.Assets.Names.FONT_MEDIUM_WHITE
import org.catinthedark.itsadeal.game.Const
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.game.questionary.DocContent
import org.catinthedark.itsadeal.game.questionary.Person
import org.catinthedark.itsadeal.game.questionary.insertPeriodically
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.game.texture
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.Deffer
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory


class DocumentReviewState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val stage: Stage by lazy { IOC.atOrFail<Stage>("stage") }
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val buttonPatch: NinePatch by lazy { NinePatch(am.texture(BUTTON_SMALL), 4, 4, 4, 4) }
    private val txt: Texts by IOC

    private val backButton = Button(8, 121, 58, 136, {
        IOC.put("state", States.WITH_MAN)
        am.at<Sound>(Assets.Names.Sounds.HOVER).play()
    })
    private val acceptButton = Button(198, 8, 248, 23, {
        am.at<Sound>(Assets.Names.Sounds.ACCEPT_DOC).play()
        val deffer: Deffer by IOC
        val p = IOC.atOrFail<Person>("person")

        deffer.register(0.5f) {
            if (p.isEvil) {
                IOC.put("state", States.FAIL)
            } else {
                IOC.put("state", States.PROFIT)
            }
        }
    })
    private val rejectButton = Button(8, 8, 58, 23, {
        am.at<Sound>(Assets.Names.Sounds.REJECT_DOC).play()
        val deffer: Deffer by IOC
        deffer.register(0.5f) {
            IOC.put("state", States.SKIP)
        }
    })
    private val buttons = listOf(backButton, acceptButton, rejectButton)


    override fun onActivate() {
        am.at<Sound>(Assets.Names.Sounds.HOVER).play()
    }

    override fun onUpdate() {
        val docTexture: String by IOC
        buttons.forEach { it.update() }

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.ROOM), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(docTexture), 0f, 0f)
            buttonPatch.draw(it, 8f, 121f, 50f, 15f)
            buttonPatch.draw(it, 8f, 8f, 50f, 15f)
            buttonPatch.draw(it, 198f, 8f, 50f, 15f)
        }

        hud.batch.managed {
            am.at<BitmapFont>(FONT_MEDIUM_WHITE).draw(it, txt.back,
                Const.Projection.toHud(20f), Const.Projection.toHud(132f))
            am.at<BitmapFont>(FONT_MEDIUM_WHITE).draw(it, txt.reject,
                Const.Projection.toHud(15f), Const.Projection.toHud(19f))
            am.at<BitmapFont>(FONT_MEDIUM_WHITE).draw(it, txt.accept,
                Const.Projection.toHud(207f), Const.Projection.toHud(19f))
        }

        drawDocContents()
    }

    private fun drawDocContents() {
        val docContents = IOC.atOrFail<DocContent>("docContents")

        hud.batch.managed {
            val header = docContents.header.toUpperCase().insertPeriodically("\n", 10)
            val subheader = docContents.action.insertPeriodically("\n", 16)
            val text = docContents.subject.insertPeriodically("\n", 20)
            val layout = GlyphLayout(am.at<BitmapFont>(Assets.Names.FONT_BIG_SERIF), header)
            am.at<BitmapFont>(Assets.Names.FONT_BIG_SERIF)
                .draw(
                    it,
                    header,
                    Const.Projection.toHud(128f - (layout.width / 2) / Const.Projection.ratio),
                    Const.Projection.toHud(132f)
                )
            am.at<BitmapFont>(Assets.Names.FONT_MEDIUM_BLACK)
                .draw(it, subheader, Const.Projection.toHud(90f), Const.Projection.toHud(107f))
            am.at<BitmapFont>(Assets.Names.FONT_SMALL_BLACK)
                .draw(it, text, Const.Projection.toHud(90f), Const.Projection.toHud(90f))
        }
    }

    override fun onExit() {

    }

}
