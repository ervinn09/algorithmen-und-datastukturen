import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

public class AStar15PuzzleTestMore extends AStar15PuzzleTest {
    static int num = 10000;
    static int[] expected = new int[num];

    static {
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream("samples/board-3x3-twosteps-ExpectedOutput.txt"));
        } catch (IOException e) {
            fail("file not found");
        }

        for (int i = 0; i < num; i++) {
            expected[i] = in.nextInt();
        }
    }

    public static Stream<AStarTestData> testSource() {

        Random rnd = new Random(187);

        AStarTestData[] rtn = new AStarTestData[num];


        for (int i = 0; i < num; i++) {
            int numMoves = rnd.nextInt(32);
            Board b = (new BoardTest.BoardData(3, 3, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0})).getBoard();
            Move lastMove = null;
            for (int j = 0; j < numMoves; j++) {
                Move[] moves = toArray(b.validMoves(lastMove));

                Move move = moves[rnd.nextInt(moves.length)];
                b.doMove(move);
                lastMove = move;
            }

            rtn[i] = new AStarTestData(3, 3, getField(b), expected[i]);
        }

        return Stream.of(rtn);
    }


    public static int[] getField(Board b) {
        try {
            Field f = Board.class.getDeclaredField("field");
            f.setAccessible(true);
            int[][] rtn = (int[][]) f.get(b);

            int[] flat = new int[rtn[0].length * rtn.length];

            int k = 0;
            for (int i = 0; i < rtn.length; i++) {
                for (int j = 0; j < rtn[i].length; j++) {
                    flat[k++] = rtn[i][j];
                }
            }

            return flat;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("test author fucked up.");
            return null;
        }
    }

    public static Move[] toArray(Iterable<Move> moves) {
        LinkedList<Move> list = new LinkedList<>();
        moves.forEach(list::addLast);
        Move[] rtn = new Move[list.size()];
        list.toArray(rtn);

        return rtn;
    }
}
