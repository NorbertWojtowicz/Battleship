package battleship;

public class Submarine extends Ship {
    Submarine () {
        size = 3;
        name = "Submarine";
        hp = size;
        coords = new String[size];
    }
}
