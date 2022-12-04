package org.example;

public class Game {
    GameBoard board = new GameBoard();
    GameMode gameMode = GameMode.P2P;
    Player player1 = new Player(PlayerColor.Black);
    Player player2;
    private Boolean isFirst = true;

    public Game() {
        player2 = new Player(PlayerColor.White);
    }

    public Game(Dificulty dificulty) {
        player2 = switch (dificulty) {
            case Hard -> new ComputerDifficult(board, PlayerColor.White);
            case Light -> new ComputerLight(board, PlayerColor.White);
        };
        gameMode = switch (dificulty) {
            case Hard -> GameMode.P2CH;
            case Light -> GameMode.P2CL;
        };
    }

    public Game(Game game) {
        board = new GameBoard(game.board);
        gameMode = game.gameMode;
        player1 = new Player(game.player1);
        player2 = switch (gameMode) {
            case P2CH -> new ComputerDifficult((ComputerDifficult) game.player2);
            case P2CL -> new ComputerLight((ComputerLight) game.player2);
            case P2P -> new Player(game.player2);
        };
        isFirst = game.isFirst;
    }

    public Boolean isGoing() {
        return board.toString().contains("b") || board.toString().contains("w") || board.toString().contains("|");
    }

    public void newMove() throws PreviousGameException {
        System.out.println(board);
        if (isFirst) {
            var moves = board.getAllMoves(player1.getColor());
            try {
                var move = player1.getMoveCoordinates(moves);
                board.addNewMove(player1.getColor(), move);
            } catch (noFutherStepException ignored) {

            }
        } else {
            var moves = board.getAllMoves(player2.getColor());
            try {
                var move = player2.getMoveCoordinates(moves);
                board.addNewMove(player2.getColor(), move);
            } catch (noFutherStepException ignored) {

            }

        }
        isFirst = !isFirst;
    }

    public String getResult() {
        player2.setPoints(board.getPointsFor(player2.getColor()));
        player1.setPoints(board.getPointsFor(player1.getColor()));
        return (player1.getPoints() > player2.points ? "Black" : "White") + " WON:\n" + player1.toString() + "\n" + player2.toString();
    }
}
