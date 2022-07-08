package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Screens.MainMenuScreen;
import com.mygdx.Screens.TicTacToeScreen;

public class MainGame extends Game {
	static ShapeRenderer shapeDrawer;

	static OrthographicCamera camera;
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 800;

	public static GameState gameState = GameState.SELECTING;
	public MainMenuScreen myMainMenuScreen;
	TicTacToeScreen myTicTacToeScreen;

	public void render() {
		ScreenUtils.clear(Color.BLUE);
		switch (gameState) {
		case SELECTING:
			myMainMenuScreen.setInputProcessor();
			setScreen(myMainMenuScreen);
			break;
		case TWO_PLAYERS:
			myTicTacToeScreen.setInputProcessor();
			setScreen(myTicTacToeScreen);
			break;
		default:
			myTicTacToeScreen.setInputProcessor();
			setScreen(myTicTacToeScreen);
			break;
		}

		getScreen().render(Gdx.graphics.getDeltaTime());

	}

	public void dispose() {
		shapeDrawer.dispose();
	}

	public void create() {
		shapeDrawer = new ShapeRenderer();
		myTicTacToeScreen = new TicTacToeScreen();
		myMainMenuScreen = new MainMenuScreen(myTicTacToeScreen);
		gameState = GameState.SELECTING;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

	}

	public enum GameState {
		SELECTING, TWO_PLAYERS, ENGINE_PLAYER_1, ENGINE_PLAYER_2
	}
}
