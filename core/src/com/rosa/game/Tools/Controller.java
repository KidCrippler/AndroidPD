package com.rosa.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rosa.game.AndroidJDEV;
import com.rosa.game.Sprites.Player;
import com.rosa.game.screens.PlayScreen;

/**
 * Created by ROSA on 19/08/2016.
 */
public class Controller {

    Viewport viewport;
    Stage stage;
    boolean upPressed, rightPressed, leftPressed;
    OrthographicCamera cam;

    public Controller() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 480, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        final Image upConImage = new Image(new Texture("controller/jump.png"));
        upConImage.setSize(50, 50);
        upConImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        final Image rightConImage = new Image(new Texture("controller/right.png"));
        rightConImage.setSize(50, 50);
        rightConImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        final Image leftConImage = new Image(new Texture("controller/left.png"));
        leftConImage.setSize(50, 50);
        leftConImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        table.add();
        table.add(upConImage).size(upConImage.getWidth(), upConImage.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(leftConImage).size(leftConImage.getWidth(), leftConImage.getHeight());
        table.add();
        table.add(rightConImage).size(rightConImage.getWidth(), rightConImage.getHeight());
        table.row().padBottom(5);
        table.add(rightConImage).size(rightConImage.getWidth(), rightConImage.getHeight());
        table.add();

        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void resize(int width,int height){
        viewport.update(width,height);
    }
}

