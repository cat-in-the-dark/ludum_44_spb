package org.catinthedark.itsadeal.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import org.catinthedark.itsadeal.game.screens.GameScreen
import org.catinthedark.itsadeal.game.screens.SplashScreen
import org.catinthedark.itsadeal.game.screens.TitleScreen
import org.catinthedark.itsadeal.lib.RouteMachine

class ItsaDealGame : Game() {
    private val rm = RouteMachine()
    private lateinit var stage: Stage
    private lateinit var hud: Stage

    override fun create() {
        stage = Stage(
            FitViewport(
                Const.Screen.WIDTH / Const.Screen.ZOOM,
                Const.Screen.HEIGHT / Const.Screen.ZOOM,
                OrthographicCamera()
            ), SpriteBatch()
        )
        hud = Stage(
            FitViewport(
                Const.Screen.WIDTH_BIG / Const.Screen.ZOOM_BIG,
                Const.Screen.HEIGHT_BIG / Const.Screen.ZOOM_BIG,
                OrthographicCamera()
            ), SpriteBatch()
        )

        val splash = SplashScreen(hud)
        val title = TitleScreen(hud)
        val game = GameScreen(stage, hud)

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
        stage.batch.projectionMatrix = stage.viewport.camera.combined

        hud.viewport.apply()
        hud.act(Gdx.graphics.deltaTime)
        hud.batch.projectionMatrix = hud.viewport.camera.combined

        rm.run(Gdx.graphics.deltaTime)
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
