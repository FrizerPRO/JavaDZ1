package org.example;

import java.util.ArrayList;

public class ComputerLight extends Computer {
    public ComputerLight(GameBoard board, PlayerColor color) {
        super(board, color);
    }

    public ComputerLight(ComputerLight player) {
        this(player.board, player.color);
        this.points = player.points;
    }

    @Override
    public Coordinates getMoveCoordinates(ArrayList<Coordinates> coordinates) throws noFutherStepException {
        Double max = 0.0;
        if (coordinates.size() == 0) {
            throw new noFutherStepException();
        }
        Coordinates result = new Coordinates(0, 0);
        for (var cell :
                coordinates) {
            boardForFuture = new GameBoard(board);
            var sum = boardForFuture.addNewMove(color, cell);
            if (sum > max) {
                max = sum;
                result = new Coordinates(cell);
            }
        }
        return result;
    }
}
