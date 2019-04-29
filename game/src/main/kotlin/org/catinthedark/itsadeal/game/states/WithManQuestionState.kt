package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.exceptions.InvalidAnswerException
import org.catinthedark.itsadeal.game.questionary.Person
import org.catinthedark.itsadeal.game.questionary.insertPeriodically
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class WithManQuestionState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val log = LoggerFactory.getLogger(WithManState::class.java)
    private val buttons = listOf(
        Button(60, 32, 190, 44, onClick = {
            // First
            answer(0)
        }),
        Button(60, 20, 190, 32, onClick = {
            // Second
            answer(1)
        }),
        Button(60, 8, 190, 20, onClick = {
            // Third
            answer(2)
        })
    )
    private val maxAskedQuestions = 3
    private val noQuestions = "Вопросов больше нет."

    override fun onActivate() {

    }

    private fun answer(questionIndex: Int) {
        val person = IOC.atOrFail<Person>("person")
        val questionMap = person.getQuestions(3)
        val questionList = questionMap.map { it.key }
        val askedQuestions = IOC.atOr("askedQuestions", 0)

        if (askedQuestions >= maxAskedQuestions) return
        if (questionIndex >= questionList.size) return

        val answer = questionMap[questionList[questionIndex]]
            ?: throw InvalidAnswerException("No answer at $questionIndex")

        log.info("Got answer: $answer")

        person.setAnswer(questionMap.filterKeys { it == questionList[questionIndex] }.entries.first())
        IOC.put("current_answer", answer)
        IOC.put("askedQuestions", IOC.atOr("askedQuestions", 0) + 1)
        IOC.put("state", States.WITH_MAN_ANSWER)
    }

    override fun onUpdate() {
        val personTextures = IOC.atOrFail<PersonTextures>("personTextures")
        val person = IOC.atOrFail<Person>("person")
        val askedQuestions = IOC.atOr("askedQuestions", 0)

        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.ROOM), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)

            it.draw(am.at<Texture>(Assets.Names.MENU), 0f, 0f)
        }
        moneyHud(stage, hud, am)

        if (askedQuestions < maxAskedQuestions) {
            val questionMap = person.getQuestions(3)
            val questionList = questionMap.map { it.key }
            drawQuestions(questionList)
        } else {
            drawNoQuestions()
        }

        buttons.forEach { it.update() }

        val inputs = IOC.atOrFail<InputAdapterHolder>("inputs")
        if (inputs.isMouseClicked && !buttons.any { it.isClicked }) {
            IOC.put("state", States.WITH_MAN)
        }
    }

    private fun drawNoQuestions() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_SMALL_BLACK).draw(
                it,
                noQuestions,
                Const.Projection.toHud(94f),
                Const.Projection.toHud(28f)
            )
        }
    }

    private fun drawQuestions(questions: List<String>) {
        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.MENU), 0f, 0f)
        }
        var questionIndex = 0
        hud.batch.managed {
            questions.forEach { q ->
                am.at<BitmapFont>(Assets.Names.FONT_SMALL_BLACK)
                    .draw(
                        it,
                        "- ${q.insertPeriodically("\n", 40)}",
                        Const.Projection.toHud(60f),
                        Const.Projection.toHud(44f - (questionIndex * 12))
                    )
                questionIndex++
            }
        }
    }

    override fun onExit() {

    }

}
