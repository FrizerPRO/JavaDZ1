package org.example;

public class Coordinates {
    private int letterNumber;
    private int number;
    Coordinates(Character letter,int number){
        this.number = number;
        this.letterNumber = letter - 'a';
    }

    public Coordinates(int letterNumber, int number) {
        this.letterNumber = letterNumber;
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
    public char getLetter(){
        return (char)('a' + letterNumber);
    }
    public int getLetterNumber(){
        return letterNumber;
    }
}
