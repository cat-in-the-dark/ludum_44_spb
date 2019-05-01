package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.at
import org.catinthedark.itsadeal.lib.atOrFail

fun String.insertPeriodically(
    insert: String, period: Int
): String {
    val builder = StringBuilder(
        this.length + insert.length * (this.length / period) + 1
    )

    var index = 0
    var prefix = ""
    while (index < this.length) {
        // Don't put the insert in the very first iteration.
        // This is easier than appending it *after* each substring
        builder.append(prefix)
        prefix = insert
        builder.append(
            this.substring(
                index, Math.min(index + period, this.length)
            )
        )
        index += period
    }
    return builder.toString()
}

fun String.replaceIssuer(): String {
    val txt: Texts by IOC
    val subject = IOC.atOrFail<DocContent>("docContents").subject
    val correctIssuer = txt.subjectToIssuer[subject] ?: error("There is not $subject")

    if (IOC.at<String>("wrongIssuer").isNullOrBlank()) {
        val res = txt.subjectToIssuer.filterValues { it != correctIssuer }.values.random()
        IOC.put("wrongIssuer", res)
    }

    val wrongIssuer = IOC.atOrFail<String>("wrongIssuer")

    return replace("<correct_issuer>", correctIssuer).replace("<wrong_issuer>", wrongIssuer)
}
