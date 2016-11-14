package com.rosa.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rosa.game.Application;
import com.rosa.game.screens.PlayScreen;

public class SplashScreen implements Screen {

    private Texture texture = new Texture(Gdx.files.internal("style/menu/splash/splash_srn.png"));
    private Image splashImage = new Image(texture);
    private Stage stage = new Stage();
    private Application game;
    PlayScreen playScreen;

    public SplashScreen (Application game,PlayScreen playScreen){
        this.game = game;
        this.playScreen = playScreen;
    }


    @Override
    public void show() {
        stage.addActor(splashImage);
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1.0f), Actions.delay(1), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game,playScreen));
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
    }
}
