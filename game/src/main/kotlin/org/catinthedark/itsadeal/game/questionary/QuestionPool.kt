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
                TemplateString("Можете %s", "пересчитать", "перепроверить"),
                TemplateString("%s", "У нас всё точно", "Да, я лично метил эти купюры")
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
            TemplateString("Как-то погода сегодня не очень..."),
            Pair(
                TemplateString("%s", "Да, как назло забыл сегодня зонт", "Да, вот, ботинки промочил"),
                TemplateString(
                    "%s",
                    "Так точно",
                    "Да, испачкал любимые кирзачи!",
                    "Теперь коврики в чёрной волге мыть..."
                )
            )
        ),
        Pair(
            TemplateString(
                "Похоже, %s. Я обычно очень занят.",
                "у вас сегодня удачный день", "вам сегодня повезло"
            ),
            Pair(
                TemplateString("%s об этом не думал", "Хм, я", "Я"),
                TemplateString("Думаю, скоро у вас появится %s", "свободное время", "много свободнгого времени")
            )
        ),
        Pair(
            TemplateString("Вы торопитесь?"),
            Pair(
                TemplateString("Нет, я никуда не тороплюсь"),
                TemplateString("%s", "Никак нет", "Нет, мы никуда не торопимся")
            )
        ),
        Pair(
            TemplateString("Передать привет жене?"),
            Pair(
                TemplateString("%s", "При чём тут ваша жена?", "Не думаю, что я с ней знаком"),
                TemplateString("%s", "Уже передал", "Я сам к ней зайду.")
            )
        ),
        Pair(
            TemplateString("%s внесли все изменения, о которых мы договаривались?", "На этот раз вы", "Вы"),
            Pair(
                TemplateString("Да, %s", "я всё перепроверил", "конечно", "полночи вносил правки."),
                TemplateString("Мы внесли все правки?.. Мы внесли все правки.")
            )
        ),
        Pair(
            TemplateString("День только начинается, а я уже устал"),
            Pair(
                TemplateString("%s", "Я тоже устал", "А кому сейчас легко", "Это от переработок", "Отдохните"),
                TemplateString("Думаю, у вас сегодня будет короткий день")
            )
        ),
        Pair(
            TemplateString("Вы из ГРУ?"),
            Pair(
                TemplateString("%s", "Что?", "Конечно нет!", "Хорошая шутка"),
                TemplateString("Я нет. И я нет.", "Вы так говорите, как будто это что-то плохое")
            )
        ),
        Pair(
            TemplateString("%s", "Из какого вы, говорите, министерства?", "Из какого вы министерства?"),
            Pair(
                TemplateString("<correct_issuer>"),
                TemplateString("<wrong_issuer>")
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
