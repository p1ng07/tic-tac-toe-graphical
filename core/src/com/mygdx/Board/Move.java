package com.mygdx.Board;

public class Move {
    public int col;
    public int line;

    public Move(int col, int line) {
        this.col = col;
        this.line = line;
    }

    public Move newInstance() {
        return new Move(this.col, this.line);
    }

    public void print() {
        System.out.println("col =" + this.col + ",line =" + this.line);
    }
}
