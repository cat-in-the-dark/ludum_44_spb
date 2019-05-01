package org.catinthedark.itsadeal.game.texts

data class TemplateString(private val template: String, private val substitution: List<List<String>>) {

    constructor(value: String) : this(value, emptyList())

    constructor(value: String, vararg substitution: String) : this(value, listOf(substitution.toList()))

    fun getText(): String {
        val subList = substitution.map { sub ->
            sub.random()
        }

        return String.format(template, *subList.toTypedArray())
    }
}
