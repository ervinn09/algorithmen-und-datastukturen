import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=0;
	
	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}
	

	@Test
	void testPermutation() {
		initialize();
		// TODO
		assertNotNull (n1);
		assertNotNull (n2);
		if (p1 != null){
		assertEquals (n1,p1.original.length);
		assertTrue (p1.allDerangements.isEmpty ());
		assertTrue (noCode_Vordoppelt (p1.original));
	   }
		if (p2 != null){
			assertEquals (n2,p2.original.length);
			assertTrue (p2.allDerangements.isEmpty ());
			assertTrue (noCode_Vordoppelt (p2.original));
		}
	}

	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
        p1.derangements ();
        p2.derangements ();
        assertTrue (fixedPoint (p1.original, p1.allDerangements),"p1 has fixed Point");
		assertTrue (fixedPoint (p2.original, p2.allDerangements),"p2 has fixed Point");
		assertTrue (p1.allDerangements.size ()==countDer (p1.original.length)&& dersnoduplicate (p1.allDerangements));
		assertTrue (p2.allDerangements.size ()==countDer (p2.original.length)&& dersnoduplicate (p2.allDerangements));


	}
	
	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
		p1.derangements ();
		p2.derangements ();
		assertNotEquals (0,p1.allDerangements.size ());
		assertNotEquals (0,p2.allDerangements.size ());
        assertTrue (permut (p1.original,p1.allDerangements));
        assertTrue (permut (p2.original,p2.allDerangements));

	}
	
	void setCases(int c) {
		this.cases=c;
	}
	
	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;
		
		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}

	public boolean notfix(int[] orig , int [] ders ){
		if(orig.length != ders.length )
			return false;
		for (int temp = 0 ;temp < orig.length; temp++  ){
			if(orig[temp] == ders[temp]){
			return false;
			}
		}
		return true;
	}
	public boolean fixedPoint(int[] orig , LinkedList<int []> allders){
		for (int[] temp : allders){
			if(Arrays.equals (orig,temp)){
				return false;
			}
		}return true;
	}

	public boolean permut(int[] orig,LinkedList<int[]> allderrangement){
		       // speichere original in eine temp array
		int temp[] = orig.clone ();
		Arrays.sort (temp);

		for (int[] a: allderrangement) {
			if (Arrays.equals (orig, a) ){ return false; }// wenn orig gleich mit dem Element ist dann false
			else {
				Arrays.sort (a);
				if (!Arrays.equals (a, temp)) {
					return false;
				}// wenn sortierte orig nicht gleich mit dem Elem ist dann false
			}
		}return true;
	}

	public boolean noCode_Vordoppelt(int[] orig){
		for (int x = 0;x<orig.length;x++){
			for (int y =x +1;y<orig.length;y++){
				if(orig[x] == orig[y])
				   return false;
			}
		}return true;
	}

	public static int countDer(int n)
	{
		if(n == 1 || n == 2) {
			return n-1;
		}
		int a = 0;
		int b = 1;
		for (int i = 3; i <= n; ++i) {
			int cur = (i-1)*(a+b);
			a = b;
			b = cur;
		}
		return b;
	}


	public boolean dersnoduplicate(LinkedList<int[]> allders){
		int [] orig = new int[0];
			for (int[] t: allders) {
				if (orig != null){
				if (orig ==  t) {
					return false;
				}
				}orig = t;
			}return true;
		}
/*
	public long derrrr(int n ){
		if(n==0)
			return 0;
		int sum = 0;
		int q = 0;
		for (int i = 0; i <= n; i++) {
			 q = (int) (Math.pow(-1,i)/factorial (i));
            sum += q;
		}
		return factorial (n) * sum;
	}
	public long factorial(int n) {
		long fact = 1;
		for (int i = 1; i <= n; i++) {
			fact = fact * i;
		}
		return fact;
	}

 */
}


