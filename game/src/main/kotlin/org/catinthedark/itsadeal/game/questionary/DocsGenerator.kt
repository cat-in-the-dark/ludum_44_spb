package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.texts.Texts

fun getDocument(txt: Texts) = DocContent(
    txt.headers.random(),
    txt.actions.random(),
    txt.subjects.random()
)

data class DocContent(val header: String, val action: String, val subject: String)
