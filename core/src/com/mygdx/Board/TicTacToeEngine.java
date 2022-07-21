package com.mygdx.Board;

import java.util.Vector;

import com.mygdx.Board.TicTacToeBoard.GameState;
import com.mygdx.Board.TicTacToeBoard.SquareType;

/**
 * TicTacToeEngine
 */
public class TicTacToeEngine {
    private Move nextMove = new Move(0, 0);

    public Move getNextMove(TicTacToeBoard board, boolean isPlayer1Turn) {
        minimax(TicTacToeBoard.newInstance(board), isPlayer1Turn);
        return nextMove;

    }

    /*
     * Return the next move in (column, row) format
     *
     * @param board, the current tic tac toe board
     */
    public int minimax(TicTacToeBoard board, boolean max) {
        if (board.isGameFinished())
            if (board.getGameState() == GameState.WonPlayer1)
                return 1;
            else if (board.getGameState() == GameState.WonPlayer2)
                return -1;
            else if (board.getGameState() == GameState.draw)
                return 0;

        Vector<Move> possibleMoves = getAllPossibleMoves(board);
        Vector<Integer> possibleMovesValues = new Vector<Integer>();
        if (max) {
            // Traverse all possible moves of the current move and save all of the values
            for (int i = 0; i < possibleMoves.size(); i++) {
                TicTacToeBoard newBoard = TicTacToeBoard.newInstance(board);
                newBoard.makeMove(possibleMoves.get(i));
                possibleMovesValues.add(minimax(newBoard, false));
            }
            int maxValue = -9999;
            // Determine the move to be played by the maximizing player
            for (int i = 0; i < possibleMoves.size(); i++) {
                if (possibleMovesValues.get(i) > maxValue) {
                    maxValue = possibleMovesValues.get(i);
                    nextMove = possibleMoves.get(i);
                }
            }
            return maxValue;
        } else {
            // Traverse all possible moves of the current move and save all of the values
            for (int i = 0; i < possibleMoves.size(); i++) {
                TicTacToeBoard newBoard = TicTacToeBoard.newInstance(board);
                newBoard.makeMove(possibleMoves.get(i));
                possibleMovesValues.add(minimax(newBoard, true));
            }
            int minValue = 9999;
            // Determine the move to be played by the minimizing player
            for (int i = 0; i < possibleMoves.size(); i++) {
                if (possibleMovesValues.get(i) < minValue) {
                    minValue = possibleMovesValues.get(i);
                    nextMove = possibleMoves.get(i);
                }
            }
            return minValue;
        }
    }

    public Vector<Move> getAllPossibleMoves(TicTacToeBoard board) {
        Vector<Move> returnVector = new Vector<Move>();
        for (int col = 0; col < 3; col++) {
            for (int line = 0; line < 3; line++) {
                if (board.getBoard()[line][col] == SquareType.empty) {
                    returnVector.add(new Move(col, line));
                }
            }
        }
        return returnVector;
    }
}
