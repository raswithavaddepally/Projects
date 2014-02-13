import java.util.Random;

public class NetworkInstance implements Cloneable{
	
	public static void main(String args[]) {

		int TIMES = 10;

		System.out.println("Edges-m\tConnectivity-Y(G)\tAVerage-f(m)");
		for (int i = 50; i <= 550; i += 5) {
			NetworkInstance ni = new NetworkInstance(i);
			ni.prepareNetwork();
			
			NetworkInstance nicopy = new NetworkInstance(ni);

			int connectivity = nicopy.calMinimumCut();
			int sum = 0;
			for (int j = 0; j < TIMES; j++) {// Number to average out.
				int numOfEdgesAdded = 0;
				NetworkInstance copy = new NetworkInstance(ni);
				NetworkInstance copycopy;
				do{
					copy.addEdge();
					copycopy = new NetworkInstance(copy);
					numOfEdgesAdded++;
				}
				while (copycopy.calMinimumCut() < connectivity + 1);
				sum+=numOfEdgesAdded;
			}
			double average = (sum * 1.0)/(TIMES*1.0);
			System.out.println(i+"\t"+connectivity+"\t"+average);

		}
	}

	
	
	
	public static int nosOfNodes = 25;
	int nosOfEdges;
	int index = 0;
	int count = 0;
	int MIN_NODE_COUNT = 0;
	int[][] nodesInfo;
	int[][] nodesMatrix;
	int[][] nodesInfo1;
	int[][] nodes = new int[2][2];
	int[][] MaxAdjList;
	int[][] EdgeCounts;

	public NetworkInstance(int noOfEdges) {
		MIN_NODE_COUNT = nosOfNodes;
		this.nosOfEdges = noOfEdges;
	}
	
	public void prepareNetwork(){
		initializeVariables();
		createNodeEdgeMatrix();
	}
	
	public NetworkInstance(NetworkInstance original) {
		this.MIN_NODE_COUNT = nosOfNodes;
		this.nosOfEdges = original.nosOfEdges;
		this.index = original.index;
		this.count = original.count;
		this.MIN_NODE_COUNT = original.MIN_NODE_COUNT;
		this.nodesInfo = copyArray(original.nodesInfo);
		this.nodesMatrix= copyArray(original.nodesMatrix);
		this.nodesInfo1 = copyArray(original.nodesInfo1);
		this.nodes = copyArray(original.nodes);
		this.MaxAdjList = copyArray(original.MaxAdjList);
		this.EdgeCounts = copyArray(original.EdgeCounts);
	}
	

