import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ATNProject2 {

	static int nodes;
	static int edges;

	public static void main(String[] args) {

		try {
			/* Prompt user to enter number of Nodes and Edges */
			System.out.println("Enter the number of Nodes (n): ");
			BufferedReader nReader = new BufferedReader(new InputStreamReader(
					System.in));
			nodes = Integer.parseInt(nReader.readLine());

			/* Calculate the maximum number of edges with 'n' nodes */
			System.out.println("Note: Given " + nodes + " nodes, "
					+ "Maximum number of edges = " + nodes * (nodes - 1) / 2
					+ "");
			System.out.println("Enter the number of Edges (m): ");
			BufferedReader mReader = new BufferedReader(new InputStreamReader(
					System.in));
			edges = Integer.parseInt(mReader.readLine());

			nReader.close();
			mReader.close();

			if (edges >= nodes || edges == (nodes - 1)) {

				int maximumEdges = nodes * (nodes - 1) / 2;
				if (edges > maximumEdges) {
					System.out
							.println("Error: Number of Edges exceeds the Maximum Edges!");
				} else {

					/* Create a Random Graph */
					int graphMatrixValues[][] = new int[nodes][nodes];
					Nagamochi g = new Nagamochi();
					graphMatrixValues = g.drawGraph(nodes, edges);

					/* Print the Graph Matrix Values */
					for (int i = 0; i < nodes; i++) {
						for (int j = 0; j < nodes; j++) {
							System.out.print(graphMatrixValues[i][j] + " ");
						}
						System.out.println("");
					}

					boolean edgeFlag = false;
					for (int i = 0; i < nodes; i++) {
						edgeFlag = false;
						for (int j = 0; j < nodes; j++) {
							if (graphMatrixValues[i][j] != 0) {
								edgeFlag = true;
								break;
							}
						}
						if (!edgeFlag) {
							System.out
									.println("Error: Graph is not connected. Please Retry");
							System.exit(0);
						}
					}

					/* Maximum Adjacency Ordering */
					int[] maxAdjacentOrder = g.maxOrder(graphMatrixValues);

					/* Edge Connectivity of Graph */
					int minCut = g.minimumCut(graphMatrixValues,
							maxAdjacentOrder);
					System.out
							.println("\nEdge Connectivity of Graph (Minimum Cut) = "
									+ minCut);

					/*
					 * to find number of edges needed to increase minimumCut by
					 * 1, f(m)
					 */

					int fm = g.minimumEdges(graphMatrixValues,
							maxAdjacentOrder, nodes, minCut);
					System.out
							.println("Number of additional edges required to increase connectivity by 1 is f(m)="
									+ fm);

				}
			} else {
				System.out
						.println("Error: Graph is not connected, Connectivity of Graph is Zero.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
