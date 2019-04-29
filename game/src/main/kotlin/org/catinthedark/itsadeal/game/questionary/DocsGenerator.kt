package org.catinthedark.itsadeal.game.questionary

object DocsGenerator {
    private val headers = listOf(
        "Закон",
        "Проект",
        "Поправки в  закон",
        "Приказ",
        "Указ"
    )

    private val actions = listOf(
        "об оптимизации",
        "о финансировании",
        "о ликвидации",
        "о проверке",
        "о передаче      инвесторам"
    )

    val subjectToIssuer = mapOf(
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

    private val subjects: Collection<String>
        get() = subjectToIssuer.keys

    fun getDocument(): DocContent {
        return DocContent(
            headers.random(),
            actions.random(),
            subjects.random()
        )
    }
}

data class DocContent(val header: String, val action: String, val subject: String)
