package org.catinthedark.itsadeal.game.exceptions

class NotEnoughQuestionsException : Throwable {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}
