package org.catinthedark.itsadeal.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import org.catinthedark.itsadeal.game.screens.GameScreen
import org.catinthedark.itsadeal.game.screens.SplashScreen
import org.catinthedark.itsadeal.game.screens.TitleScreen
import org.catinthedark.itsadeal.lib.RouteMachine

class ItsaDealGame : Game() {
    private val rm = RouteMachine()
    private lateinit var stage: Stage

    override fun create() {
        stage = Stage(
            FillViewport(
                Const.Screen.WIDTH / Const.Screen.ZOOM,
                Const.Screen.HEIGHT / Const.Screen.ZOOM,
                OrthographicCamera()
            ), SpriteBatch()
        )

        val splash = SplashScreen(stage)
        val title = TitleScreen(stage)
        val game = GameScreen(stage)

        rm.addRoute(splash) { title }
        rm.addRoute(title) { game }
        rm.addRoute(game) { title }
        rm.start(splash, Unit)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        stage.viewport.apply()
        stage.act(Gdx.graphics.deltaTime)
        rm.run(Gdx.graphics.deltaTime)

        super.render()
    }
}
