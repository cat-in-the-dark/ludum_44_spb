package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const
import org.catinthedark.itsadeal.game.at
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.states.IState
import org.catinthedark.itsadeal.lib.atOrFail

class StartNewGameState: IState {
    override fun onActivate() {
        IOC.put("money", Const.Balance.START_MONEY)
        IOC.put("state", States.EMPTY_ROOM)
        val music = IOC.atOrFail<AssetManager>("assetManager").at<Music>(Assets.Names.Sounds.MUSIC)
        music.isLooping = true
        music.play()
    }

    override fun onUpdate() {

    }

    override fun onExit() {

    }
}
