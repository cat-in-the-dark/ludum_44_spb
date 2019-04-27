package org.catinthedark.itsadeal.game

object Balance {
    const val skipCost: Int = 100
}

class Player {
    var money: Int = 100

    fun isPlaying(): Boolean = true
    fun canAskQuestions(): Boolean = true
    fun payMoneyForSkip() {
        money -= Balance.skipCost
    }

    fun isBankrot(): Boolean {
        TODO()
    }

}

class Question {

}

class Person {
    val money: Int = 0

    fun nextQuestions(): List<Question> = emptyList()
    fun check(decision: Decision): Boolean {
        TODO()
    }
}

class PersonGenerator {
    fun next(): Person {
        TODO()
    }
}

enum class Decision {
    SKIP, TRY
}

class UI {
    fun showListOf(questions: List<Question>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showAnswer(selectedQuestion: Question) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showDecisionWindow(): Decision {
        TODO()
    }

    fun showBankrot(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isAnyKeyPressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showPunishment(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class Button {
    fun toIndex(): Int = TODO()
}

class Controls {
    fun getPressedButton(): Button {
        TODO()
    }
}

class Game {
    fun restart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class StateMachine {
    fun run() {
        val player = Player()
        val personGenerator = PersonGenerator()
        val ui = UI()
        val controls = Controls()
        val game = Game()
        while (player.isPlaying()) {
            val person = personGenerator.next()
            while(player.canAskQuestions()) {
                val questions = person.nextQuestions()
                ui.showListOf(questions)
                val id = controls.getPressedButton().toIndex()
                val selectedQuestion = questions[id]
                ui.showAnswer(selectedQuestion)
            }
            val decision = ui.showDecisionWindow()
            if (decision == Decision.SKIP) {
                player.payMoneyForSkip()
                if (player.isBankrot()) {
                    ui.showBankrot(player)
                    ui.isAnyKeyPressed()
                    game.restart()
                }
            } else {
                if (person.check(decision)) {
                    player.money += person.money
                } else {
                    ui.showPunishment(player)
                    ui.isAnyKeyPressed()
                    game.restart()
                }
            }
        }
    }
}

