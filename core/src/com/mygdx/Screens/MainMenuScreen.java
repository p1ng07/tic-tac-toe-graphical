package com.mygdx.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MainGame;
import com.mygdx.game.MainGame.GameState;

public class MainMenuScreen implements Screen {
    private Stage stage;

    public TicTacToeScreen myTicTacToeScreen;
    private MainGame.GameState gameState = GameState.SELECTING;

    public MainMenuScreen(TicTacToeScreen tttScreen) {
        this.myTicTacToeScreen = tttScreen;
        stage = new Stage();
        setInputProcessor();

        // A skin can be loaded via JSON or defined programmatically, either is fine.
        // Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a
        // drawable, tinted drawable, etc.
        Skin skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.PINK);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libGDX font under the name "default".
        skin.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored
        // by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        // Create a table that fills the screen. Everything else will go inside this
        // table.
        Table table = new Table();
        table.setFillParent(true);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be
        // used to specify a name other than "default".
        TextButton button = new TextButton("Dois jogadores", skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent e, Actor actor) {
                MainGame.gameState = GameState.TWO_PLAYERS;
            }
        });
        TextButton button2 = new TextButton("Engine is Player 1", skin);
        button2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MainGame.gameState = GameState.ENGINE_PLAYER_1;
                myTicTacToeScreen.resolveEngineTurn();
            }
        });
        TextButton button3 = new TextButton("Engine is Player 2", skin);
        button3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.gameState = GameState.ENGINE_PLAYER_2;
                myTicTacToeScreen.resolveEngineTurn();
            }
        });
        table.add(button).spaceRight(50);
        table.add(button2).spaceRight(50);
        table.add(button3);
        stage.addActor(table);
    }

    public void setInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.draw();
    }

    /** Called when this screen becomes the current screen for a {@link Game}. */
    public void show() {

    }

    /** @see ApplicationListener#resize(int, int) */
    public void resize(int width, int height) {
    }

    /** @see ApplicationListener#pause() */
    public void pause() {

    }

    /** @see ApplicationListener#resume() */
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    public void hide() {

    }

    /** Called when this screen should release all resources. */
    public void dispose() {
        stage.dispose();

    }

    public MainGame.GameState getGameState() {
        return gameState;
    }
}
