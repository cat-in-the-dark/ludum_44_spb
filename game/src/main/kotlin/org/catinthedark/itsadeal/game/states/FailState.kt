package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.Assets.Names.FONT_BIG
import org.catinthedark.itsadeal.game.Assets.Names.Sounds.JAIL
import org.catinthedark.itsadeal.game.Assets.Names.Sounds.MUSIC
import org.catinthedark.itsadeal.game.Const.Projection.tohud
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.*
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class FailState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private var canSkip = false
    private val deffer by lazy { IOC.atOrFail<Deffer>("deffer") }
    private val inputs by lazy { IOC.atOrFail<InputAdapterHolder>("inputs") }
    private val after = AfterBarrier(1f)
    private val bail = { Const.Balance.bail(IOC.atOr("reward", 0)) }

    override fun onActivate() {
        am.music(MUSIC).stop()
        am.sound(JAIL).play()
        deffer.register(2f) { canSkip = true }
        IOC.updateOrSkip<Int>("money") { it - bail() }
    }

    override fun onUpdate() {
        val txt: Texts by IOC
        val money: Int by IOC

        hud.batch.managed { b ->
            am.font(FONT_BIG).draw(b, txt.failTitle, 77f.tohud(), 128f.tohud())
            after {
                if (money >= 0) {
                    am.font(FONT_BIG).draw(b, txt.bail.format(bail()), 77f.tohud(), 100f.tohud())
                    am.font(FONT_BIG).draw(b, txt.money(money), 77f.tohud(), 16f.tohud())
                    handleNext(States.EMPTY_ROOM)
                } else {
                    am.font(FONT_BIG).draw(b, txt.cantBail, 77f.tohud(), 100f.tohud())
                    am.font(FONT_BIG).draw(b, txt.seeYou, 77f.tohud(), 90f.tohud())
                    am.font(FONT_BIG).draw(b,txt.money(money), 77f.tohud(), 16f.tohud())
                    handleNext(States.TITLE_SCREEN)
                }
            }
        }
    }

    private fun handleNext(state: String) {
        if (canSkip && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || inputs.isMouseClicked)) {
            IOC.put("state", state)
        }
    }

    override fun onExit() {

    }

}
