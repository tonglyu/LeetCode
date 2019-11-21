package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 980. Unique Paths III
 * On a 2-dimensional grid, there are 4 types of squares:
 * 1 represents the starting square.  There is exactly one starting square.
 * 2 represents the ending square.  There is exactly one ending square.
 * 0 represents empty squares we can walk over.
 * -1 represents obstacles that we cannot walk over.
 * Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.
 *
 * Example 1:
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
 * Output: 2
 * Explanation: We have the following two paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
 * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
 *
 * Example 2:
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
 * Output: 4
 * Explanation: We have the following four paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
 * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
 * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
 * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
 *
 * Example 3:
 * Input: [[0,1],[2,0]]
 * Output: 0
 * Explanation:
 * There is no path that walks over every empty square exactly once.
 *
 * Note that the starting and ending square can be anywhere in the grid.
 *
 * Note:
 * 1 <= grid.length * grid[0].length <= 20
 */
public class LC0980 {
	int[][] dir = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
	int m;
	int n;
	/**
	 * Solution1: Backtracking + DFS
	 * First we need to find the start point and the number of steps we need to take
	 * Then we use DFS to traverse the grid
	 * #Levels: m * n at worst case
	 * #Directions in each level: 4
	 * Termination condition: we find end point with 2, if the required steps are all taken, increase result.
	 *
	 * Time = O(m * n + 4^(m*n))
	 * Space = O(m * n) for stack
	 */
	public int uniquePathsIII(int[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		int sx = -1; int sy = -1;
		int cnt = 1;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1) {
					sx = i;
					sy = j;
				} else if (grid[i][j] == 0) {
					cnt++;
				}
			}
		}

		return dfs(grid, sx, sy, cnt);
	}

	private int dfs(int[][] grid, int row, int col, int cnt) {
		//we meet the destination
		if (grid[row][col] == 2) {
			if (cnt == 0) return 1;
			return 0;
		}

		int path = 0;
		int tmp = grid[row][col];
		grid[row][col] = -2;
		for (int j = 0; j < 4; j++) {
			int x = row + dir[j][0];
			int y = col + dir[j][1];
			//new cell is out of boundary or is an obstacle or is visited
			if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == -1 || grid[x][y] == -2)
				continue;
			path += dfs(grid, x, y, cnt - 1);
		}
		grid[row][col] = tmp;
		return path;
	}

	/**
	 * Solution2: DP with memo
	 * memo[i][j][state]: the # of path from (i, j), and given that state is the set of empty squares we've yet to walk on.
	 * Since there are m*n cells, we need m*n digit binary number to represent
	 *
	 * Time = O(m*n*2^(m*n))
	 * Space = O(m*n*2^(m*n))
	 */
	public int uniquePathsIII2(int[][] grid) {
		m = grid.length;
		n = grid[0].length;
		int target = 0;

		int sr = 0, sc = 0;
		for (int r = 0; r < m; ++r)
			for (int c = 0; c < n; ++c) {
				if (grid[r][c] % 2 == 0) {//set the r*n+c digit to 1
					target |= code(r, c);
				}

				if (grid[r][c] == 1) {
					sr = r;
					sc = c;
				}
			}

		Integer[][][] memo = new Integer[m][n][1 << m*n];
		return dp(grid, sr, sc, target, memo);
	}

	public int code(int r, int c) {
		return 1 << (r * n + c);
	}

	public Integer dp(int[][] grid, int row, int col, int target, Integer[][][] memo) {
		if (memo[row][col][target] != null)
			return memo[row][col][target];

		if (grid[row][col] == 2) {
			//when all 0's are visited, target becomes 0
			return target == 0 ? 1 : 0;
		}

		int path = 0;
		for (int k = 0; k < 4; ++k) {
			int x = row + dir[k][0];
			int y = col + dir[k][1];
			if ( x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == -1) continue;
			//if (x,y) has been visited, target & code(x, y) == 0, cuz (x,y) in target is 0
			if ((target & code(x, y)) == 0) continue;
			//target ^ code(x, y) set (x,y) to visited
			//difference is when we expand the node, we already set it to visit
			path += dp(grid, x, y, target ^ code(x, y), memo);
		}
		memo[row][col][target] = path;
		return path;
	}

	@Test
	public void test1() {
		int[][] grid = new int[][]{
				{1,0,0,0},
				{0,0,0,0},
				{0,0,2,-1}
		};
		Assert.assertEquals(2, uniquePathsIII(grid));
	}

	@Test
	public void test2() {
		int[][] grid = new int[][]{
				{1,0,0,0},
				{0,0,0,0},
				{0,0,0,2}
		};
		Assert.assertEquals(4, uniquePathsIII2(grid));
	}

	@Test
	public void test3() {
		int[][] grid = new int[][]{
				{0,1},
				{2,0}
		};
		Assert.assertEquals(0, uniquePathsIII(grid));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0980.class.getName());
	}
}
