package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Board.Move;
import com.mygdx.Board.TicTacToeBoard;
import com.mygdx.Board.TicTacToeEngine;
import com.mygdx.game.MainGame;
import com.mygdx.game.MainGame.GameState;

/**
 * TicTacToeScreen
 */
public class TicTacToeScreen implements Screen {
    static ShapeRenderer shapeDrawer;

    public TicTacToeBoard myBoard;
    private TicTacToeEngine engine;

    private Stage myStage;
    private int paddingX;
    private int offsetY;
    private int cellSize;
    private Vector2 beginLine;

    private int player1Wins;
    private Label player1WinsLabel;
    private int player2Wins;
    private Label player2WinsLabel;
    private Label turnLabel;

    private Table table;

    private Skin skin;

    protected int temp = 0;

    public TicTacToeScreen() {
        beginLine = new Vector2();
        shapeDrawer = new ShapeRenderer();
        myBoard = new TicTacToeBoard();
        engine = new TicTacToeEngine();

        table = new Table();
        skin = new Skin();
        myStage = new Stage();

        player1Wins = 0;
        player2Wins = 0;
        // Valores para a construção do tabuleiro
        paddingX = 150;
        offsetY = 20;
        cellSize = (MainGame.SCREEN_WIDTH - 2 * paddingX) / 3;

        // Modificar o tamanho do contentor que vai albergar os botoes para conter
        // apenas a parte de cima do ecra
        table.setBounds(0, offsetY + cellSize * 3, MainGame.SCREEN_WIDTH,
                MainGame.SCREEN_HEIGHT - cellSize * 3 - offsetY);

        // Estilo para usar com as labels do contentor table
        skin.add("default", new LabelStyle(new BitmapFont(), Color.BLACK));

        // Inicializar o botao de retorno ao menu principal
        Sprite mySprite = new Sprite(new Texture(Gdx.files.internal("TTTBoard/arrow.png")));
        mySprite.setSize(100, 100);
        ImageButton mainMenuButton = new ImageButton(new SpriteDrawable(mySprite));

        // Adicionar evento de volta ao menu principal ao botao
        mainMenuButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                MainGame.gameState = GameState.SELECTING;
            }
        });

        // Alinhamento da table
        table.center();
        table.add(mainMenuButton).padTop(50).padRight(100);

        player1WinsLabel = new Label("Player 1 Wins: " + player1Wins, skin);
        player2WinsLabel = new Label("Player 2 Wins: " + player2Wins, skin);
        turnLabel = new Label("Turn: ", skin);
        updateTurnLabel();
        // Definir tamanhos de cada coluna
        table.columnDefaults(0).width(MainGame.SCREEN_WIDTH / 3);
        table.columnDefaults(1).width(MainGame.SCREEN_WIDTH / 3);
        table.columnDefaults(2).width(MainGame.SCREEN_WIDTH / 3);

        table.add(player1WinsLabel);
        table.add(player2WinsLabel);
        table.row();
        table.add(turnLabel).padTop(50).padLeft(50);
        Label restartLabel = new Label("Press r to restart", skin);
        restartLabel.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                myBoard.restart();
            }
        });
        table.add(restartLabel);

        myStage.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (myBoard.isGameFinished()) {
                    return;
                }
                // Logic to make the player choose a move on the screen board and play it
                for (int row = 0; row < 3; row++)
                    for (int col = 0; col < 3; col++)
                        if (x >= paddingX + cellSize * col && x <= paddingX + cellSize * (col + 1)
                                && y > offsetY + cellSize * (2 - row) && y <= offsetY + cellSize * (3 - row)) {
                            myBoard.makeMove(new Move(col, row));
                        }

                // Right after the player makes its move, if engine use is set, then make the
                // engine choose a move
                if (!myBoard.isGameFinished()) {
                    if (MainGame.gameState == GameState.ENGINE_PLAYER_1 && myBoard.isPlayer1Turn()) {
                        myBoard.makeMove(engine.getNextMove(myBoard, true));
                    } else if (MainGame.gameState == GameState.ENGINE_PLAYER_2 && !myBoard.isPlayer1Turn()) {
                        myBoard.makeMove(engine.getNextMove(myBoard, false));
                    }
                }
                updateTurnLabel();

                if (myBoard.isGameFinished() && !myBoard.isGameDrawn()) {
                    if (myBoard.isPlayer1Turn()) {
                        player1Wins++;
                        player1WinsLabel.setText("Player 1 Wins: " + Integer.toString(player1Wins));
                    } else {
                        player2Wins++;
                        player2WinsLabel.setText("Player 2 Wins: " + Integer.toString(player2Wins));
                    }
                }
            };
        });
        myStage.addActor(table);

    }

    private void updateTurnLabel() {
        if (myBoard.isGameFinished()) {
            turnLabel.setText(myBoard.getGameState().toString());
            return;
        }
        if (myBoard.isPlayer1Turn())
            turnLabel.setText("Player 1 (X)");
        else
            turnLabel.setText("Player 2 (O)");
    }

    public void setInputProcessor() {
        Gdx.input.setInputProcessor(myStage);
    }

    public void render(final float delta) {
        ScreenUtils.clear(Color.WHITE);
        shapeDrawer.setColor(Color.BLACK);
        shapeDrawer.setAutoShapeType(true);
        shapeDrawer.begin(ShapeType.Filled);

        // Vertical Lines
        beginLine.set(paddingX, offsetY);
        for (int i = 0; i < 4; i++) {
            shapeDrawer.rectLine(beginLine, new Vector2(beginLine.x, offsetY + cellSize * 3), 5);
            beginLine.add(new Vector2(cellSize, 0));
        }

        // Horizontal Lines
        beginLine.set(paddingX, offsetY);
        for (int i = 0; i < 4; i++) {
            shapeDrawer.rectLine(beginLine, new Vector2(MainGame.SCREEN_WIDTH - paddingX, beginLine.y), 5);
            beginLine.add(new Vector2(0, cellSize));
        }

        beginLine.set(paddingX + 5, cellSize * 3 + offsetY - 5);
        for (int line = 0; line < 3; line++) {
            for (int col = 0; col < 3; col++) {
                if (myBoard.getSquareType(col, line) == TicTacToeBoard.SquareType.player1) {
                    shapeDrawer.setColor(Color.BLUE);
                    // Diagonal from top left to bottom right
                    shapeDrawer.rectLine(beginLine,
                            new Vector2(beginLine.x + cellSize - 10, beginLine.y - cellSize + 10), 4);

                    // Diagonal from top right to bottom left
                    shapeDrawer.rectLine(beginLine.x + cellSize - 10, beginLine.y, beginLine.x,
                            beginLine.y - cellSize + 10, 4);
                } else if (myBoard.getSquareType(col, line) == TicTacToeBoard.SquareType.player2) {
                    shapeDrawer.setColor(Color.RED);
                    shapeDrawer.circle(beginLine.x + cellSize / 2 - 5, beginLine.y - cellSize / 2 + 5,
                            cellSize / 2 - 5);
                }
                beginLine.add(cellSize, 0);
            }
            beginLine.x = paddingX + 5;
            beginLine.y -= cellSize;
        }
        shapeDrawer.end();

        this.myStage.draw();
        this.myStage.act();

        // Handle the restart functionality
        if (Gdx.input.isKeyPressed(Keys.R)) {
            myBoard.restart();
            // After restart, if engine is to use as player 1, then make the engine choose a
            // move
            if (MainGame.gameState == GameState.ENGINE_PLAYER_1) {
                myBoard.makeMove(engine.getNextMove(myBoard, true));
            }
            updateTurnLabel();
        }
    }

    public void dispose() {
        shapeDrawer.dispose();
        myStage.dispose();
    }

    @Override
    public void resize(final int width, final int height) {
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
    public void show() {
    }

    // Gets called every time the game screen is focused
    // "Resolves" the engine turn logic
    // If it is the engine turn and the engine is to be used, than play a move with
    // the engine
    public void resolveEngineTurn() {
        if (myBoard.isGameFinished()) {
            return;
        }
        if (MainGame.gameState == GameState.ENGINE_PLAYER_1 && myBoard.isPlayer1Turn()) {
            myBoard.makeMove(engine.getNextMove(myBoard, true));
        } else if (MainGame.gameState == GameState.ENGINE_PLAYER_2 && !myBoard.isPlayer1Turn())
            myBoard.makeMove(engine.getNextMove(myBoard, false));
        updateTurnLabel();
    }
}
