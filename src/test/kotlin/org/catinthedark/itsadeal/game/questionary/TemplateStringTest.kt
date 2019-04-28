package org.catinthedark.itsadeal.game.questionary

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TemplateStringTest {
    @Test
    fun getText() {
        val text = TemplateString(
            "this is a %s test %s",
            listOf(listOf("one"), listOf("two"))
        )

        assertEquals("this is a one test two", text.getText())
    }

    @Test
    fun getTextRandom() {
        val text = TemplateString(
            "this is a %s test %s",
            listOf(listOf("one", "two"), listOf("three", "four"))
        )

        val result = text.getText()
        assertTrue(result.contains("this is a"))
        assertTrue(result.contains("test"))
        assertTrue(result.contains("one") || result.contains("two"))
        assertTrue(result.contains("three") || result.contains("four"))
    }

    @Test
    fun getTextNotEnoughSunstitutions() {
        val text = TemplateString(
            "this is a %s test %s",
            listOf(listOf("one", "two"))
        )

        val result = text.getText()
        assertTrue(result.contains("this is a"))
        assertTrue(result.contains("test"))
        assertTrue(result.contains("one") || result.contains("two"))
    }
}
