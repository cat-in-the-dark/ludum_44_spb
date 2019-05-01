package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.game.exceptions.NotEnoughQuestionsException

class QuestionPool(
    private val txt: Texts
) {
    private val isQuestionAskedMap = hashMapOf(
        *txt.questionTemplates.map {
            Pair(it.key, false)
        }.toTypedArray()
    )

    fun getQuestion(answerType: AnswerType): Pair<String, Answer> {
        if (isQuestionAskedMap.filter { it.value }.count() > txt.questionTemplates.size) {
            throw NotEnoughQuestionsException(
                "Requested question, but got nothing!"
            )
        }

        val notAskedQuestions = isQuestionAskedMap.filter { !it.value }.keys

        val question = txt.questionTemplates.filter { notAskedQuestions.contains(it.key) }.entries.random()
        isQuestionAskedMap[question.key] = true

        return if (answerType == AnswerType.NEUTRAL) {
            Pair(question.key.getText(), Answer(question.value.first.getText(), AnswerType.NEUTRAL))
        } else {
            Pair(question.key.getText(), Answer(question.value.second.getText(), AnswerType.INCRIMINATING))
        }
    }
}
