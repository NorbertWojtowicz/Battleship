package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Battlefield {
    private final char[][][] battlefield = new char[2][10][10];
    private final Ship[][] playersShips = new Ship[2][5];
    private int numOfShip = 0;
    private boolean gameEnded = false;
    private static final Scanner scanner = new Scanner(System.in);
    private boolean firstPlayer = true;
    private int currentPlayer = 1;

    Battlefield () {
        initBattlefield();
        printBattlefield();
    }

    public void printBattlefield() {
        System.out.print("  ");
        for (int i = 1; i <= battlefield[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < battlefield[0].length; i++) {
            for (int j = 0; j <= battlefield[0][i].length; j++) {
                if (j == 0) {
                    System.out.print((char) (i + 65) + " ");
                } else {
                    System.out.print(battlefield[currentPlayer - 1][i][j - 1] + " ");
                }
            }
            System.out.println();
        }
    }

    private void initBattlefield() {
        System.out.println("Player " + currentPlayer + ", place your ships on the game field");
        for (int i = 0; i < battlefield[0][0].length; i++) {
            Arrays.fill(battlefield[currentPlayer - 1][i], '~');
        }
    }

    public void addShip(Ship ship) {
        setShipCoordinates(ship);
        int[] startCoordsCopy = ship.startCoords.clone();
        while (startCoordsCopy[0] != ship.endCoords[0] || startCoordsCopy[1] != ship.endCoords[1]) {
            battlefield[currentPlayer - 1][startCoordsCopy[0]][startCoordsCopy[1]] = 'O';
            incrementCoords(startCoordsCopy, ship);
        }
        printBattlefield();
        playersShips[currentPlayer - 1][numOfShip] = ship;
        ship.fillCoords();
        numOfShip++;
        if (numOfShip == 5) {
            numOfShip = 0;
        }
    }

    private void incrementCoords(int[] coords, Ship ship) {
        if (ship.startCoords[0] > ship.endCoords[0]) {
            coords[0]--;
        } else if (ship.startCoords[0] < ship.endCoords[0]) {
            coords[0]++;
        } else if (ship.startCoords[1] > ship.endCoords[1]) {
            coords[1]--;
        } else if (ship.startCoords[1] < ship.endCoords[1]) {
            coords[1]++;
        }
    }

    private void decrementCoords(int[] coords, Ship ship) {
        if (ship.startCoords[0] > ship.endCoords[0]) {
            if (currentPlayer == 1) {
                coords[1]++;
                ship.endCoords[1]++;
            } else {
                coords[1]--;
                ship.endCoords[1]--;
            }
            ship.endCoords[0]--;
        } else if (ship.startCoords[0] < ship.endCoords[0]) {
            coords[1]--;
            ship.endCoords[1]--;
            ship.endCoords[0]++;
        }
        if (ship.startCoords[1] > ship.endCoords[1]) {
            if (coords[1] < 10) {
                coords[1]++;
            } else {
                coords[1]--;
                ship.endCoords[1] -= 2;
            }
        } else if (ship.startCoords[1] < ship.endCoords[1]) {
            coords[1]--;
        }
    }

    private void setShipCoordinates(Ship ship) {
        System.out.println("Enter coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");
        do {
            String cordStart = scanner.next();
            String cordEnd = scanner.next();
            scanner.nextLine();
            ship.startCoords = new int[] {cordStart.charAt(0) - 65, Integer.parseInt(cordStart.substring(1))};
            ship.endCoords = new int[] {cordEnd.charAt(0) - 65, Integer.parseInt(cordEnd.substring(1))};
        } while (!isShipValid(ship));
    }

    private boolean isShipValid(Ship ship) {
        if (!ship.areCoordsValid()) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        } else if (!ship.isLengthValid()) {
            System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
            return false;
        } else if (isShipPlacedOnAnotherShip(ship)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }
        return true;
    }

    private boolean isShipPlacedOnAnotherShip(Ship ship) {
        decrementCoords(ship.startCoords, ship);
        int[] startCoordsCopy = ship.startCoords.clone();
        while (startCoordsCopy[0] != ship.endCoords[0] || startCoordsCopy[1] != ship.endCoords[1]) {
            if (battlefield[currentPlayer - 1][startCoordsCopy[0]][startCoordsCopy[1]] == 'O') {
                return true;
            }
            if (startCoordsCopy[0] != 9) {
                if (battlefield[currentPlayer - 1][startCoordsCopy[0] + 1][startCoordsCopy[1]] == 'O') {
                    return true;
                }
            }
            if (startCoordsCopy[0] != 0) {
                if (battlefield[currentPlayer - 1][startCoordsCopy[0] - 1][startCoordsCopy[1]] == 'O') {
                    return true;
                }
            }
            if (startCoordsCopy[1] != 0) {
                if (battlefield[currentPlayer - 1][startCoordsCopy[0]][startCoordsCopy[1] - 1] == 'O') {
                    return true;
                }
            }
            if (startCoordsCopy[1] != 9) {
                if (battlefield[currentPlayer - 1][startCoordsCopy[0]][startCoordsCopy[1] + 1] == 'O') {
                    return true;
                }
            }
            incrementCoords(startCoordsCopy, ship);
        }
        return false;
    }

    public void startGame() {
        System.out.println("The game starts!");
        currentPlayer = 2;
        switchPlayer();
        while (!isGameEnded()) {
            int[] shotCoords;
            do {
                String shotCoordsStr = scanner.next();
                scanner.nextLine();
                shotCoords = new int[] {shotCoordsStr.charAt(0) - 65, Integer.parseInt(shotCoordsStr.substring(1))};
            } while (!areShotCoordsValid(shotCoords));
            hitBattlefield(shotCoords);
            switchPlayer();
        }
    }

    private boolean areShotCoordsValid(int[] shotCoords) {
        for (int cord : shotCoords) {
            if (cord > 10 || cord < 0) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                return false;
            }
        }
        shotCoords[1]--;
        return true;
    }

    private void hitBattlefield(int[] shotCoords) {
        int player = firstPlayer ? 1 : 0;
        if (battlefield[player][shotCoords[0]][shotCoords[1]] == 'O') {
            battlefield[player][shotCoords[0]][shotCoords[1]] = 'X';
            if (areAllShipsSank()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                gameEnded = true;
                return;
            } else {
                if (isShipSank(shotCoords)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!");
                }
            }
        } else if (battlefield[player][shotCoords[0]][shotCoords[1]] != 'X') {
            battlefield[player][shotCoords[0]][shotCoords[1]] = 'M';
            System.out.println("You missed!");
        } else {
            System.out.println("You missed!");
        }
    }

    private void printBattlefieldWithFogOfWar() {
        int player = firstPlayer ? 1 : 0;
        System.out.print("  ");
        for (int i = 1; i <= battlefield[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < battlefield[0].length; i++) {
            for (int j = 0; j <= battlefield[0][i].length; j++) {
                if (j == 0) {
                    System.out.print((char) (i + 65) + " ");
                } else {
                    if (battlefield[player][i][j - 1] == 'M' || battlefield[player][i][j - 1] == 'X') {
                        System.out.print(battlefield[player][i][j - 1] + " ");
                    } else {
                        System.out.print("~ ");
                    }
                }
            }
            System.out.println();
        }
    }

    private boolean areAllShipsSank() {
        int player = firstPlayer ? 1 : 0;
        for (int i = 0; i < battlefield[0].length; i++) {
            for (int j = 0; j < battlefield[0][i].length; j++) {
                if (battlefield[player][i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public void switchPlayer() {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        firstPlayer = !firstPlayer;
        currentPlayer = firstPlayer ? 1 : 2;
        if (battlefield[currentPlayer - 1][0][0] == '\u0000') {
            initBattlefield();
            printBattlefield();
        } else {
            printBattlefieldWithFogOfWar();
            System.out.println("---------------------");
            printBattlefield();
            System.out.println("Player " + currentPlayer + ", it's your turn:");
        }
    }

    private boolean isGameEnded() {
        return gameEnded;
    }

    private boolean isShipSank(int[] cord) {
        int player = firstPlayer ? 1 : 0;
        String cordStr = (char) (cord[0] + 65) + "" + (cord[1] + 1);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < playersShips[player][i].coords.length; j++) {
                if (playersShips[player][i].coords[j].equals(cordStr)) {
                    playersShips[player][i].hp--;
                    if (playersShips[player][i].hp == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