	private int[][] copyArray(int[][] original) {
		if(original ==null)
			return null;
		int [][] copy = new int[original.length][];
		for(int i = 0; i < original.length; i++){
			copy[i] = new int[original[i].length];
			for(int j = 0; j < original[i].length; j++){
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}

	public void addEdge() {
		int u;
		int v;
		do {
			Random r = new Random();
			Random s = new Random();
			u = r.nextInt(nosOfNodes);
			v = s.nextInt(nosOfNodes);
		} while (u == v);
		nodesInfo[u][v] += 1;
		nodesInfo[v][u] += 1;
		nodesMatrix[u][v] += 1;
		nodesMatrix[v][u] += 1;
		nosOfEdges++;
	
	}

	private void createNodeEdgeMatrix() {
		// new method
		int tempIterations = nosOfEdges;
		int tempCounter = 0;
		while (tempCounter < tempIterations) {
			Random r = new Random();
			Random s = new Random();
			int u = r.nextInt(nosOfNodes);
			int v = s.nextInt(nosOfNodes);

			if (u != v) {
				nodesInfo[u][v] += 1;
				nodesInfo[v][u] += 1;
				tempCounter = tempCounter + 1;
				nodesMatrix[u][v] += 1;
				nodesMatrix[v][u] += 1;
			} else if (u == v) {
				nodesInfo[u][v] = 0;
				nodesInfo[v][u] = 0;
				nodesMatrix[u][v] = 0;
				nodesMatrix[v][u] = 0;
			}

		}

		for (int i = 0; i < nosOfNodes; i++) {
			nodesInfo[i][i] = 1;
			nodesMatrix[i][i] = 1;
		}
	}

	private void initializeVariables() {
		// System.out.println("Into initializeVariables");
		nodesInfo = new int[nosOfNodes][nosOfNodes];
		nodesMatrix = new int[nosOfNodes][nosOfNodes];
		MaxAdjList = new int[nosOfNodes][2];
		EdgeCounts = new int[nosOfNodes][nosOfNodes];

		for (int a = 0; a < nosOfNodes; a++) {
			for (int b = 0; b < nosOfNodes; b++) {
				nodesInfo[a][b] = 0;
				nodesMatrix[a][b] = 0;
			}
		}

	}

	/* A function to find the minimum cut */
	public int calMinimumCut() {

		/* Call the MaxAdjOrder function to perform MA ordering */
		int degree = findMaxAdjOrder(MIN_NODE_COUNT);

		if (degree == 0) {
			return 0;
		}
		/* Recursive call to Minimum cut as long as there are more than 2 nodes */
		if (MIN_NODE_COUNT > 2) {
			return min(degree, calMinimumCut());
		} else {
			return nodesInfo[1][1];
		}

	}

	/* A function which gives the minimum of 2 integers */
	public static int min(int a, int b) {
		if (a < b) {
			return a;
		} else {
			return b;
		}

	}

	/* A function to perform MA ordering */
	public int findMaxAdjOrder(int counter) {
		int deg = -1;
		for (int a = 0; a < MIN_NODE_COUNT; a++) {
			for (int b = 0; b < 2; b++) {
				MaxAdjList[a][b] = -1;
			}
		}
		if (count == 0) {
			MaxAdjList[0][0] = 0;
			MaxAdjList[0][1] = 0;

		}

		for (int n = 1; n < MIN_NODE_COUNT; n++) {
			index = n;

			EdgeCounts = new int[MIN_NODE_COUNT][MIN_NODE_COUNT];
			for (int i = 0; MaxAdjList[i][0] != -1; i++) {
				for (int j = 0; j < MIN_NODE_COUNT; j++) {
					if (j != MaxAdjList[i][0] && nodesInfo[j][MaxAdjList[i][0]] >= 1)
						EdgeCounts[j][MaxAdjList[i][0]] = nodesInfo[j][MaxAdjList[i][0]];
				}

			}
			max(EdgeCounts);
		}

		nodesInfo1 = new int[MIN_NODE_COUNT][MIN_NODE_COUNT];
		for (int k = 0; k < MIN_NODE_COUNT; k++) {
			for (int m = 0; m < MIN_NODE_COUNT; m++) {
				nodesInfo1[k][m] = nodesInfo[k][m];
			}
		}
		nodes[0][0] = MaxAdjList[MIN_NODE_COUNT - 2][0];
		nodes[0][1] = MaxAdjList[MIN_NODE_COUNT - 2][1];
		nodes[1][0] = MaxAdjList[MIN_NODE_COUNT - 1][0];
		nodes[1][1] = MaxAdjList[MIN_NODE_COUNT - 1][1];

		/* Check if graph is disconnected */
		for (int k = 0; k < MIN_NODE_COUNT; k++) {
			if (MaxAdjList[k][1] == -1)
				deg = 0;
		}
		if (deg != 0)
			deg = nodes[1][1];

		MIN_NODE_COUNT = MIN_NODE_COUNT - 1;

		reconstructMatrix();

		return deg;

	}

	private void reconstructMatrix() {
		int smallNo;
		int largeNo;
		/*
		 * Re-constructing the edge connectivity matrix after contracting the
		 * last 2 nodes in the MA ordering
		 */

		nodesInfo = new int[MIN_NODE_COUNT][MIN_NODE_COUNT];
		for (int a = 0; a < MIN_NODE_COUNT; a++) {
			for (int b = 0; b < MIN_NODE_COUNT; b++) {
				nodesInfo[a][b] = -1;
			}
		}

		if (nodes[0][0] > nodes[1][0]) {
			largeNo = nodes[0][0];
			smallNo = nodes[1][0];
		} else {
			largeNo = nodes[1][0];
			smallNo = nodes[0][0];
		}

		for (int i = 0; i < MIN_NODE_COUNT; i++) {
			for (int j = 0; j < MIN_NODE_COUNT; j++) {
				if (j < smallNo && j < largeNo && nodesInfo[i][j] == -1) {
					nodesInfo[i][j] = nodesInfo1[i][j];
					nodesInfo[j][i] = nodesInfo1[i][j];
				} else if (j > smallNo && j < largeNo && nodesInfo[i][j] == -1) {
					nodesInfo[i][j] = nodesInfo1[i][j];
					nodesInfo[j][i] = nodesInfo1[i][j];
				} else if (j > smallNo && j >= largeNo && nodesInfo[i][j] == -1) {
					nodesInfo[i][j] = nodesInfo1[i][j + 1];
					nodesInfo[j][i] = nodesInfo[i][j];
				} else if (i == smallNo)// && nodesInfo[i][j]==-1)
				{
					nodesInfo[i][j] = nodesInfo1[i][j] + nodesInfo1[largeNo][j];
					nodesInfo[j][i] = nodesInfo[i][j];
				} else if (j == smallNo)// && nodesInfo[i][j]==-1)
				{
					nodesInfo[i][j] = nodesInfo1[i][j] + nodesInfo1[i][largeNo];
					nodesInfo[j][i] = nodesInfo[i][j];
				}

			}
		}
	}

	/* A function to compute the maximum element in an array */
	public void max(int[][] array) {
		int[][] arr = new int[MIN_NODE_COUNT][MIN_NODE_COUNT];
		for (int i = 0; i < MIN_NODE_COUNT; i++) {
			for (int j = 0; j < MIN_NODE_COUNT; j++) {
				arr[i][j] = array[i][j];
			}
		}
		int[] sum = new int[MIN_NODE_COUNT];
		for (int k = 0; k < MIN_NODE_COUNT; k++) {
			for (int s = 0; s < MIN_NODE_COUNT; s++) {
				sum[k] += EdgeCounts[k][s];
			}
		}
		for (int i = 0; i < MIN_NODE_COUNT; i++) {
			int a = MaxAdjList[i][0];
			if (a != -1)
				sum[a] = 0;

		}

		int max = sum[0]; // start with the first value
		int ind = -1;

		for (int i = 1; i < MIN_NODE_COUNT; i++) {
			if (sum[i] > max) {
				max = sum[i]; // new maximum
			}
		}
		for (int chk = 0; chk < MIN_NODE_COUNT; chk++) {
			if (sum[chk] == max) {

				int i = 0;
				int flag = 0;

				while (MaxAdjList[i][0] != -1) {
					if (chk == MaxAdjList[i][0]) {
						flag = 1;
						break;
					}
					i++;
				}
				if (flag == 0) {
					ind = chk;
					break;
				}
			}
		}

		/* Updating the MA order list by the next element */
		MaxAdjList[index][0] = ind;
		MaxAdjList[index][1] = max;
		

	}
}