import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

class FlowApplicationsTest {
	@ParameterizedTest
	@MethodSource("numberOfDisjointPatsDataSource")
	void testNumberOfEdgeDisjointPaths(NumberOfDisjointPatsData data) {
		assertEquals(data.expected, FlowApplications.numberOfEdgeDisjointPaths(data.getGraph(), data.from, data.to));
	}

	@ParameterizedTest
	@MethodSource("numberOfDisjointPatsDataSource")
	void testEdgeDisjointPaths(NumberOfDisjointPatsData data) {
		Graph graph = data.getGraph();

		Bag<LinkedList<Integer>> bag = FlowApplications.edgeDisjointPaths(graph, data.from, data.to);
		assertEquals(data.expected, bag.size());

		// constr. a flowNetwork containing the same edges
		FlowNetwork fn = new FlowNetwork(graph.V());
		for (int i = 0; i < graph.V(); i++) {
			for (int e : graph.adj(i)) {
				fn.addEdge(new FlowEdge(i, e, 187));
			}
		}

		HashSet<FlowEdge> used = new HashSet<>();

		int[][] paths = new int[bag.size()][];
		{
			int i = 0;
			for (LinkedList<Integer> path : bag) {
				int[] pathAr = new int[path.size()];
				int j = 0;
				for (int p : path) {
					pathAr[j++] = p;
				}
				paths[i++] = pathAr;
			}
		}

		for (LinkedList<Integer> path : bag) {

			assertEquals(data.from, path.get(0));
			assertEquals(data.to, path.get(path.size() - 1));

			for (int i = 1; i < path.size(); i++) {
				int self = path.get(i);
				int prev = path.get(i - 1);

				// search for an unused edge between self and prev.
				FlowEdge took = null;
				for (FlowEdge fe : fn.adj(self)) {
					if (!used.contains(fe) && fe.to() == self && fe.from() == prev) {
						took = fe;
						break;
					}
				}
				if (took == null) {
					for(int[] ar : paths){
						System.out.println(Arrays.toString(ar));
					}
					fail("One of your paths reused an edged. \n");
				}
				used.add(took);
			}
		}
	}

	public static Stream<NumberOfDisjointPatsData> numberOfDisjointPatsDataSource() {
		List<NumberOfDisjointPatsData> rtn = new LinkedList<>();
		int[] expected = new int[]{2, 2, 3, 3, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 3, 2, 3, 2, 3, 3, 2, 2, 3, 2, 3, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 3, 3, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 3, 3, 2, 3, 2};

		loadGraphCasesNum("Graph1.txt", 9, rtn, expected);

		NumberOfDisjointPatsData[] ar = new NumberOfDisjointPatsData[rtn.size()];
		rtn.toArray(ar);

		return Stream.of(ar);
	}

	public static void loadGraphCasesNum(String filename, int numOfV, List<NumberOfDisjointPatsData> into, int[] expected){
		int i = 0;
		for (int from = 0; from < numOfV; from++) {
			for (int to = 0; to < numOfV; to++) {
				if(from != to) {
					into.add(new NumberOfDisjointPatsData(filename, from, to, expected[i++]));
				}
			}
		}
	}


	@ParameterizedTest
	@MethodSource("uniqueGraphDataSource")
	void testIsUnique(UniqueGraphData data) {
		boolean actual = FlowApplications.isUnique(data.getFlowNetwork(), data.from, data.to);
		assertEquals(data.unique, actual);
	}


	public static Stream<UniqueGraphData> uniqueGraphDataSource() {
		return Stream.of(new UniqueGraphData[]{
				new UniqueGraphData("Flussgraph1.txt", 0, 5, false), // blatt bsp
				new UniqueGraphData("Flussgraph2.txt", 0, 5, true) // blatt bsp
		});
	}

	@ParameterizedTest
	@MethodSource("bottleneckDataSource")
	void testFindBottlenecks(BottleneckData data) {
		LinkedList<Integer> necks = FlowApplications.findBottlenecks(data.getFlowNetwork(), data.from, data.to);

		assertEquals(data.expected.length, necks.size(), "size differs.");

		for (int i = 0; i < data.expected.length; i++) {
			assertTrue(necks.contains(data.expected[i]));
		}
	}

	public static Stream<BottleneckData> bottleneckDataSource() {
		return Stream.of(new BottleneckData[]{
				new BottleneckData("Flussgraph2.txt", 0, 5, new int[]{2, 1})
		});
	}

	static class GraphTestData {
		private String filename;

		public GraphTestData(String filename) {
			this.filename = filename;
		}

		public Graph getGraph() {
			return new Graph(new In(filename));
		}

		public FlowNetwork getFlowNetwork() {
			return new FlowNetwork(new In(filename));
		}
	}

	static class FromToTestData extends GraphTestData {
		public int from;
		public int to;

		public FromToTestData(String filename, int from, int to) {
			super(filename);
			this.from = from;
			this.to = to;
		}
	}

	static class NumberOfDisjointPatsData extends FromToTestData {

		public int expected;

		public NumberOfDisjointPatsData(String filename, int from, int to, int expected) {
			super(filename, from, to);
			this.expected = expected;
		}
	}

	static class UniqueGraphData extends FromToTestData {
		public boolean unique;

		public UniqueGraphData(String filename, int from, int to, boolean unique) {
			super(filename, from, to);
			this.unique = unique;
		}
	}

	static class BottleneckData extends FromToTestData {
		public int[] expected;

		public BottleneckData(String filename, int from, int to, int[] expected) {
			super(filename, from, to);
			this.expected = expected;
		}
	}
}

