package org.example;

public class Coordinates {
    private int first;
    private int second;

    public Coordinates(int first, int number) {
        this.first = first;
        this.second = number;
    }

    public Coordinates(Coordinates cell) {
        this(cell.first, cell.second);
    }

    public int getSecond() {
        return second;
    }

    public int getFirst() {
        return first;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Coordinates)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Coordinates c = (Coordinates) o;

        // Compare the data members and return accordingly
        return first == c.first
                && second == c.second;
    }

    @Override
    public String toString() {
        return "(" + (first + 1) + ", " + (second + 1) + ")";
    }
}
