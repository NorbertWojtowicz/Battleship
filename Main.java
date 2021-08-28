package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Battlefield battlefield = new Battlefield();
        for (int i = 0; i < 2; i++) {
            battlefield.addShip(ShipStaticFactory.newInstance("Aircraft Carrier"));
            battlefield.addShip(ShipStaticFactory.newInstance("Battleship"));
            battlefield.addShip(ShipStaticFactory.newInstance("Submarine"));
            battlefield.addShip(ShipStaticFactory.newInstance("Cruiser"));
            battlefield.addShip(ShipStaticFactory.newInstance("Destroyer"));
            if (i == 0) {
                battlefield.switchPlayer();
            }
        }
        battlefield.startGame();
    }
}
