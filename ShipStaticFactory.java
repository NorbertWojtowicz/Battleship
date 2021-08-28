package battleship;

class ShipStaticFactory {
    public static Ship newInstance(String type) {
        if (type.equals("Aircraft Carrier")) {
            return new AircraftCarrier();
        } else if (type.equals("Battleship")) {
            return new Battleship();
        } else if (type.equals("Submarine")) {
            return new Submarine();
        } else if (type.equals("Cruiser")) {
            return new Cruiser();
        } else if (type.equals("Destroyer")) {
            return new Destroyer();
        } else {
            throw new IllegalArgumentException("Wrong ship type");
        }
    }
}
