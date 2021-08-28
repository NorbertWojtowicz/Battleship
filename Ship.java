package battleship;

public abstract class Ship {
    protected int size;
    protected int[] startCoords;
    protected int[] endCoords;
    protected String name;
    String[] coords;
    protected int hp;

    protected void setStartCoords(int[] startCoords) {
        this.startCoords = startCoords.clone();
    }

    protected int[] getStartCoords() {
        return startCoords.clone();
    }

    protected void setEndCoords(int[] endCoords) {
        this.endCoords = endCoords.clone();
    }

    protected int[] getEndCoords() {
        return endCoords.clone();
    }

    protected int getSize() {
        return size;
    }

    protected String getName() {
        return name;
    }

    protected boolean isLengthValid() {
        int length = 1;
        if (endCoords[0] == startCoords[0]) {
            length += Math.abs(endCoords[1] - startCoords[1]);
        } else {
            length += Math.abs(endCoords[0] - startCoords[0]);
        }
        return length == size;
    }

    protected boolean areCoordsValid() {
        if (startCoords[0] != endCoords[0] && startCoords[1] != endCoords[1]) {
            return false;
        }
        return true;
    }

    protected void fillCoords() {
        if (startCoords[0] == endCoords[0]) {
            if (startCoords[1] > endCoords[1]) {
                for (int i = startCoords[1]; i > endCoords[1]; i--) {
                    coords[i - endCoords[1] - 1] = (char) (startCoords[0] + 65) + "" + i;
                }
            } else {
                for (int i = startCoords[1]; i < endCoords[1]; i++) {
                    coords[i - startCoords[1]] = (char) (startCoords[0] + 65) + "" + i;
                }
            }
        } else {
            if (startCoords[0] < endCoords[0]) {
                for (int i = startCoords[0]; i < endCoords[0]; i++) {
                    coords[i - startCoords[0]] = (char) (i + 65) + "" + (startCoords[1] + 1);
                }
            } else {
                for (int i = startCoords[0]; i > endCoords[0]; i--) {
                    coords[i - endCoords[0] - 1] = (char) (i + 65) + "" + (startCoords[1] + 1);
                }
            }
        }
    }
}
