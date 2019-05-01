package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.Assets.Names.FONT_BIG
import org.catinthedark.itsadeal.game.Assets.Names.Sounds.UNPROFIT
import org.catinthedark.itsadeal.game.Const.Projection.tohud
import org.catinthedark.itsadeal.lib.*
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class SkipState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val inputs by lazy { IOC.atOrFail<InputAdapterHolder>("inputs") }
    private var credit = 0

    override fun onActivate() {
        credit = Const.Balance.payCredit()
        am.sound(UNPROFIT).play()
        if (isBankrot()) {
            IOC.at<Deffer>("deffer")?.register(3f) {
                IOC.put("state", States.BANKROT)
            }
        }
    }

    override fun onUpdate() {
        val money: Int by IOC
        val txt: Texts by IOC
        val (achCost, achName) = Const.Balance.nextAchievement(money)

        hud.batch.managed {
            am.font(FONT_BIG).draw(it, txt.rejected, 77f.tohud(), 128f.tohud())
            am.font(FONT_BIG).draw(it, txt.goal(achName, achCost), 77f.tohud(), 36f.tohud())
            am.font(FONT_BIG).draw(it, txt.payment(credit), 77f.tohud(), 26f.tohud())
            am.font(FONT_BIG).draw(it, txt.money(money), 77f.tohud(), 16f.tohud())
        }

        if (!isBankrot()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || inputs.isMouseClicked) {
                IOC.put("state", States.EMPTY_ROOM)
            }
        }
    }

    override fun onExit() {

    }

    private fun isBankrot(): Boolean {
        val money: Int by IOC
        return money <= 0
    }

}
