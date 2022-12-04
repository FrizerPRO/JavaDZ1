package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    protected Integer points = 0;
    protected PlayerColor color = PlayerColor.White;

    public Player(Player player) {
        this(player.color);
        this.points = player.points;
    }

    public Player(PlayerColor color) {
        this.color = color;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public PlayerColor getColor() {
        return color;
    }

    public Coordinates getMoveCoordinates(ArrayList<Coordinates> coordinates) throws noFutherStepException, PreviousGameException {
        System.out.println("Доступные координаты:");
        if (coordinates.size() == 0) {
            throw new noFutherStepException();
        }
        for (var cell :
                coordinates) {
            System.out.print(cell.toString() + " ");
        }
        System.out.println();
        while (true) {
            System.out.println("Введите координаты точки через пробел (Или -1 для возврата на предыдущий ход):");
            try {
                Scanner in = new Scanner(System.in);
                int x = in.nextInt();
                if (x == -1) {
                    throw new PreviousGameException();
                }
                int y = in.nextInt();

                var enteredData = new Coordinates(x - 1, y - 1);
                boolean contains = false;
                if (coordinates.contains(enteredData)) {
                    return enteredData;
                }
            } catch (java.util.InputMismatchException exception) {
                System.out.println("Координаты должны соответствовать представленным");
            }
        }
    }

    @Override
    public String toString() {
        return "Player has " + points + " points";
    }
}
