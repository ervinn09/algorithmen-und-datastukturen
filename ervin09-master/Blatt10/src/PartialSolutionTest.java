import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class PartialSolutionTest {

    @ParameterizedTest
    @MethodSource("costTestDataSource")
    public void testCost(CostTestData data) {
        assumeTrue(PartialSolutionTestInterface.class.isAssignableFrom(PartialSolution.class), "PartialSolution has to implement PartialSolutionTest.PartialSolutionTestInterface.");

        PartialSolution ps = data.getPartialSolution();
        assertEquals(data.backward + data.forward, ((PartialSolutionTestInterface) ps).cost());
    }

    @ParameterizedTest
    @MethodSource("costTestDataSource")
    public void testForwardCost(CostTestData data) {
        assumeTrue(PartialSolutionTestInterface.class.isAssignableFrom(PartialSolution.class), "PartialSolution has to implement PartialSolutionTest.PartialSolutionTestInterface.");

        PartialSolution ps = data.getPartialSolution();
        assertEquals(data.forward, ((PartialSolutionTestInterface) ps).forwardCost());
    }

    @ParameterizedTest
    @MethodSource("costTestDataSource")
    public void testBackwardCost(CostTestData data) {
        assumeTrue(PartialSolutionTestInterface.class.isAssignableFrom(PartialSolution.class), "PartialSolution has to implement PartialSolutionTest.PartialSolutionTestInterface.");

        PartialSolution ps = data.getPartialSolution();
        assertEquals(data.backward, ((PartialSolutionTestInterface) ps).backwardCost());
    }

    @ParameterizedTest
    @MethodSource("costTestDataSource")
    public void testIsSolution(CostTestData data) {
        PartialSolution ps = data.getPartialSolution();
        assertEquals(data.forward == 0, ps.isSolution());
    }

    public static Stream<CostTestData> costTestDataSource() {
        return Stream.of(new CostTestData[]{
                new CostTestData(2, 2, new int[]{1, 2, 3, 0}, null, null, null, 0, 0),
                new CostTestData(2, 2, new int[]{3, 2, 1, 0}, null, null, null, 2, 0),

                new CostTestData(2, 2, new int[]{3, 2, 1, 0}, new int[]{0}, new int[]{1}, new int[]{1}, 3, 1),
                new CostTestData(2, 2, new int[]{3, 2, 1, 0}, new int[]{0, 0}, new int[]{1, 0}, new int[]{1, 2}, 2, 2),
        });
    }

    @ParameterizedTest
    @MethodSource("moveSequenceSource")
    void testMoveSequence(PartialSolutionData data) {
        PartialSolution ps = data.getPartialSolution();

        int i = 0;
        for (Move move : ps.moveSequence()) {
            assertEquals(data.moveX[i], move.pos.x);
            assertEquals(data.moveY[i], move.pos.y);
            assertEquals(data.dir[i], move.dir);
            i++;
        }
    }

    public static Stream<PartialSolutionData> moveSequenceSource() {
        return Stream.of(new PartialSolutionData[]{
                new PartialSolutionData(2, 2, new int[]{3, 2, 1, 0}, new int[]{0}, new int[]{1}, new int[]{1}),
                new PartialSolutionData(2, 2, new int[]{3, 2, 1, 0}, new int[]{0, 0}, new int[]{1, 0}, new int[]{1, 2})
        });
    }

    @Test
    public void testValidMoveSimple() {
        PartialSolution ps = (new PartialSolutionData(2, 2, new int[]{1, 2, 3, 0}, null, null, null)).getPartialSolution();
        Iterable<Move> vm = ps.validMoves();

        int i = 0;

        for (Move ignored : vm) {
            i++;
        }

        assertEquals(2, i);
    }

    @Test
    public void testValidMoveComplex() {
        PartialSolution ps = (new PartialSolutionData(2, 2, new int[]{1, 2, 0, 3}, new int[]{1}, new int[]{1}, new int[]{3})).getPartialSolution();
        Iterable<Move> vm = ps.validMoves();

        int i = 0;

        for (Move ignored : vm) {
            i++;
        }

        assertEquals(1, i);
    }


    public static class PartialSolutionData extends BoardTest.BoardData {
        public int[] moveX;
        public int[] moveY;
        public int[] dir;

        public PartialSolutionData(int x, int y, int[] field, int[] moveX, int[] moveY, int[] dir) {
            super(x, y, field);

            if (moveX == null) {
                assertEquals(null, moveY);
                assertEquals(null, dir);
            } else {
                assertEquals(moveX.length, moveY.length);
                assertEquals(moveY.length, dir.length);
            }

            this.moveX = moveX;
            this.moveY = moveY;
            this.dir = dir;
        }

        public PartialSolution getPartialSolution() {
            PartialSolution ps = new PartialSolution(getBoard());

            if (moveX != null) {
                for (int i = 0; i < moveX.length; i++) {
                    ps.doMove(new Move(new Position(moveX[i], moveY[i]), dir[i]));
                }
            }

            return ps;
        }
    }

    public static class CostTestData extends PartialSolutionData {
        public int forward;
        public int backward;

        public CostTestData(int x, int y, int[] field, int[] moveX, int[] moveY, int[] dir, int forward, int backward) {
            super(x, y, field, moveX, moveY, dir);

            this.forward = forward;
            this.backward = backward;
        }
    }

    public interface PartialSolutionTestInterface {
        int forwardCost();

        int backwardCost();

        int cost();
    }
}
