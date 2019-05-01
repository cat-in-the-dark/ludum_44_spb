package org.catinthedark.itsadeal.game.testing

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import org.catinthedark.itsadeal.game.InputAdapterHolder
import org.catinthedark.itsadeal.game.questionary.Person
import org.catinthedark.itsadeal.game.states.States.DOCUMENT_REVIEW
import org.catinthedark.itsadeal.game.states.States.EMPTY_ROOM
import org.catinthedark.itsadeal.game.states.States.PROFIT
import org.catinthedark.itsadeal.game.states.States.SKIP
import org.catinthedark.itsadeal.game.states.States.WITH_MAN
import org.catinthedark.itsadeal.lib.*
import org.slf4j.LoggerFactory

class Autopilot {
    private var currentState = ""
    private var pin = 0
    private var activated = false
    private val logger = LoggerFactory.getLogger(Autopilot::class.java)
    private val moneys = mutableListOf<Int>()

    var steps: Int = 0

    fun reset() {
        logger.info("It took autopilot $steps steps to beat this game")
        logger.info("Money history = $moneys")
        steps = 0
        activated = false
        pin = 0
        IOC.put("deffer", DefferImpl())
        moneys.clear()
    }

    fun update() {
        if (!activated) {
            updatePin()
            return
        }

        updateAutoPilot()
    }


    private fun updateAutoPilot() {
        val state = IOC.atOrFail<String>("state")
        val inputs: InputAdapterHolder by IOC

        if (state == currentState) {
            return
        }

        logger.info("Got state $state in autopilot")

        if (state == WITH_MAN) {
            val money: Int by IOC
            moneys.add(money)
            inputs.mouseX = 1
            inputs.mouseY = 1
            inputs.isMouseClicked = true
        } else if (state == EMPTY_ROOM) {
            inputs.mouseX = 50
            inputs.mouseY = 50
            inputs.isMouseClicked = true
        } else if (state == DOCUMENT_REVIEW) {
            val person = IOC.atOrFail<Person>("person")
            if (person.isEvil) {
                inputs.mouseX = 9
                inputs.mouseY = 9
            } else {
                inputs.mouseX = 199
                inputs.mouseY = 9
            }
            inputs.isMouseClicked = true
            steps++
        } else if (state == PROFIT) {
            inputs.isMouseClicked = true
        } else if (state == SKIP) {
            inputs.mouseX = 50
            inputs.mouseY = 50
            inputs.isMouseClicked = true
        }

        currentState = state
    }

    private fun updatePin() {
        val state = IOC.atOr("state", "")
        if (state != currentState) {
            pin = 0
        }

        currentState = state

        when {
            Gdx.input.isKeyJustPressed(Input.Keys.T) -> pin = pin * 10 + 1
            Gdx.input.isKeyJustPressed(Input.Keys.E) -> pin = pin * 10 + 2
            Gdx.input.isKeyJustPressed(Input.Keys.S) -> pin = pin * 10 + 3
        }

        if (pin == 1231) {
            moneys.clear()
            logger.info("Auto player unlocked!")
            IOC.put("deffer", NoDeffer())
            activated = true
        }
    }
}
