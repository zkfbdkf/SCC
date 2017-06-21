import java.util.*;

public class SCC {
	static int[][] Link = { 
			{ 0, 1, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 1, 0, 1, 1, 0, 0 }, 
			{ 0, 0, 0, 1, 0, 0, 1, 0 },
			{ 0, 0, 1, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 1, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 1 } };//Original Graph
	static int[][] TLink;// Transpose Graph
	static boolean SCC = false;
	static String SCCarr[] = { "", "", "", "" };
	static int scccnt = 0;

	public static void main(String args[]) {
		Graph Original = new Graph(Link);

		DFS(Original);
		for (int i = 1; i <= 8; i++) {
			System.out.println("Original Vertex " + i + "'s discover time: " + Original.dis[i - 1]);
			System.out.println("Original Vertex " + i + "'s finish time: " + Original.fin[i - 1]);
		}

		TLink = Transpose(Link);

		Graph Transpose = new Graph(TLink);
		TransDFS(Transpose, Original);

		for (int i = 1; i <= 8; i++) {
			System.out.println("Transpose Vertex " + i + "'s discover time: " + Transpose.dis[i - 1]);
			System.out.println("Transpose Vertex " + i + "'s finish time: " + Transpose.fin[i - 1]);
		}

		for (int i = 0; i < SCCarr.length; i++) {
			System.out.println((i + 1) + " SCC: " + SCCarr[i]);
		}
	}

	private static void TransDFS(Graph T, Graph O) {
		int Dec[] = Order(O.fin);
		int vertex = 0;
		for (int i = 0; i < 8; i++) {
			vertex = Dec[i];
			if (T.visit[vertex] == false) {
				SCC = true;
				DFS_VISIT(T, vertex);
				scccnt++;
			}
			SCC = false;
		}
	}

	private static int[] Order(int[] fin) {
		//
		int[] temp = fin.clone();
		int[] result = new int[fin.length];
		int k = 7;
		Arrays.sort(temp);
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < fin.length; j++) {
				if (temp[i] == fin[j]) {
					result[k] = j;
					k--;
				}
			}
		}
		// reverse order
		return result;
	}

	private static void DFS(Graph g) {
		for (int i = 0; i < 8; i++) {
			if (g.visit[i] == false) {
				DFS_VISIT(g, i);
			}
		}
	}

	private static void DFS_VISIT(Graph g, int i) {
		g.visit[i] = true;
		System.out.println("Visit: " + (i + 1) + " vertex");

		if (SCC == true) {
			SCCarr[scccnt] += String.valueOf(i + 1);
		}
		g.time++;
		g.dis[i] = g.time;
		for (int j = 0; j < 8; j++) {
			if (g.link[i][j] == 1 && g.visit[j] == false) {
				g.parent[j] = i;
				DFS_VISIT(g, j);
			}
		}
		g.time++;
		g.fin[i] = g.time;
	}

	private static int[][] Transpose(int[][] link) {
		int[][] temp = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				temp[j][i] = link[i][j];
			}
		}
		return temp;
	}
}

class Graph {
	int[][] link;
	boolean[] visit = new boolean[8];
	int[] dis = new int[8];
	int[] fin = new int[8];
	int[] parent = new int[8];
	int time = 0;

	public Graph(int[][] link) {
		this.link = link;
	}
}