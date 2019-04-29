package org.catinthedark.itsadeal.game.questionary

public class DocsGenerator {
    private val headers = listOf(
        "Закон",
        "Проект",
        "Поправки в  закон",
        "Приказ",
        "Указ"
    )

    private val subHeaders = listOf(
        "об оптимизации",
        "о финансировании",
        "о ликвидации",
        "о проверке",
        "о передаче      инвесторам"
    )

    private val contents = listOf(
        "Медицинских учреждений",
        "Объектов охраны НЛО",
        "Городского имущества",
        "Ядерных отходов",
        "Объектов культурного наследия",
        "Парникового эффекта",
        "Векторного гипетртекстового фидонета",
        "Систем оповещения",
        "Средств контрацепции"
    )

    fun getDocument(): List<String> {
        return listOf(
            headers.random(),
            subHeaders.random(),
            contents.random()
        )
    }
}
