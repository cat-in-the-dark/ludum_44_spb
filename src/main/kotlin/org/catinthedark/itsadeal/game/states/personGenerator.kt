package org.catinthedark.itsadeal.game.states

import org.catinthedark.itsadeal.game.Assets
import org.catinthedark.itsadeal.game.Const.Balance.generateReward
import org.catinthedark.itsadeal.game.IOC
import org.catinthedark.itsadeal.game.RandomPersonTextures
import org.catinthedark.itsadeal.game.questionary.DocsGenerator
import org.catinthedark.itsadeal.game.questionary.PersonFactory

fun generatePerson() {
    IOC.put("personTextures", RandomPersonTextures())
    IOC.put("person", PersonFactory().getRandomPerson())
    IOC.put("askedQuestions", 0)
    IOC.put("current_answer", "")
    IOC.put("reward", generateReward())
    IOC.put("docContents", DocsGenerator().getDocument())
    IOC.put("docTexture", Assets.Names.DOCUMENTS.random())
}
