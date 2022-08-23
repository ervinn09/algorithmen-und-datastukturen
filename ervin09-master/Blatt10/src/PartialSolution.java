import java.util.LinkedList;

/**
 * PartialSolution is a class which represents a state of the game
 * from its initial state to its solution. It includes the current
 * state of the board and the move sequence from the initial state
 * to the current state.</br>
 * For the use in the A*-algorithm, the class implements Comparable
 * wrt the cost of the number of mov es + heuristic.</br>
 * For the heuristic and game functionality, the respective methods
 * of class {@link Board} are used.
 */
public class PartialSolution implements  Comparable<PartialSolution>,PartialSolutionTest.PartialSolutionTestInterface {

    public LinkedList<Move> linkedM;
   public Board board;
    public int c = 0 ;
    /**
     * Constructor, generates an empty solution based on the provided
     * <em>board</em> with an empty move sequence.
     *
     * @param board initial state of the board
     */
    public PartialSolution(Board board) {
        // TODO 1.2 PartialSolution(board)
        this.board = new Board (board);
        this.c = board.manhattan ();
        this.linkedM = new LinkedList<Move> ();

    }

    /**
     * Copy constructor, generates a deep copy of the input
     *
     * @param that The partial solution that is to be copied
     */
    public PartialSolution(PartialSolution that) {
        // TODO 1.2 PartialSolution(PartialSolution)
        this(that.board);
        this.c = that.c;
        for (Move move: that.linkedM) {
            this.linkedM.add (move);
        }
    }

    /**
     * Performs a move on the board of the partial solution and updates
     * the cost.
     * 
     * @param move The move that is to be performed
     */
    public void doMove(Move move) {
        // TODO 1.2 doMove
        this.board.doMove (move);
        this.linkedM.add (move);
        int t1 =  this.linkedM.size () ;
        int t2 = this.board.manhattan ();
        this.c = t1 + t2;

    }

    /**
     * Tests whether the solution has been reached, i.e. whether
     * current board is in the goal state.
     *
     * @return {@code true}, if the board is in goal state
     */
    public boolean isSolution() {
        // TODO 1.2 isSolution
        return this.board.isSolved ();
    }

    /**
     * Return the sequence of moves which leads from the initial board
     * to the current state.
     *
     * @return move sequence leading to this state of solution
     */
    public Iterable<Move> moveSequence() {
        // TODO 1.2 moveSequence
        return linkedM ;
    }

    /**
     * Generates all possible moves on the current board, <em>except</em>
     * the move which would undo the previous move (if there is one).
     * 
     * @return moves to be considered in the current situation
     */
    public Iterable<Move> validMoves() {
        // TODO 1.2 validMoves

        if (linkedM.isEmpty ()){
         return this.board.validMoves ();
        }
        return this.board.validMoves (linkedM.getLast ());
    }

    /**
     * Compares partial solutions based on their cost.
     * (For performance reasons, the costs should be pre-calculated
     * and stored for each partial solution, rather than computed
     * here each time anew.)
     *
     * @param that the other partial solution
     * @return result of cost comparistion between this and that
     */
    public int compareTo(PartialSolution that) {
        // TODO 1.2 compareTo
        int temp  = this.c - that.c ;
        if(temp>0){
            return  1;
        }
        if(temp<0){
            return  -1;
        }else
        {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder("Partial solution with moves: \n");
        for (Move move : moveSequence()) {
            msg.append(move).append(", ");
        }
        return msg.substring(0, msg.length() - 2);
    }


    public static void main(String[] args) {
        String filename = "samples/board-3x3-twosteps.txt";
        Board board = new Board(filename);
        PartialSolution psol = new PartialSolution(board);
        psol.doMove(new Move(new Position(1, 2), 0));
        psol.doMove(new Move(new Position(2, 2), 3));
        AStar15Puzzle.printBoardSequence(board, psol.moveSequence());
    }

    @Override
    public int forwardCost() {
        return board.manhattan ();
    }

    @Override
    public int backwardCost() {
        return  linkedM.size ();
    }

    @Override
    public int cost() { return board.manhattan () + linkedM.size ();
    }
}

