package org.catinthedark.itsadeal.game

object Const {
    object Screen {
        const val WIDTH = 256
        const val HEIGHT = 144
        const val ZOOM = 1f

        const val WIDTH_BIG = 1024
        const val HEIGHT_BIG = 600
        const val ZOOM_BIG = 1f
    }

    object Projection {
        const val ratio = Screen.WIDTH_BIG.toFloat() / Screen.WIDTH.toFloat()
        fun toHud(pos: Float) = pos * ratio
    }

    object Balance {
        const val START_MONEY = 150
        const val SKIP_COST = 50
    }
}
