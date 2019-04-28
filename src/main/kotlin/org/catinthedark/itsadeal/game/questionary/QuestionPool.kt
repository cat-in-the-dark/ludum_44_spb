package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.exceptions.NotEnoughQuestionsException

object QuestionPool {
    /**
     * Map<String, Pair<String, String>> - question, neutral answer, incriminating answer
     */
    private val questionTemplates: Map<TemplateString, Pair<TemplateString, TemplateString>> = mapOf(
        Pair(
            TemplateString("%s, how are you?", listOf(listOf("Sup", "Hello", "Hi"))),
            Pair(
                TemplateString("I'm fine, thanks!"),
                TemplateString("Glory to the Arstotzka!")
            )
        )
    )

    private val isQuestionAskedMap: MutableMap<TemplateString, Boolean> = hashMapOf(
        *questionTemplates.map {
            Pair(it.key, false)
        }.toTypedArray()
    )

    fun getQuestion(answerType: AnswerType): Pair<String, Answer> {
        if (isQuestionAskedMap.filter { it.value }.count() > questionTemplates.size) {
            throw NotEnoughQuestionsException(
                "Requested question, but got nothing!"
            )
        }

        val question = questionTemplates.entries.random()
        isQuestionAskedMap[question.key] = false

        return if (answerType == AnswerType.NEUTRAL) {
            Pair(question.key.getText(), Answer(question.value.first.getText(), AnswerType.NEUTRAL))
        } else {
            Pair(question.key.getText(), Answer(question.value.second.getText(), AnswerType.NEUTRAL))
        }
    }
}
