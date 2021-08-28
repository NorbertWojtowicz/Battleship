package battleship;

public class Battleship extends Ship {
    Battleship() {
        size = 4;
        name = "Battleship";
        hp = 4;
        coords = new String[size];
    }
}
