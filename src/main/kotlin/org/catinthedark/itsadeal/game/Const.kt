package org.catinthedark.itsadeal.game

object Const {
    object Screen {
        val WIDTH = 256
        val HEIGHT = 144
        val ZOOM = 1f

        val WIDTH_BIG = 1024
        val HEIGHT_BIG = 600
        val ZOOM_BIG = 1f
    }

    object Projection {
        val ratio = Screen.WIDTH_BIG.toFloat() / Screen.WIDTH.toFloat()
        fun toHud(pos: Float) = pos * ratio
    }
}
