package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.game.questionary.DocContent
import org.catinthedark.itsadeal.game.questionary.Person
import org.catinthedark.itsadeal.game.questionary.insertPeriodically
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.*
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class DocumentReviewState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val stage: Stage by lazy { IOC.atOrFail<Stage>("stage") }
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

    private val backButton = Button(10, 110, 55, 136, {
        IOC.put("state", States.WITH_MAN)
        am.at<Sound>(Assets.Names.Sounds.HOVER).play()
    })
    private val acceptButton = Button(200, 5, 255, 20, {
        am.at<Sound>(Assets.Names.Sounds.ACCEPT_DOC).play()
        val p = IOC.atOrFail<Person>("person")

        IOC.atOrFail<Deffer>("deffer").register(0.5f) {
            if (p.isEvil) {
                IOC.put("state", States.FAIL)
            } else {
                IOC.put("state", States.PROFIT)
            }
        }
    })
    private val rejectButton = Button(10, 5, 70, 20, {
        am.at<Sound>(Assets.Names.Sounds.REJECT_DOC).play()
        IOC.atOrFail<Deffer>("deffer").register(0.5f) {
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
            it.draw(am.at<Texture>(Assets.Names.DOCUMENT), 0f, 0f)
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
