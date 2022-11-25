package org.example;

import java.util.ArrayList;
//Сначала горизонтальная, потом вертикальная.
public class GameBoard {
    private ArrayList<ArrayList<Status>> currentField = new ArrayList<ArrayList<Status>>(8);
    public GameBoard(){
        for (int i = 0; i < 8; i++) {
            currentField.add(new ArrayList<Status>(8));
            for (int j = 0; j < 8; j++) {
                currentField.get(i).add(j,Status.White);
            }
        }
        currentField.get(3).set(3,Status.Black);
        currentField.get(4).set(4,Status.Black);
        currentField.get(3).set(4,Status.White);
        currentField.get(4).set(3,Status.White);
    }

}
