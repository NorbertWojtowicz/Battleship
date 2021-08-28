package battleship;

public class Destroyer extends Ship {
    Destroyer() {
        size = 2;
        name = "Destroyer";
        hp = size;
        coords = new String[size];
    }
}
