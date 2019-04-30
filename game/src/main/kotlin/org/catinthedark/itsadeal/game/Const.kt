package org.catinthedark.itsadeal.game

import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.getValue
import org.catinthedark.itsadeal.lib.updateOrFail

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
        fun Float.tohud() = this * ratio
    }

    object Balance {
        const val START_MONEY = 150
        const val GG = "GG"

        fun everyDayCredit(money: Int): Int {
            if (money < 200) return 50
            if (money < 3200) return 416
            if (money < 25000) return 3250
            if (money < 195200) return 25376
            if (money < 1578000) return 205140
            if (money < 9999999) return 1300000
            if (money < 99000000) return 12870000
            return 0
        }

        fun generateReward(money: Int): Int {
            if (money < 200) return (40..140).random()
            if (money < 3200) return (640..2240).random()
            if (money < 25000) return (5000..17500).random()
            if (money < 195200) return (39040..136640).random()
            if (money < 1578000) return (315600..1104600).random()
            if (money < 9999999) return (2000000..6999999).random()
            if (money < 99000000) return (19800000..69300000).random()
            return 0
        }

        fun nextAchievement(money: Int): Pair<Int, String> {
            if (money < 200) return 200 to "Костюм"
            if (money < 3200) return 3200 to "Часы"
            if (money < 25000) return 25000 to "Тачка"
            if (money < 195200) return 195200 to "Пентхаус"
            if (money < 1578000) return 1578000 to "Вилла"
            if (money < 9999999) return 9999999 to "Яхта"
            if (money < 99000000) return 99000000 to "Самолет"
            return Int.MAX_VALUE to GG
        }

        fun incriminatingFactor(money: Int): Float {
            if (money < 500) return 1f
            if (money < 1500) return 0.7f
            if (money < 7500) return 0.7f
            return 0.4f
        }

        fun bail(reward: Int): Int {
            return reward * 2
        }

        fun payCredit(): Int {
            val money: Int by IOC
            val credit = everyDayCredit(money)
            IOC.put("money", money - credit)
            return credit
        }
    }
}
