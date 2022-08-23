import java.util.ArrayList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {
   private ArrayList<Integer> bewegen = new ArrayList<Integer> ();
   private Point[][] mat = null;


    public RowOfBowls() {

    }
    
    /**
     * Implements an optimal game using dynamic programming
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values)
    {
        // TODO
        mat = new Point[values.length][values.length];
        int sum = sum(values);
        for (int a = 1 ; a<=values.length;a++){
            for(int b = 0 ;b<values.length;b++){
                int temp = b + a -1;
                if (temp < values.length){
                if (a==1){
                    mat[b][temp] = new Point (values[b],0);
                }else {
                    int links = values[b] + mat[b + 1][temp].djat;
                    int rechts = values[temp] + mat[b][temp - 1].djat;
                    if (links > rechts) {
                        mat[b][temp] = new Point (links, mat[b + 1][temp].majt);
                    } else {
                        mat[b][temp] = new Point (rechts, mat[b][temp - 1].majt);
                    }
                  }
                }
            }
        }
        return 2*mat[0][values.length-1].majt-sum;
    }

    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */

    public int maxGainRecursive(int[] values) {
        // TODO
       int sum;
        sum = sum (values);
        return 2*maxGainRecursive (values,0,values.length-1,sum)-sum;
    }
    public int maxGainRecursive(int[] values,int a , int b,int sum) {
        // TODO
        if (b == a +1 ){
            return Math.max (values[a],values[b]);
        }
        if (b==a){
            return values[a];
        }else return Math.max ((sum-maxGainRecursive (values,a+1,b,sum-values[a])),
                (sum-maxGainRecursive (values,a,b-1,sum-values[b])));

    }


    
    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in maxGain(int values)
     * @return optimal sequence of chosen bowls (represented by the index in the values array)
     */
    public Iterable<Integer> optimalSequence()
    {
        // TODO
        int temp ,n;
        bewegen.clear ();


        if (mat!=null){ int i=0;
             n = mat[0].length-1;
             temp = mat[0].length-1;
            while (i<temp){
                if (mat[i][i].majt + mat[i+1][n].djat == mat[i][n].majt){ bewegen.add (i);
                    i++;
                }
                else{ bewegen.add (temp);
                    temp--;
                    n--;
                }
            }bewegen.add (i);
            mat = null;
        }return bewegen; }

    private int sum (int values[])
    {
        int sum = 0;
        for (int i=0; i<values.length; i++) {sum = sum + values[i];}
        return sum;
    }

    private class Point{
        int majt = 0;
        int djat = 0;
        public Point(int majt, int dajt){
            this.majt = majt;
            this.djat = dajt;
        }
    }

    public static void main(String[] args)
    {
        // For Testing
    }
}

