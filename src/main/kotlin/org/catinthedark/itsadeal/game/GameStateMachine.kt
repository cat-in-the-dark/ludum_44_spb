package org.catinthedark.itsadeal.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.states.*
import org.slf4j.LoggerFactory

class GameStateMachine(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val inputs = InputAdapterHolder(stage)

    private val states = mapOf(
        States.EMPTY_ROOM to EmptyRoomState(stage, hud, am),
        States.WITH_MAN to WithManState(stage, hud, am),
        States.DOCUMENT_REVIEW to DocumentReviewState(stage, hud, am),
        States.FAIL to FailState(stage, hud, am),
        States.PROFIT to ProfitState(stage, hud, am),
        States.SKIP to ProfitState(stage, hud, am)
    )
    private var currentState: States = States.NONE

    init {
        Gdx.input.inputProcessor = inputs
        IOC.put("inputs", inputs)
        IOC.put("state", States.EMPTY_ROOM)
    }


    fun render() {
        val state = IOC.atOr("state", States.NONE)
        if (state != currentState) {
            logger.info("Transition from $currentState to $state")
            states[currentState]?.onExit()
            states[state]?.onActivate()
            currentState = state
        }

        states[currentState]?.onUpdate() ?: logger.info("Unknown $state")
        inputs.update()
    }

    fun onExit() {
        Gdx.input.inputProcessor = null
    }
}
