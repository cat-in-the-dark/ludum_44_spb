package org.catinthedark.itsadeal.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.catinthedark.itsadeal.game.states.*
import org.catinthedark.itsadeal.game.testing.Autopilot
import org.catinthedark.itsadeal.lib.Deffer
import org.catinthedark.itsadeal.lib.DefferImpl
import org.catinthedark.itsadeal.lib.IOC
import org.catinthedark.itsadeal.lib.atOrFail
import org.catinthedark.itsadeal.lib.states.StateMachine

class ItsaDealGame : Game() {
    private val stage: Stage by lazy {
        Stage(
            FitViewport(
                Const.Screen.WIDTH / Const.Screen.ZOOM,
                Const.Screen.HEIGHT / Const.Screen.ZOOM,
                OrthographicCamera()
            ), SpriteBatch()
        )
    }
    private val hud: Stage by lazy {
        Stage(
            FitViewport(
                Const.Screen.WIDTH_BIG / Const.Screen.ZOOM_BIG,
                Const.Screen.HEIGHT_BIG / Const.Screen.ZOOM_BIG,
                OrthographicCamera()
            ), SpriteBatch()
        )
    }
    private val sm: StateMachine by lazy {
        StateMachine().apply {
            putAll(
                States.SPLASH_SCREEN to SplashScreenState(),
                States.TITLE_SCREEN to TitleScreenState(),
                States.STORY to StoryState(),
                States.NEW_GAME to StartNewGameState(),
                States.EMPTY_ROOM to EmptyRoomState(),
                States.WITH_MAN to WithManState(),
                States.WITH_MAN_QUESTION to WithManQuestionState(),
                States.WITH_MAN_ANSWER to WithManAnswerState(),
                States.DOCUMENT_REVIEW to DocumentReviewState(),
                States.FAIL to FailState(),
                States.PROFIT to ProfitState(),
                States.SKIP to SkipState(),
                States.BANKROT to BankrotState()
            )
            putMixin(States.EMPTY_ROOM, emptyRoomTutor)
            putMixin(States.WITH_MAN, withManTutor)
            putMixins(
                States.TITLE_SCREEN,
                States.NEW_GAME,
                States.EMPTY_ROOM,
                States.WITH_MAN,
                States.WITH_MAN_QUESTION,
                States.WITH_MAN_ANSWER,
                States.DOCUMENT_REVIEW,
                States.FAIL,
                States.PROFIT,
                States.SKIP,
                States.BANKROT
            ) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    IOC.put("state", States.TITLE_SCREEN)
                }
                IOC.atOrFail<InputAdapterHolder>("inputs").update()
                IOC.atOrFail<Autopilot>("autopilot").update()
            }
        }
    }

    override fun create() {
        IOC.put("deffer", DefferImpl())
        IOC.put("stage", stage)
        IOC.put("hud", hud)
        val inputs = InputAdapterHolder(stage)
        Gdx.input.inputProcessor = inputs
        IOC.put("inputs", inputs)
        IOC.put("autopilot", Autopilot())
        IOC.put("state", States.SPLASH_SCREEN)
    }

    override fun render() {
        val deffer: Deffer by IOC

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        stage.viewport.apply()
        stage.act(Gdx.graphics.deltaTime)
        stage.batch.projectionMatrix = stage.viewport.camera.combined

        hud.viewport.apply()
        hud.act(Gdx.graphics.deltaTime)
        hud.batch.projectionMatrix = hud.viewport.camera.combined

        deffer.update(Gdx.graphics.deltaTime)
        sm.onUpdate()
        stage.draw()
        hud.draw()

        super.render()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height)
        hud.viewport.update(width, height)
    }
}
