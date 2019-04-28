package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.managed

class WithManState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private var personTextures: PersonTextures = RandomPersonTextures()

    override fun onActivate() {

    }

    override fun onUpdate() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            personTextures = RandomPersonTextures()
        }
        if (IOC.at<InputAdapterHolder>("inputs")?.isMouseClicked == true) {
            IOC.put("state", States.DOCUMENT_REVIEW)
        }

        stage.batch.managed {
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f) // TODO: make kivok

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)
        }
        drawQuestions()
    }

    private fun drawQuestions() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, "Все как договаривались?", 0f, Const.Projection.toHud(130f))
            am.at<BitmapFont>(Assets.Names.FONT_SMALL)
                .draw(it, "Похоже у вас сегодня удачный день. Я обычно очень занят", 0f, Const.Projection.toHud(120f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG).draw(it, "Вы торопитесь?", 0f, Const.Projection.toHud(110f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG).draw(it, "Передать привет жене?", 0f, Const.Projection.toHud(90f))
        }
    }

    override fun onExit() {

    }

}
