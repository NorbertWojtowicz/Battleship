package battleship;

public class Cruiser extends Ship {
    Cruiser () {
        size = 3;
        name = "Cruiser";
        hp = size;
        coords = new String[size];
    }
}
