package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.Assets.Names.FONT_BIG
import org.catinthedark.itsadeal.game.Const.Balance.GG
import org.catinthedark.itsadeal.game.Const.Projection.tohud
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class ProfitState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val inputs by lazy { IOC.atOrFail<InputAdapterHolder>("inputs") }
    private var credit = 0

    override fun onActivate() {
        val reward: Int by IOC
        val money: Int by IOC
        credit = Const.Balance.payCredit()
        IOC.put("money", money + reward)
        am.at<Sound>(Assets.Names.Sounds.PROFIT).play()
    }

    override fun onUpdate() {
        val txt: Texts by IOC
        val reward: Int by IOC
        val money: Int by IOC
        val (achCost, achName) = Const.Balance.nextAchievement(money, txt)

        hud.batch.managed {
            if (achName == GG) {
                am.font(FONT_BIG).draw(it, txt.win, 77f.tohud(), 128f.tohud())
                am.font(FONT_BIG).draw(it, txt.winDescription, 77f.tohud(), 118f.tohud())
                am.font(FONT_BIG).draw(it, txt.money(money), 77f.tohud(), 16f.tohud())
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || inputs.isMouseClicked) {
                    IOC.put("state", States.TITLE_SCREEN)
                }
            } else {
                am.font(FONT_BIG).draw(it, txt.profit.format(reward), 77f.tohud(), 128f.tohud())
                am.font(FONT_BIG).draw(it, txt.goal(achName, achCost), 77f.tohud(), 36f.tohud())
                am.font(FONT_BIG).draw(it, txt.payment(credit), 77f.tohud(), 26f.tohud())
                am.font(FONT_BIG).draw(it, txt.money(money), 77f.tohud(), 16f.tohud())
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || inputs.isMouseClicked) {
                    IOC.put("state", States.EMPTY_ROOM)
                }
            }
        }
    }

    override fun onExit() {

    }

}
