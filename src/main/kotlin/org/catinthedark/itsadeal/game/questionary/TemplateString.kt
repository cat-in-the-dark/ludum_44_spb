package org.catinthedark.itsadeal.game.questionary

data class TemplateString(private val template: String, private val substitution: List<List<String>>) {

    constructor(value: String) : this(value, emptyList())

    fun getText(): String {
        val subList = substitution.map { sub ->
            sub.random()
        }

        return String.format(template, *subList.toTypedArray())
    }
}
