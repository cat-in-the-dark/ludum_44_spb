package org.catinthedark.itsadeal.game

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import kotlin.math.roundToInt

class InputAdapterHolder(
    private val stage: Stage
) : InputAdapter() {
    var isMouseClicked = false
    var mouseX: Int = -1
    var mouseY: Int = -1

    fun update() {
        isMouseClicked = false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (pointer == Input.Buttons.LEFT) {
            val vec = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
            stage.viewport.unproject(vec)
            isMouseClicked = true
            mouseX = vec.x.roundToInt()
            mouseY = vec.y.roundToInt()
            println("x=$mouseX, y=$mouseY")
        }

        return true
    }
}
