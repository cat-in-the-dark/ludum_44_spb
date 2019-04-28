package org.catinthedark.itsadeal.game.questionary

import org.catinthedark.itsadeal.game.exceptions.NotEnoughQuestionsException

class QuestionPool {
    /**
     * Map<String, Pair<String, String>> - question, neutral answer, incriminating answer
     */
    private val questionTemplates: Map<TemplateString, Pair<TemplateString, TemplateString>> = mapOf(
        Pair(
            TemplateString("Всё %s?", "как договаривались", "согласно договорённостям"),
            Pair(
                TemplateString("Можете пересчитать."),
                TemplateString("У нас всё точно.")
            )
        ),
        Pair(
            TemplateString("Хорошая сегодня погода, не так ли?"),
            Pair(
                TemplateString("Да, с радостью бы прогулялся сегодня"),
                TemplateString("Так точно")
            )
        ),
        Pair(
            TemplateString(
                "Похоже, %s. Я обычно очень занят.",
                listOf(listOf("у вас сегодня удачный день", "вам сегодня повезло"))
            ),
            Pair(
                TemplateString("%s об этом не думал", "Хм, я", "Я"),
                TemplateString("Да, я давно ждал нашей встречи.")
            )
        ),
        Pair(
            TemplateString("Вы торопитесь?"),
            Pair(
                TemplateString("Нет, я никуда не тороплюсь"),
                TemplateString("Нет, мы никуда не торопимся")
            )
        ),
        Pair(
            TemplateString("Передать привет жене?"),
            Pair(
                TemplateString("%s", "При чём тут ваша жена?", "Не думаю, что я с ней знаком"),
                TemplateString("Ммм... Да. Да. Обязательно.")
            )
        ),
        Pair(
            TemplateString("На этот раз вы внесли все изменения о которых мы договаривались?"),
            Pair(
                TemplateString("Да, я всё перепроверил"),
                TemplateString("У нас всё точно")
            )
        )
    )

    private
    val isQuestionAskedMap: MutableMap<TemplateString, Boolean> = hashMapOf(
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

        val notAskedQuestions = isQuestionAskedMap.filter { !it.value }.keys

        val question = questionTemplates.filter { notAskedQuestions.contains(it.key) }.entries.random()
        isQuestionAskedMap[question.key] = true

        return if (answerType == AnswerType.NEUTRAL) {
            Pair(question.key.getText(), Answer(question.value.first.getText(), AnswerType.NEUTRAL))
        } else {
            Pair(question.key.getText(), Answer(question.value.second.getText(), AnswerType.INCRIMINATING))
        }
    }
}
