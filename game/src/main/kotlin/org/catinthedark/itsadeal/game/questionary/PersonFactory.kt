package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.Const.Balance.incriminatingFactor
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import kotlin.random.Random

class PersonFactory {

    // TODO: generate more random pools for other levels
    private val randomPool: MutableList<Boolean> = mutableListOf(1, 1, 0).apply {
        shuffle()
    }.map { it == 0 }.toMutableList()

    fun getRandomPerson(): Person {
        val money = IOC.atOr("money", 0)

        val isBad = if (randomPool.isNotEmpty()) randomPool.removeAt(0) else nextBool()
        return Person(isBad, incriminatingFactor(money))
    }

    private fun nextBool() = Random(System.currentTimeMillis()).nextBoolean()
}
