import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @ParameterizedTest
    @MethodSource("manhattanDataSource")
    void testManhattan(ManhattanData data) {
        Board board = data.getBoard();
        assertEquals(data.expected, board.manhattan());
    }

    public static Stream<ManhattanData> manhattanDataSource() {
        return Stream.of(new ManhattanData[]{
                new ManhattanData(2, 2, new int[]{1, 2, 3, 0}, 0), // solved game
                new ManhattanData(3, 3, new int[]{1, 2, 3, 0, 5, 6, 7, 8, 4}, 3), // Blatt
                new ManhattanData(3, 3, new int[]{1, 2, 4, 5, 3, 6, 7, 8, 0}, 6), // Blatt
                new ManhattanData(3, 3, new int[]{8, 6, 4, 5, 0, 7, 1, 2, 3}, 18) // Blatt
        });
    }

    public static class BoardData {
        private int x;
        private int y;
        private int[] field;

        public BoardData(int x, int y, int[] field) {
            this.x = x;
            this.y = y;
            this.field = field;
            assertEquals(x * y, field.length, "Filed dose not match dimensions given.");
        }

        public Board getBoard() {
            Board b = new Board(x, y);

            for (int i = 0; i < field.length; i++) {
                int posx = i % x;
                int posy = (i - posx) / y;

                b.setField(new Position(posx, posy), field[i]);

                if (field[i] == 0) {
                    try {
                        Field f = Board.class.getDeclaredField("blank");

                        f.setAccessible(true);
                        f.set(b, new Position(posx, posy));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        fail("test author fucked up.");
                    }
                }
            }

            return b;
        }
    }

    public static class ManhattanData extends BoardData {
        public int expected;

        public ManhattanData(int x, int y, int[] field, int expected) {
            super(x, y, field);
            this.expected = expected;
        }
    }
}
