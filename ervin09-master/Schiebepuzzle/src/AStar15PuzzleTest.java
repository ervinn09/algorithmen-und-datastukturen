import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AStar15PuzzleTest {
    @ParameterizedTest
    @MethodSource("testSource")
    void test(AStarTestData data){
        PartialSolution ps = AStar15Puzzle.solveByAStar(data.getBoard());

        assertTrue(ps.isSolution());

        int i = 0;
        for(Move ignored :ps.moveSequence()){
            i++;
        }

        assertEquals(data.moves, i);
    }



    public static Stream<AStarTestData> testSource(){
        return Stream.of(new AStarTestData[]{
                new AStarTestData(3,3, new int[]{1,2,3,4,0,6,7,5,8},2),
                new AStarTestData(3,3, new int[]{1,2,3,8,5,6,4,7,0},8),
                new AStarTestData(3,3, new int[]{6,4,7,8,5,0,3,2,1},31),
        });
    }

    public static class AStarTestData extends BoardTest.BoardData {
        public int moves;
        public AStarTestData(int x, int y, int[] field, int moves) {
            super(x, y, field);
            this.moves = moves;
        }
    }
}
