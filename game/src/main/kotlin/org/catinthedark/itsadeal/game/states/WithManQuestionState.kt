package org.catinthedark.itsadeal.game.states

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.itsadeal.game.*
import org.catinthedark.itsadeal.game.Assets.Names.FONT_SMALL_BLACK
import org.catinthedark.itsadeal.game.Assets.Names.FONT_SMALL_GRAY
import org.catinthedark.itsadeal.game.Const.Balance.maxAskedQuestions
import org.catinthedark.itsadeal.game.exceptions.InvalidAnswerException
import org.catinthedark.itsadeal.game.questionary.Person
import org.catinthedark.itsadeal.game.questionary.insertPeriodically
import org.catinthedark.itsadeal.game.texts.Texts
import org.catinthedark.itsadeal.game.ui.Button
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOr
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.managed
import org.catinthedark.itsadeal.lib.states.IState
import org.slf4j.LoggerFactory

class WithManQuestionState : IState {
    private val stage: Stage by lazy { IOC.atOrFail<Stage>("stage") }
    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private val log = LoggerFactory.getLogger(WithManState::class.java)
    private val fontColors: MutableMap<Int, String> = hashMapOf(
        0 to FONT_SMALL_BLACK,
        1 to FONT_SMALL_BLACK,
        2 to FONT_SMALL_BLACK
    )
    private val buttons = listOf(
        Button(60, 33, 190, 44, onClick = {
            // First
            answer(0)
        }, onHover = { fontColors[0] = FONT_SMALL_GRAY }),
        Button(60, 21, 190, 32, onClick = {
            // Second
            answer(1)
        }, onHover = { fontColors[1] = FONT_SMALL_GRAY }),
        Button(60, 8, 190, 20, onClick = {
            // Third
            answer(2)
        }, onHover = { fontColors[2] = FONT_SMALL_GRAY })
    )

    override fun onActivate() {

    }

    private fun answer(questionIndex: Int) {
        val askedQuestions: Int by IOC
        if (askedQuestions >= maxAskedQuestions) return

        val person = IOC.atOrFail<Person>("person")
        val questionMap = person.getQuestions(3)
        val questionList = questionMap.map { it.key }

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

        fontColors.replaceAll { _, _ -> FONT_SMALL_BLACK }
        buttons.forEach { it.update() }

        val inputs = IOC.atOrFail<InputAdapterHolder>("inputs")
        if (inputs.isMouseClicked && !buttons.any { it.isClicked }) {
            IOC.put("state", States.WITH_MAN)
        }
    }

    private fun drawNoQuestions() {
        val txt: Texts by IOC
        hud.batch.managed {
            am.at<BitmapFont>(Assets.Names.FONT_SMALL_BLACK).draw(
                it,
                txt.noQuestions,
                Const.Projection.toHud(94f),
                Const.Projection.toHud(28f)
            )
        }
    }

    private fun drawQuestions(questions: List<String>) {
        stage.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.MENU), 0f, 0f)
        }
        hud.batch.managed {
            questions.forEachIndexed { questionIndex, q ->
                am.font(fontColors.getOrDefault(questionIndex, FONT_SMALL_BLACK))
                    .draw(
                        it,
                        "- ${q.insertPeriodically("\n", 40)}",
                        Const.Projection.toHud(60f),
                        Const.Projection.toHud(44f - (questionIndex * 12))
                    )
            }
        }
    }

    override fun onExit() {

    }

}
