package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.exceptions.InvalidAnswerException
import org.catinthedark.itsadeal.game.questionary.PersonFactory
import org.catinthedark.itsadeal.lib.managed
import org.slf4j.LoggerFactory

class WithManState(
    private val stage: Stage,
    private val hud: Stage,
    private val am: AssetManager
) : IState {
    private val log = LoggerFactory.getLogger(WithManState::class.java)

    private val noQuestions = "Вопросов больше нет."

    private var personTextures: PersonTextures = RandomPersonTextures()
    private var person = PersonFactory().getRandomPerson()
    private val currentAnswerKey = "current_answer"

    var askedQuestions = 0
    val maxAskedQuestions = 3

    override fun onActivate() {

    }

    override fun onUpdate() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //TODO: move to onActivate (or in the IOC)
            personTextures = RandomPersonTextures()
            person = PersonFactory().getRandomPerson()
        }
        if (IOC.at<InputAdapterHolder>("inputs")?.isMouseClicked == true) {
            IOC.put("state", States.DOCUMENT_REVIEW)
        }

        stage.batch.managed {
            it.draw(am.at<Texture>(personTextures.body), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.STOL), 0f, 0f)
            it.draw(am.at<Texture>(Assets.Names.RUKI), 0f, 0f)
            it.draw(am.at<Texture>(personTextures.golova), 0f, 0f)

            it.draw(am.at<Texture>(personTextures.faces), 0f, 0f) // TODO: make kivok

            it.draw(am.at<Texture>(personTextures.shlapa), 0f, 0f)
        }

        if (askedQuestions < maxAskedQuestions) {
            val questionMap = person.getQuestions(3)
            val questionList = questionMap.map { it.key }
            drawQuestions(questionList)

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)
                || Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)
                || Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)
            ) {
                askedQuestions++
                val questionIndex: Int = when {
                    Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) -> 0
                    Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) -> 1
                    else -> 2
                }

                val answer: String =
                    questionMap[questionList[questionIndex]]
                        ?: throw InvalidAnswerException("No answer at $questionIndex")

                log.info("Got answer: $answer")

                person.setAnswer(questionMap.filterKeys { it == questionList[questionIndex] }.entries.first())
                IOC.put(currentAnswerKey, answer)
            }
        } else {
            drawNoQuestions()
        }

        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, IOC.atOr(currentAnswerKey, ""), 0f, Const.Projection.toHud(10f))
        }
    }

    private fun drawNoQuestions() {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, noQuestions, 0f, Const.Projection.toHud(130f))
        }
    }

    private fun drawQuestions(questions: List<String>) {
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, questions[0], 0f, Const.Projection.toHud(130f))
            am.at<BitmapFont>(Assets.Names.FONT_SMALL)
                .draw(it, questions[1], 0f, Const.Projection.toHud(120f))
            am.at<BitmapFont>(Assets.Names.FONT_BIG)
                .draw(it, questions[2], 0f, Const.Projection.toHud(110f))
        }
    }

    override fun onExit() {

    }

}
