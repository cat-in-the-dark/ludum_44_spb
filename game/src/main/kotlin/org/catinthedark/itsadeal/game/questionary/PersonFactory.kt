package org.catinthedark.itsadeal.game.questionary

import kotlin.random.Random

class PersonFactory {
    private val INCRIMINATING_PART = 1f

    fun getRandomPerson(): Person {
        return Person(Random(System.currentTimeMillis()).nextBoolean(), INCRIMINATING_PART)
    }
}
