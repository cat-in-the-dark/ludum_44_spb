package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.Const.Balance.incriminatingFactor
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import kotlin.random.Random

class PersonFactory {

    // TODO: generate more random pools for other levels
    private val initialRandomPool: MutableList<Boolean> = mutableListOf(1, 1, 0).apply {
        shuffle()
    }.map { it == 0 }.toMutableList()

    private val randomPool: MutableList<Boolean> = mutableListOf()

    private fun putNextPool(list: MutableList<Boolean>) {
        list.addAll(listOf(1, 1, 1, 1, 0, 0, 0).map { it == 0 }.shuffled())
    }

    fun getRandomPerson(): Person {
        val money = IOC.atOr("money", 0)
        val txt: Texts by IOC

        val isBad = if (initialRandomPool.isNotEmpty()) initialRandomPool.removeAt(0)
        else {
            if (randomPool.isEmpty()) {
                putNextPool(randomPool)
            }
            randomPool.removeAt(0)
        }
        return Person(isBad, incriminatingFactor(money), txt)
    }

    private fun nextBool() = Random(System.currentTimeMillis()).nextBoolean()
}
