import java.util.Objects;

public class Move {
    public final int r;
    public final int c;
    private final int boardSize;

    public Move(String move, int boardSize) {
        this.boardSize = boardSize;
        this.c = convertCol(
                Character.toUpperCase(move.charAt(0)));
        this.r = convertRow(move.charAt(1));
    }

    private int convertRow(char r) {
        int row = (this.boardSize - 1) - (r - '0');
        if (row < 0 || row > this.boardSize - 1) {
            throw new IllegalArgumentException("Invalid move");
        }

        return row;
    }

    private int convertCol(char c) {
        int col = c - 65; // ascii for 'A'
        if (col < 0 || col > this.boardSize - 1) {
            throw new IllegalArgumentException("Invalid move");
        }

        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Move move = (Move) o;
        return r == move.r && c == move.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}