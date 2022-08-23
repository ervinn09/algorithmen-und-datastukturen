import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Stack;

import static java.lang.Math.abs;
/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private  int n;
    private int[][] board ;
    private int freefields;
    public int winn;
    /**
     *  Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n) {
        // TODO
        this.n = n;
        if (n <1 || n > 10) {
            throw new InputMismatchException ("input not correct");
        }

           board = new int[n][n];
           freefields = n*n;

    }
    
    /**
     *  @return     length/width of the Board object
     */
    public int getN() { return n; }
    
    /**
     *  @return     number of currently free fields
     */
    public int nFreeFields() {
        // TODO
       // int q = n*n;
        /*int temp = 0;
        for (int[] a : board) {
            for (int b : a) {
                if (b == 0) {
                    temp += 1;
                }
            }
        }
        return temp;

         */
        return freefields;
    }
    
    /**
     *  @return     token at position pos
     */
    public int getField(Position pos) throws InputMismatchException
    {
        // TODO
        if (pos.x >= n ||pos.y >= n||pos.x<0||pos.y<0){
            throw new InputMismatchException ("not right postion");
        }
        int a = board[pos.x][pos.y];
        return a ;
    }

    /**
     *  Sets the specified token at Position pos.
     */    
    public void setField(Position pos, int token) throws InputMismatchException
    {
        // TODO
        if (pos.x >= n ||pos.y >= n||pos.x<0||pos.y<0){
            throw  new InputMismatchException ("not right postion");
        }

        if (token == 0){
            if (Math.abs (getField (pos))==1)
                freefields++;

            board[pos.x][pos.y] = 0;
        }
        else if (token == 1){
            if (getField (pos)==0)
                freefields--;
            board[pos.x][pos.y] = 1;
        }else if (token == -1){
            if (getField (pos)==0)
                freefields--;
            board[pos.x][pos.y] = -1;
        } else
            throw new InputMismatchException ("nnn to");


    }
    
    /**
     *  Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player)
    {
        // TODO if (otherPlayerWon) {
        //        endGame;
        //    }
        //    doBestMove;
        int token = getField (pos);
       if (freefields == 0) throw new InputMismatchException ("full");

       if (player == 1){
           if (token ==0) setField (pos,1);
           else throw new InputMismatchException ("not empty");
       }
        else if (player == -1){
            if (token ==0) setField (pos,-1);
            else throw new InputMismatchException ("not empty");
        }else {
            throw new InputMismatchException ("not exist");
       }

    }

    /**
     *  Clears board at Position pos.
     */
    public void undoMove(Position pos)
    {
        // TODO
        setField (pos,0);
        freefields++;

    }
    
    /**
     *  @return     true if game is won, false if not
     */
    public boolean isGameWon() {
        // TODO
        int rowi = 0;
        for (int[] row : board){
            int colI = 0;
            for (int col : row) {
                Position f = new Position (rowi, colI);
                int ftok = getField (f);

                int counter = 0;
                int x = f.x;
                int y = f.y;
                if (ftok == 1 || ftok == -1) {
                    if (x == y) {
                        for (int i = 0; i < n; i++) {
                            if (board[i][i] == ftok) {
                                counter++;
                                if (counter == n) {
                                    winn = ftok;
                                    return true;
                                }
                            }
                        }
                    }
                    counter = 0;
                    for (int i = 0; i < n; i++) {
                        if (board[i][n-1-i] == ftok) {
                            counter++;
                            if (counter == n) {
                                winn = ftok;
                                return true;
                            }
                        }
                    }
                    counter = 0;
                    for (int i = 0; i < n; i++) {
                        if (board[x][i] == ftok) {
                            counter++;
                            if (counter == n) {
                                winn = ftok;
                                return true;
                            }
                        }
                    }

                    counter = 0;
                    for (int i = 0; i < n; i++) {
                        if (board[i][y] == ftok) {
                            counter++;
                            if (counter == n) {
                                winn = ftok;
                                return true;
                            }
                        }
                    }
                }
                colI++;
            }
            rowi++;
        }
        return false;
    }

    public int won(){
        int hor = 0;
        int ver = 0;
        int d1 = 0;
        int d2 = 0;
        for (int i = 0 ; i<n ;i++){
            d1 = d1 +board[i][i];
            d2 = d2 + board[i][n-1-i];
            for (int j=0;j<n;j++){
                hor = hor + board[j][i];
                ver = ver + board[i][j];
            }
            if (hor == 1 || ver == 1)
                return 1;
            if (hor == -1 || ver == -1)
                return -1;
        }
        if (d1==1||d2==1)
            return 1;
        if (d1== -1 || d2== -1)
            return -1;
        return 0;
    }



    /**
     *  @return     set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves()
    {
        // TODO

        ArrayList<Position> list = new ArrayList<Position> ();
        for (int i = 0 ; i<board.length ; i++) {
            for (int j = 0 ; j<board.length ; j++) {
                    Position p = new Position (i,j);
                    if (board[i][j] == 0){
                    list.add (p);
                    }
            }
        }
        return list;
    }

    /**
     *  Outputs current state representation of the Board object.
     *  Practical for debugging.
     */
    public void print()
    {
        // TODO


    }

}

