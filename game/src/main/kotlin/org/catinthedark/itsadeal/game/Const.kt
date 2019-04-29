package org.catinthedark.itsadeal.game

object Const {
    object Screen {
        const val WIDTH = 256
        const val HEIGHT = 144
        const val ZOOM = 1f

        const val WIDTH_BIG = 1024
        const val HEIGHT_BIG = 576
        const val ZOOM_BIG = 1f
    }

    object Projection {
        const val ratio = Screen.WIDTH_BIG.toFloat() / Screen.WIDTH.toFloat()
        fun toHud(pos: Float) = pos * ratio
    }

    object Balance {
        const val START_MONEY = 150
        const val SKIP_COST = 50

        fun generateReward(money: Int): Int {
            if (money < 500) return (200..420).random()
            if (money < 2500) return (450..750).random()
            if (money < 15000) return (750..2150).random()
            if (money < 80000) return (2000..6000).random()
            if (money < 500000) return (5000..50000).random()
            if (money < 7000000) return (70000..600000).random()
            if (money < 90000000) return (100000..1000000).random()
            return 1000
        }

        fun nextAchievement(money: Int): Pair<Int, String> {
            if (money < 500) return 500 to "Костюм"
            if (money < 1500) return 1000 to "Часы"
            if (money < 7500) return 7500 to "Тачка"
            if (money < 40000) return 40000 to "Пентхаус"
            if (money < 500000) return 500000 to "Вилла"
            if (money < 7000000) return 7000000 to "Яхта"
            if (money < 10000000) return 10000000 to "Самолет"
            return Int.MAX_VALUE to "GG"
        }
    }
}
