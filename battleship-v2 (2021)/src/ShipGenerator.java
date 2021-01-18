import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class ShipGenerator {
    public static class Coord {
        private int r;
        private int c;
        public int getR() {
            return this.r;
        }
        public int getC() {
            return this.c;
        }
        private void setCoord(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coord coord = (Coord) o;
            return r == coord.r && c == coord.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    public static final int MIN_COORD = 2;

    private final int boardSize;
    private final Random rand;
    private final Set<Coord> occupiedCoords;
    private int health;

    public ShipGenerator(int boardSize) {
        this.boardSize = boardSize;
        this.rand = new Random();
        this.occupiedCoords = new HashSet<>();
    }

    public int getHealth() {
        return this.health;
    }

    // returns a set of coord for a random ship
    public Set<Coord> generateShip() {
        Set<Coord> coords = new HashSet<>();
        boolean validShip = false;

        while (!validShip) {
            int bound = -1;
            int start = 0;

            while (bound <= 0) {
                start = this.rand.nextInt(this.boardSize);
                bound = (this.boardSize - start) - MIN_COORD;
            }

            this.health = this.rand.nextInt(bound) + MIN_COORD;
            boolean isHorizontal = this.rand.nextBoolean();

            for (int i = 0; i < this.health; i++) {
                Coord coord = new Coord();
                if (isHorizontal) {
                    coord.setCoord(start, i);
                } else {
                    coord.setCoord(i, start);
                }

                if (occupiedCoords.contains(coord)) {
                    coords.clear();
                    validShip = false;
                    break;
                }

                coords.add(coord);
                validShip = true;
            }
        }

        occupiedCoords.addAll(coords);
        return coords;
    }
}
