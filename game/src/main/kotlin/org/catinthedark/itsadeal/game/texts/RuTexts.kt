package org.catinthedark.itsadeal.game.texts

class RuTexts : Texts {
    override val gameName = "It's a deal"
    override val pressToStart = "Нажмите Пробел, чтобы начать"
    override val ok = "Ясно"
    override val bankrot = "Вам конец!"
    override val cantPay = "Вы не можете заплатить за крышу"
    override val failTitle = "Вас подставили!"
    override val bail = "Вы откупились за %d у.е."
    override val cantBail = "Вы не можете откупиться."
    override val seeYou = "Увидимся через 15 лет."
    override val money = { money: Int -> "Баланс: $money у.е." }
    override val win = "Успешный успех!"
    override val winDescription = "Вы накопили на самолет\n и улетели на острова"
    override val profit = "ПРОФИТ: %d у.е."
    override val goal = { achName: String, achCost: Int -> "Цель: $achName за $achCost у.е" }
    override val payment = { credit: Int -> "Плата за крышу: $credit у.е." }
    override val rejected = "Отказано"
    override val reject = "Отказать"
    override val accept = "Принять"
    override val back = "Назад"
    override val achievments = listOf(
        "Костюм",
        "Часы",
        "Тачка",
        "Пентхаус",
        "Вилла",
        "Яхта",
        "Самолет"
    )
    override val headers = listOf(
        "Закон",
        "Проект",
        "Поправки в  закон",
        "Приказ",
        "Указ"
    )

    override val actions = listOf(
        "об оптимизации",
        "о финансировании",
        "о ликвидации",
        "о проверке",
        "о передаче      инвесторам"
    )

    override val subjectToIssuer = mapOf(
        "Медицинских учреждений" to "Министерство здравоохранения",
        "Объектов охраны НЛО" to "Министерство летающих тарелок",
        "Городского имущества" to "Министерство гражданского строительства",
        "Ядерных отходов" to "Министерство атомной промышленности",
        "Объектов культурного наследия" to "Министерство культуры",
        "Парникового эффекта" to "Министерство охраны природы",
        "Векторного гипетртекстового фидонета" to "Министерство гипертекстового фидонета",
        "Систем оповещения" to "Министерство гражданской обороны",
        "Средств контрацепции" to "Министерство планирования семьи"
    )

    override val subjects: Collection<String>
        get() = subjectToIssuer.keys

    /**
     * Map<String, Pair<String, String>> - question, neutral answer, incriminating answer
     */
    override val questionTemplates = mapOf(
        Pair(
            TemplateString(
                "Всё %s?",
                "как договаривались",
                "согласно договорённостям"
            ),
            Pair(
                TemplateString("Можете %s", "пересчитать", "перепроверить"),
                TemplateString(
                    "%s",
                    "У нас всё точно",
                    "Да, я лично метил эти купюры"
                )
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
                TemplateString(
                    "%s",
                    "Да, как назло забыл сегодня зонт",
                    "Да, вот, ботинки промочил"
                ),
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
                TemplateString(
                    "Думаю, скоро у вас появится %s",
                    "свободное время",
                    "много свободнгого времени"
                )
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
                TemplateString(
                    "%s",
                    "При чём тут ваша жена?",
                    "Не думаю, что я с ней знаком"
                ),
                TemplateString("%s", "Уже передал", "Я сам к ней зайду.")
            )
        ),
        Pair(
            TemplateString(
                "%s внесли все изменения, о которых мы договаривались?",
                "На этот раз вы",
                "Вы"
            ),
            Pair(
                TemplateString(
                    "Да, %s",
                    "я всё перепроверил",
                    "конечно",
                    "полночи вносил правки."
                ),
                TemplateString("Мы внесли все правки?.. Мы внесли все правки.")
            )
        ),
        Pair(
            TemplateString("День только начинается, а я уже устал"),
            Pair(
                TemplateString(
                    "%s",
                    "Я тоже устал",
                    "А кому сейчас легко",
                    "Это от переработок",
                    "Отдохните"
                ),
                TemplateString("Думаю, у вас сегодня будет короткий день")
            )
        ),
        Pair(
            TemplateString("Вы из ГРУ?"),
            Pair(
                TemplateString("%s", "Что?", "Конечно нет!", "Хорошая шутка"),
                TemplateString(
                    "Я нет. И я нет.",
                    "Вы так говорите, как будто это что-то плохое"
                )
            )
        ),
        Pair(
            TemplateString(
                "%s",
                "Из какого вы, говорите, министерства?",
                "Из какого вы министерства?"
            ),
            Pair(
                TemplateString("<correct_issuer>"),
                TemplateString("<wrong_issuer>")
            )
        )
    )
}
