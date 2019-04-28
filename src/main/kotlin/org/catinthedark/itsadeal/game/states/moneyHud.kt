package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.lib.managed

fun moneyHud(
    stage: Stage,
    hud: Stage,
    am: AssetManager
) {
    hud.batch.managed {
        val money = IOC.atOr("money", 0)
        am.at<BitmapFont>(Assets.Names.FONT_SMALL).draw(
            it,
            "$money",
            Const.Projection.toHud(100f),
            Const.Projection.toHud(144f)
        )
    }
}
