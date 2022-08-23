/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     * @return          rating of game situation from player's point of view
    **/

    public static int alphaBeta(Board board, int player)
    {
      int alpha = - 10000;
      int beta = 10000;
        // TODO
        if (player == 1){
            alpha = spielerMAx(board,alpha,beta);
            return alpha;
        } else  if (player == -1){
            beta = spielerMin(board,alpha,beta);
            return beta;
        }
      return 0;

    }
    static int spielerMAx(Board board ,int alpha , int beta){
        if(board.isGameWon ()){
            return board.nFreeFields () + 1;
        }
        if (board.nFreeFields () == 0 ) return  0;
        for (Position move : board.validMoves ()) {
            board.doMove (move,1);
            int score = spielerMin (board,alpha,beta);
            board.undoMove (move);
            if (score > alpha){
                alpha = score;
                if (alpha>=beta) break;
            }

        }
        return alpha;
    }
    static int spielerMin(Board board ,int alpha , int beta){
        if(board.isGameWon ()){
            return -board.nFreeFields () - 1;
        }
        if (board.nFreeFields () == 0 ) return  0;
        for (Position move : board.validMoves ()) {
            board.doMove (move,-1);
            int score = spielerMAx (board,alpha,beta);
            board.undoMove (move);
            if (score < beta){
                beta = score;
                if (beta<=alpha) break;
            }

        }
        return beta;
    }



    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
    **/
    public static void evaluatePossibleMoves(Board board, int player)
    {
        // TODO

    }

    public static void main(String[] args)
    {

Board board = new Board (3);
board.setField (new Position (1,0),-1);
board.setField (new Position (2,2),1);

    }
}

