package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets.Names.FONT_MEDIUM_BLACK
import org.catinthedark.itsadeal.game.Assets.Names.MENU
import org.catinthedark.itsadeal.game.Const.Balance.maxAskedQuestions
import org.catinthedark.itsadeal.game.Const.Projection.tohud
import org.catinthedark.itsadeal.game.font
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.game.texture
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed

private val assetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }

val emptyRoomTutor = {
    val showTutor: Boolean by IOC
    if (showTutor) {
        val stage: Stage by IOC
        val hud: Stage by IOC
        val txt: Texts by IOC
        stage.batch.managed {
            it.draw(assetManager.texture(MENU), 0f, 0f)
        }
        hud.batch.managed {
            assetManager.font(FONT_MEDIUM_BLACK).draw(
                it, txt.tutorPressDoorToNext,
                65f.tohud(), 40f.tohud()
            )
        }
    }
}

val withManTutor = {
    val showTutor: Boolean by IOC
    if (showTutor) {
        val stage: Stage by IOC
        val hud: Stage by IOC
        val txt: Texts by IOC
        val askedQuestions: Int by IOC

        if (askedQuestions < maxAskedQuestions) {
            stage.batch.managed {
                it.draw(assetManager.texture(MENU), 0f, 0f)
            }
            hud.batch.managed {
                assetManager.font(FONT_MEDIUM_BLACK).draw(
                    it, txt.tutorClickPerson,
                    65f.tohud(), 40f.tohud()
                )
            }
        }
    }
}
