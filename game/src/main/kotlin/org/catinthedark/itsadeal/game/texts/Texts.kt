package org.catinthedark.itsadeal.game.texts


interface Texts {
    val gameName: String
    val pressToStart: String
    val bankrot: String
    val cantPay: String
    val failTitle: String
    val bail: String
    val cantBail: String
    val seeYou: String
    val money: (Int) -> String
    val win: String
    val winDescription: String
    val profit: String
    val goal: (String, Int) -> String
    val payment: (Int) -> String
    val rejected: String
    val headers: List<String>
    val actions: List<String>
    val subjectToIssuer: Map<String, String>
    val subjects: Collection<String>
    val ok: String
    val reject: String
    val accept: String
    val back: String
    /**
     * Map<String, Pair<String, String>> - question, neutral answer, incriminating answer
     */
    val questionTemplates: Map<TemplateString, Pair<TemplateString, TemplateString>>
    val achievments: List<String>
}
