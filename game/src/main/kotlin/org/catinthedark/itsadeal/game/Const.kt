package org.catinthedark.itsadeal.game

import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import kotlin.math.max

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
        const val maxAskedQuestions = 3
        const val START_MONEY = 150
        const val GG = "GG"

        // TODO: emulate it with monte carlo

        fun everyDayCredit(money: Int): Int {
            return max((money * 0.07).toInt(), 30)
        }

        fun generateReward(money: Int): Int {
            if (money < 200) return (140..160).random()
            if (money < 3200) return (1250..2240).random()
            if (money < 25000) return (12500..17500).random()
            if (money < 195200) return (95040..106640).random()
            if (money < 1578000) return (975600..1104600).random()
            if (money < 9999999) return (5785000..5999999).random()
            if (money < 99000000) return (64800000..69300000).random()
            return 0
        }

        fun nextAchievement(money: Int, txt: Texts): Pair<Int, String> {
            if (money < 200) return 200 to txt.achievments[0]
            if (money < 3200) return 3200 to txt.achievments[1]
            if (money < 25000) return 25000 to txt.achievments[2]
            if (money < 195200) return 195200 to txt.achievments[3]
            if (money < 1578000) return 1578000 to txt.achievments[4]
            if (money < 9999999) return 9999999 to txt.achievments[5]
            if (money < 99000000) return 99000000 to txt.achievments[6]
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
