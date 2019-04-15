package Leetcode.Medium;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 200. Number of Islands
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 * Input:
 * 11110
 * 11010
 * 11000
 * 00000
 * Output: 1
 *
 * Example 2:
 * Input:
 * 11000
 * 11000
 * 00100
 * 00011
 * Output: 3
 */
public class LC0200 {
	/**
	 * Method1: DFS
	 * visit[][]: record whether the node has been visited, if it has been visited,
	 * we do not expand it.
	 *
	 * For each cell == '1': we do dfs(4 directions) to expand the area until we reach the boundary
	 *
	 * Time = O(m * n)
	 * Space = O(m * n) -> stack and 2-d array
	 * We can also set grid[i][j] = '0' to represent it is visited.
	 */
	public int numIslands(char[][] grid) {
		if (grid.length == 0) return 0;

		int m = grid.length;
		int n = grid[0].length;
		int[][] visit = new int[m][n];

		int sum = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (visit[i][j] == 1) continue;
				if (grid[i][j] == '1') {
					dfs(grid, visit, i, j);
					sum++;
				}
			}
		}

		return sum;
	}

	private void dfs(char[][] grid, int[][] visit, int row, int col) {
		if (grid[row][col] == '0' || visit[row][col] == 1) {
			return;
		}

		visit[row][col] = 1;
		if (row > 0) {
			dfs(grid, visit, row - 1, col);
		}
		if (row < grid.length - 1) {
			dfs(grid, visit, row + 1, col);
		}
		if (col > 0) {
			dfs(grid, visit, row, col - 1);
		}
		if (col < grid[0].length - 1) {
			dfs(grid, visit, row, col + 1);
		}
	}

	/**
	 * Method2: BFS
	 * Linear scan the 2d grid map, if a node contains a '1', then it is a root node that triggers a BFS.
	 * Put it into a queue and set its value as '0' to mark as visited node.
	 * Iteratively search the neighbors of enqueued nodes until the queue becomes empty.
	 *
	 * Tips: when offer a new node into the queue, we set it as '0' to avoid TLE.
	 *
	 * Time = O(m * n)
	 * Space = O(max(m,n)) for the length of the queue.
	 */
	public int numIslands2(char[][] grid) {
		if (grid.length == 0) return 0;

		int m = grid.length;
		int n = grid[0].length;

		int sum = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == '1') {
					sum++;
					grid[i][j] = '0';
					Queue<Integer> q = new LinkedList<>();
					q.offer(i * n + j);
					while (!q.isEmpty()) {
						for (int size = q.size(); size > 0; size--) {
							int id = q.poll();
							int r = id / n;
							int c = id % n;
							if (r > 0 && grid[r-1][c] == '1') {
								grid[r-1][c] = '0';
								q.offer((r-1) * n + c);
							}
							if (r < m - 1 && grid[r+1][c] == '1') {
								grid[r+1][c] = '0';
								q.offer((r+1) * n + c);
							}
							if (c > 0 && grid[r][c-1] == '1') {
								grid[r][c-1] = '0';
								q.offer(r * n + c - 1);
							}
							if (c < n - 1 && grid[r][c+1] == '1') {
								grid[r][c+1] = '0';
								q.offer(r * n + c + 1);
							}
						}
					}
				}
			}
		}

		return sum;
	}

	/**
	 * Method3: union find
	 *
	 * Time = O(n * m)
	 * Space = O(m * n)
	 * ref: https://www.jianshu.com/p/e23698bfff4b
	 */
	class UnionFind {
		int count; // # of connected components
		int[] parent;
		int[] rank;

		public UnionFind(char[][] grid) {
			count = 0;
			int m = grid.length;
			int n = grid[0].length;
			parent = new int[m * n];
			rank = new int[m * n];

			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (grid[i][j] == '1') {
						parent[ i * n + j] = i * n + j;
						count++;
					}
					rank[i * n + j] = 0;
				}
			}
		}

		public int find(int i) { // path compression
			if (parent[i] != i)
				parent[i] = find(parent[i]);
			return parent[i];
		}

		public void union(int x, int y) { // union with rank
			int rootx = find(x);
			int rooty = find(y);
			if (rootx != rooty) {
				if (rank[rootx] > rank[rooty]) {
					parent[rooty] = rootx;
				} else if (rank[rootx] < rank[rooty]) {
					parent[rootx] = rooty;
				} else {
					parent[rooty] = rootx; rank[rootx] += 1;
				}
				--count;
			}
		}

		public int getCount() {
			return count;
		}
	}

	public int numIslands3(char[][] grid) {
		if (grid == null || grid.length == 0) {
			return 0;
		}

		int nr = grid.length;
		int nc = grid[0].length;

		UnionFind uf = new UnionFind(grid);
		for (int r = 0; r < nr; ++r) {
			for (int c = 0; c < nc; ++c) {
				if (grid[r][c] == '1') {
					grid[r][c] = '0';
					if (r - 1 >= 0 && grid[r-1][c] == '1') {
						uf.union(r * nc + c, (r-1) * nc + c);
					}
					if (r + 1 < nr && grid[r+1][c] == '1') {
						uf.union(r * nc + c, (r+1) * nc + c);
					}
					if (c - 1 >= 0 && grid[r][c-1] == '1') {
						uf.union(r * nc + c, r * nc + c - 1);
					}
					if (c + 1 < nc && grid[r][c+1] == '1') {
						uf.union(r * nc + c, r * nc + c + 1);
					}
				}
			}
		}

		return uf.getCount();
	}

	public static void main(String[] args) {
		LC0200 sol = new LC0200();
		char[][] grid = new char[][]{
				{'1', '1', '0', '0','0'},
				{'1', '1', '0', '0','0'},
				{'0', '0', '1', '0','0'},
				{'0', '0', '0', '1','1'},
				{'0', '0', '0', '1','1'}
		};

		System.out.println(sol.numIslands2(grid));
	}
}
