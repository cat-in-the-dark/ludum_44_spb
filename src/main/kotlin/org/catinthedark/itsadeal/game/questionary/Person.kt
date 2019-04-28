package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.exceptions.InvalidAnswerException
import org.catinthedark.itsadeal.game.exceptions.InvalidFractionException

class Person(
    private val isEvil: Boolean,
    //todo: handle evil man
    private val incriminatingPart: Float
) {

    init {
        if (incriminatingPart < 0 || incriminatingPart > 1) {
            throw InvalidFractionException("Fraction must be in [0,1], got $incriminatingPart")
        }
    }

    private var questions: MutableMap<String, Answer> = hashMapOf()

    fun getQuestions(number: Int): Map<String, String> {
        if (questions.isEmpty()) {
            val incriminatingAnswers: Int
            val neutralAnswers: Int
            if (isEvil) {
                incriminatingAnswers = (number * incriminatingPart).toInt()
                neutralAnswers = number - incriminatingAnswers
            } else {
                incriminatingAnswers = 0
                neutralAnswers = number
            }

            repeat(incriminatingAnswers) {
                val question = QuestionPool.getQuestion(AnswerType.INCRIMINATING)
                questions[question.first] = question.second
            }
            repeat(neutralAnswers) {
                val question = QuestionPool.getQuestion(AnswerType.NEUTRAL)
                questions[question.first] = question.second
            }

            return questions.mapValues { it.value.text }
        } else {
            return questions.mapValues { it.value.text }
        }
    }

    fun setAnswer(answer: Map.Entry<String, String>) {
        val answeredQuestionType =
            questions[answer.key]?.type ?: throw InvalidAnswerException("Question marked as answered was not asked!")
        questions.remove(answer.key)
        val newQuestion = QuestionPool.getQuestion(answeredQuestionType)
        questions[newQuestion.first] = newQuestion.second
    }

    fun getIsEvil(): Boolean {
        return isEvil
    }
}
