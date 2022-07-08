package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		MainGame myGame = new MainGame();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setResizable(false);
		config.setWindowedMode(MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
		config.setTitle("TicTacToeGraphical");
		new Lwjgl3Application(myGame, config);
	}
}
