package com.mygdx.Board;

/**
 * This class implements a fully functional tic tac toe board
 */
public class TicTacToeBoard {
    private SquareType[][] board;
    private boolean isPlayer1Turn;
    private GameState gameState;

    public TicTacToeBoard(SquareType[][] board) {
        this.board = board;
    }

    // Copy factory of object
    public static TicTacToeBoard newInstance(TicTacToeBoard board) {
        TicTacToeBoard myBoard = new TicTacToeBoard();
        SquareType[][] newPhysicalBoard = new SquareType[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                newPhysicalBoard[i][j] = board.getBoard()[i][j];
        myBoard.setPlayer1Turn(board.isPlayer1Turn());
        myBoard.gameState = board.getGameState();
        myBoard.board = newPhysicalBoard;
        return myBoard;
    }

    public TicTacToeBoard() {
        this.board = new SquareType[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                this.board[i][j] = SquareType.empty;
        this.gameState = GameState.playing;
        this.isPlayer1Turn = true;
    }

    public SquareType[][] getBoard() {
        return this.board;
    }

    public SquareType getSquareType(int col, int row) {
        return this.board[row][col];
    }

    /*
     * getCharByGameType
     *
     * @param line line on 2d array
     *
     * @param col column on 2d array
     *
     * @return Returns the String Representation of board[line][col]
     */
    private String getCharByGameType(int line, int col) {
        switch (this.board[line][col]) {
        case player1:
            return "X";
        case player2:
            return "O";
        default:
            return Integer.toString(line * 3 + col + 1);
        }
    }

    public void makeMove(Move move) {
        if (move.line < 0 || move.line > 2 || move.col < 0 || move.col > 2) {
            return;
        }
        if (this.board[move.line][move.col] != SquareType.empty)
            return;
        this.board[move.line][move.col] = this.isPlayer1Turn() ? SquareType.player1 : SquareType.player2;

        // check if the player won, if they won, change state and return, else, continue
        // normally
        if (this.isGameFinished()) {
            if (this.gameState == GameState.draw) {
                return;
            }
            this.gameState = isPlayer1Turn() ? GameState.WonPlayer1 : GameState.WonPlayer2;
            return;
        }
        // Switch player
        this.isPlayer1Turn = !this.isPlayer1Turn;
    }

    public boolean isGameDrawn() {
        if (this.gameState == GameState.draw)
            return true;
        return false;
    }

    /*
     * Go through every possible line and check if any player won
     */
    public boolean isGameFinished() {
        int numberChar = 0;
        SquareType player = this.isPlayer1Turn ? SquareType.player1 : SquareType.player2;

        for (int line = 0; line < 3; line++) {
            for (int col = 0; col < 3; col++)
                numberChar += player == this.board[line][col] ? 1 : 0;
            if (numberChar == 3) {
                if (player == SquareType.player1) {
                    this.gameState = GameState.WonPlayer1;
                } else {
                    this.gameState = GameState.WonPlayer2;
                }
                return true;
            }
            numberChar = 0;
        }

        for (int col = 0; col < 3; col++) {
            for (int line = 0; line < 3; line++)
                numberChar += player == this.board[line][col] ? 1 : 0;
            if (numberChar == 3) {
                if (player == SquareType.player1) {
                    this.gameState = GameState.WonPlayer1;
                } else {
                    this.gameState = GameState.WonPlayer2;
                }
                return true;
            }
            numberChar = 0;
        }

        if (board[0][0] == player && board[1][1] == player && player == board[2][2]) {
            if (player == SquareType.player1) {
                this.gameState = GameState.WonPlayer1;
            } else {
                this.gameState = GameState.WonPlayer2;
            }
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && player == board[2][0]) {
            if (player == SquareType.player1) {
                this.gameState = GameState.WonPlayer1;
            } else {
                this.gameState = GameState.WonPlayer2;
            }
            return true;
        }

        int emptySquares = 0;
        for (int line = 0; line < 3; line++)
            for (int col = 0; col < 3; col++)
                emptySquares += this.board[line][col] == SquareType.empty ? 1 : 0;

        if (emptySquares == 0) {
            this.gameState = GameState.draw;
            return true;
        }
        return false;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setPlayer1Turn(boolean isPlayer1Turn) {
        this.isPlayer1Turn = isPlayer1Turn;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void printBoardToConsole() {
        for (int i = 0; i < 3; i++)
            System.out.println(getCharByGameType(i, 0) + "|" + getCharByGameType(i, 1) + "|" + getCharByGameType(i, 2));
    }

    public enum SquareType {
        empty, player1, player2;
    }

    public enum GameState {
        playing, WonPlayer1, WonPlayer2, draw
    }

    public void restart() {
        this.board = new SquareType[3][3];
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                this.board[row][col] = SquareType.empty;
        this.gameState = GameState.playing;
        this.isPlayer1Turn = true;
    }
}
