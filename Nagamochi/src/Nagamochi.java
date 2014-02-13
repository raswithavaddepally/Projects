import java.util.Arrays;
import java.util.Random;

public class Nagamochi {

	static int[] maximumArray;

	/* Create a Random Graph */
	public int[][] drawGraph(int nodes, int edges) {

		int[][] graphMatrixValues = new int[nodes][nodes];
		Random r = new Random();
		int count = 0;

		while (count < edges) {

			int node1 = r.nextInt(nodes);
			int node2 = r.nextInt(nodes);

			if (node1 != node2 && graphMatrixValues[node1][node2] != 1) {
				graphMatrixValues[node1][node2] = 1;
				graphMatrixValues[node2][node1] = 1;
				count++;
			}
		}
		return graphMatrixValues;
	}

	// Maximum Degree of all Nodes
	public int[] maxArray(int graphMatrixValues[][], int maxAdjacentOrder[]) {

		int nodes = graphMatrixValues.length;
		int[] maxArray = new int[nodes];

		for (int i = 0; i < maxAdjacentOrder.length; i++) {
			if (maxAdjacentOrder[i] != 0) {
				int in = maxAdjacentOrder[i];
				maxArray[in - 1] = -2;
			}
		}
		for (int i = 0; i < maxAdjacentOrder.length; i++) {
			if (maxAdjacentOrder[i] != 0) {
				for (int k = 0; k < nodes; k++) {
					if (graphMatrixValues[k][maxAdjacentOrder[i] - 1] == 1) {
						if (maxArray[k] != -2)
							maxArray[k]++;
					}
				}
			}
		}
		return maxArray;
	}

	// find Maximum in the array
	public int maxIndex(int array[]) {
		int maxIndex = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > array[maxIndex] && array[i] != -2)
				maxIndex = i;
		}
		return maxIndex;
	}

	// Maximum Adjacency Order
	public int[] maxOrder(int graphMatrix[][]) {

		int nodes = graphMatrix.length;
		int[] maxAdjacentOrder = new int[nodes];
		Random r = new Random();
		int firstNode = r.nextInt(nodes);
		if (firstNode == 0)
			firstNode = 1;
		maxAdjacentOrder[0] = firstNode;
		int count = 1;

		while (count < nodes) {
			maximumArray = maxArray(graphMatrix, maxAdjacentOrder);
			int node = maxIndex(maximumArray);
			node++;
			maxAdjacentOrder[count] = node;
			count++;
		}
		return maxAdjacentOrder;
	}

	/* Degree of a vertex in a Graph */
	public int vDegree(int[][] graphMatrix, int vertexIndex) {
		int vertex = 0;
		for (int i = 0; i < graphMatrix.length; i++) {
			if (graphMatrix[vertexIndex][i] != 0) {
				vertex++;
			}
		}
		return vertex;
	}

	/* Edge Connectivity(Minimum Cut) of Graph */
	public int minimumCut(int graphMatrixValues[][], int maxAdjacentOrder[]) {

		int minCut = 0;
		int nodes = graphMatrixValues.length;

		if (nodes > 1) {
			int[] vertex = new int[nodes - 1];

			int[][] AGraph = new int[nodes][nodes];
			for (int i = 0; i < nodes; i++) {
				for (int j = 0; j < nodes; j++) {
					AGraph[i][j] = graphMatrixValues[i][j];
				}
			}

			for (int count = 0; count < nodes - 1; count++) {
				int last = maxAdjacentOrder[(nodes - 1) - count];
				int lVertex = maxAdjacentOrder[(nodes - 2) - count];

				int lastIndex = last - 1;
				int lVertexIndex = lVertex - 1;
				vertex[count] = vDegree(AGraph, lastIndex);

				for (int i = 0; i < nodes; i++) {
					if (AGraph[lastIndex][i] != 0 && AGraph[lastIndex][i] != -1) {

						AGraph[lVertexIndex][i] = AGraph[lVertexIndex][i]
								+ AGraph[lastIndex][i];

						AGraph[i][lVertexIndex] = AGraph[i][lVertexIndex]
								+ AGraph[i][lastIndex];

						AGraph[lastIndex][i] = -1;
						AGraph[i][lastIndex] = -1;
					}
				}
			}
			Arrays.sort(vertex);
			minCut = vertex[0];
		} else {
			minCut = 0;
		}
		return minCut;
	}

	// Find the number of additional edges required to increase connectivity by
	// 1
	public int minimumEdges(int[][] inputGraph, int[] maxOrder, int nodes,
			int minCut) {
		int fm = 0;
		Random r = new Random();
		int mCut;
		while (true) {
			int nodex1 = r.nextInt(nodes);
			int nodex2 = r.nextInt(nodes);
			if (nodex1 != nodex2 && inputGraph[nodex1][nodex2] != 1) {
				inputGraph[nodex1][nodex2] = 1;
				inputGraph[nodex2][nodex1] = 1;
				fm++;
				mCut = minimumCut(inputGraph, maxOrder);

				if (mCut == (minCut + 1))
					break;

			}
		}
		return fm;
	}

}
