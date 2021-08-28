package battleship;

public class AircraftCarrier extends Ship {
    AircraftCarrier () {
        size = 5;
        name = "Aircraft Carrier";
        hp = size;
        coords = new String[size];
    }
}
