package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const
import org.catinthedark.itsadeal.game.PersonTextures
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.game.questionary.insertPeriodically
import org.catinthedark.itsadeal.game.questionary.replaceIssuer
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState

class WithManAnswerState : IState {
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val stage: Stage by lazy { IOC.atOrFail<Stage>("stage") }
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val okBtn = Button(101, 16, 138, 29, onClick = {
        // Ok button
        IOC.put("state", States.WITH_MAN_QUESTION)
    })

    override fun onActivate() {

    }

    override fun onUpdate() {
        val personTextures = IOC.atOrFail<PersonTextures>("personTextures")
        val txt: Texts by IOC

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.ROOM), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f) // TODO: make kivok

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)

            it.draw(am.at<Texture>(Assets.Names.MENU), 0f, 0f)

            it.draw(am.at<Texture>(Assets.Names.BUTTON_SMALL), 98f, 16f)
        }

        // TODO: draw inside menu
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_SMALL_BLACK)
                .draw(
                    it,
                    IOC.atOr("current_answer", "").replaceIssuer().insertPeriodically("\n", 40),
                    Const.Projection.toHud(60f),
                    Const.Projection.toHud(44f)
                )

            am.at<BitmapFont>(Assets.Names.FONT_SMALL_WHITE)
                .draw(it, txt.ok, Const.Projection.toHud(112f), Const.Projection.toHud(25f))
        }
        moneyHud(stage, hud, am)

        okBtn.update()
    }

    override fun onExit() {

    }
}
