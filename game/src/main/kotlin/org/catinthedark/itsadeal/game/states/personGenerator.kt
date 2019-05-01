package org.catinthedark.itsadeal.game.states

import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const.Balance.generateReward
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.game.RandomPersonTextures
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.atOr
import org.catinthedark.itsadeal.game.questionary.PersonFactory
import org.catinthedark.itsadeal.game.questionary.getDocument
import org.catinthedark.itsadeal.lib.atOrFail

fun generatePerson() {
    val txt: Texts by IOC
    IOC.put("personTextures", RandomPersonTextures())
    IOC.put("person", IOC.atOrFail<PersonFactory>("personFactory").getRandomPerson())
    IOC.put("askedQuestions", 0)
    IOC.put("current_answer", "")
    IOC.put("wrongIssuer", "")
    IOC.put("docContents", getDocument(txt))
    IOC.put("docTexture", Assets.Names.DOCUMENTS.random())
    IOC.put("reward", generateReward(IOC.atOr("money", 0)))
}
