package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import org.catinthedark.itsadeal.lib.managed

fun moneyHud(
    stage: Stage,
    hud: Stage,
    am: AssetManager
) {
    val x = (256-98)/2f
    val y = 142f - 16f
    val money = IOC.atOr("money", 0)
    val txt: Texts by IOC
    val next = Const.Balance.nextAchievement(money, txt)
    val text = "$money/${next.first} : ${next.second}"

    stage.batch.managed {
        it.draw(am.at<Texture>(Assets.Names.PROGRESS_BAR), x, y)
    }

    hud.batch.managed {
        am.at<BitmapFont>(Assets.Names.FONT_SMALL).draw(
            it,
            text,
            Const.Projection.toHud(x + 14),
            Const.Projection.toHud(y + 10)
        )
    }
}
