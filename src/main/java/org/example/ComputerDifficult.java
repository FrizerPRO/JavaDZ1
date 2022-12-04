package org.example;

import java.util.ArrayList;

public class ComputerDifficult extends Computer {
    public ComputerDifficult(GameBoard board, PlayerColor color) {
        super(board, color);
    }

    public ComputerDifficult(ComputerDifficult player) {
        this(player.board, player.color);
        this.points = player.points;
    }

    @Override
    public Coordinates getMoveCoordinates(ArrayList<Coordinates> coordinates) throws noFutherStepException {
        Double max = Double.MIN_VALUE;
        if (coordinates.size() == 0) {
            throw new noFutherStepException();
        }

        Coordinates result = coordinates.stream().findFirst().get();
        for (var cell :
                coordinates) {
            boardForFuture = new GameBoard(board);
            var sum = boardForFuture.addNewMove(color, cell);
            var futureCoordinates = boardForFuture.getAllMoves(color);
            Double minus = 0.0;
            if (futureCoordinates.size() == 0) {
                continue;
            }
            for (var cell1 :
                    futureCoordinates) {
                var anotherBoard = new GameBoard(boardForFuture);
                minus = anotherBoard.addNewMove(color, cell1);
                if (sum - minus > max) {
                    max = sum - minus;
                    result = new Coordinates(cell);
                }
            }
        }
        return result;
    }
}
