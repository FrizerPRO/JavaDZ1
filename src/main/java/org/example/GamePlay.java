package org.example;

import java.util.Scanner;

public class GamePlay {
    private Game game;
    private Game previousGame;

    public GamePlay() {
        game = new Game();
        previousGame = null;
    }

    public void StartGame() {
        System.out.println(getRules());
        Scanner in = new Scanner(System.in);
        do {
            initializeGame();
            Game twoGamesAgo = null;
            Game middleGame = null;
            while (game.isGoing()) {
                try {
                    game.newMove();
                    if (previousGame != null) {
                        twoGamesAgo = new Game(previousGame);
                    }
                    if (middleGame != null) {
                        previousGame = new Game(middleGame);
                    }
                    middleGame = new Game(game);
                } catch (PreviousGameException exception) {
                    if (twoGamesAgo != null) {
                        game = twoGamesAgo;
                    }
                }
            }
            System.out.println(game.board);
            System.out.println(game.getResult());
            System.out.println("Чтобы сыграть еще раз введите \"y\":\n");
        } while (in.nextLine().equals("y"));
    }

    private String getRules() {
        return "С правилами игры вы знакомы\n" +
                "Вы(первый игрок) ходите черными\n" +
                "Ваш противник(второй игрок/копьютер) - белыми\n" +
                "Откат на 1 ход назад можно осуществить с ПОСЛЕ вашего 2-го хода.\n" +
                "   Обозначения в таблице:\n" +
                "   B,W - черный,белые фишки\n" +
                "   0 - пустая клетка\n" +
                "   b,w - возможный ход для черного/белого игрока\n" +
                "   | - возможный ход для обоих";
    }

    private void initializeGame() {
        GameMode gameMode;
        int templeGameMode = -1;
        do {
            try {
                System.out.println("Выберите режим игры:\n1.Против человека;\n2.Против компа легкий;\n3.Против компа тяжелый;");
                Scanner in = new Scanner(System.in);
                templeGameMode = in.nextInt();
            } catch (java.util.InputMismatchException exception) {
                templeGameMode = 0;
            }
        } while (templeGameMode < 1 || templeGameMode > 3);
        gameMode = switch (templeGameMode) {
            case 1 -> GameMode.P2P;
            case 2 -> GameMode.P2CL;
            default -> GameMode.P2CH;
        };
        if (gameMode != GameMode.P2P) {
            game = new Game(switch (gameMode) {
                case P2CL -> Dificulty.Light;
                default -> Dificulty.Hard;
            });
        } else {
            game = new Game();
        }
    }
}
