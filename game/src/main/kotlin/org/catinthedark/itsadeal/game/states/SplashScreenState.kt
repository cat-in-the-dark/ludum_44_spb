package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.states.IState
import org.catinthedark.itsadeal.lib.managed

class SplashScreenState(
    private val stage: Stage
): IState {
    private val am: AssetManager by lazy { Assets.load() }

    override fun onActivate() {

    }

    override fun onUpdate() {
        if (am.update()) {
            IOC.put("state", States.TITLE_SCREEN)
            IOC.put("assetManager", am)
        } else {
            Gdx.app.log("SplashScreen", "Loading assets...${am.progress}")
        }

        if (am.isLoaded(Assets.Names.LOGO, Texture::class.java)) {
            stage.batch.managed {
                it.draw(am.get(Assets.Names.LOGO, Texture::class.java), 0f, 0f)
            }
        }
    }

    override fun onExit() {

    }
}
