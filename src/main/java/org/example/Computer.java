package org.example;

public abstract class Computer extends Player {
    protected GameBoard board;
    protected GameBoard boardForFuture;

    public Computer(GameBoard board, PlayerColor color) {
        super(color);
        this.board = board;
    }

    @Override
    public String toString() {
        return "Computer has " + points + " points";
    }
}
