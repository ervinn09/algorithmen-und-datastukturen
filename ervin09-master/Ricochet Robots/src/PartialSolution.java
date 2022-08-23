import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * ParitialSolution provides at least the functionality which is required
 * for the use in searching for solutions of the game in a search tree.
 * It can store a game situation (Board) and a sequence of mooves.
 */
public class PartialSolution {
public Board board;
public LinkedList<Move> linkedM;

    /* TODO */
    /* Add object variables, constructors, methods as required and desired.      */
public PartialSolution(Board board){
    this.board = new Board (board);
    linkedM = new LinkedList<> ();
}
public PartialSolution(PartialSolution that){
    linkedM = new LinkedList<> ();
    for (Move move :
            that.linkedM) {
        this.linkedM.add(move);
    }
    board = new Board (that.board);
}

  public void doMove(Move move){
    board.doMove (move);
    linkedM.add(move);
  }
    public boolean isSol(){
        return board.targetReached ();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        PartialSolution that = (PartialSolution) o;
        return Objects.equals (board, that.board) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash (board);
    }



    /**
     * Return the sequence of moves which resulted in this partial solution.
     *
     * @return The sequence of moves.
     */
    public LinkedList<Move> moveSequence() {
        /* TODO */
        return linkedM;
    }
    public Queue<Move> validM(){
      return  board.validMoves ();
    }



    @Override
    public String toString() {
        String str = "";
        int lastRobot = -1;
        for (Move move : moveSequence()) {
            if (lastRobot == move.iRobot) {
                str += " -> " + move.endPosition;
            } else {
                if (lastRobot != -1) {
                    str += ", ";
                }
                str += "R" + move.iRobot + " -> " + move.endPosition;
            }
            lastRobot = move.iRobot;
        }
        return str;
    }
}

