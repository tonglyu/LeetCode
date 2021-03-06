package Leetcode.Medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1066. Campus Bikes II
 * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
 * We assign one unique bike to each worker so that the sum of the Manhattan distances between each worker and their assigned bike is minimized.
 * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
 * Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.
 *
 * Example 1:
 * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
 * Output: 6
 * Explanation:
 * We assign bike 0 to worker 0, bike 1 to worker 1. The Manhattan distance of both assignments is 3, so the output is 6.
 *
 * Example 2:
 * Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
 * Output: 4
 * Explanation:
 * We first assign bike 0 to worker 0, then assign bike 1 to worker 1 or worker 2, bike 2 to worker 2 or worker 1. Both assignments lead to sum of the Manhattan distances as 4.
 *
 * Note:
 * 0 <= workers[i][0], workers[i][1], bikes[i][0], bikes[i][1] < 1000
 * All worker and bike locations are distinct.
 * 1 <= workers.length <= bikes.length <= 10
 */
public class LC1066 {
	int sum = Integer.MAX_VALUE;

	/**
	 * Solution1: DFS
	 * We provide 0th workers with m bikes, 1th with m-1, ....
	 * Find all possible solutions for the workers, bikes combination
	 *
	 * Time = O(m!)
	 * Space = O(n)
	 */
	public int assignBikes(int[][] workers, int[][] bikes) {
		boolean[] used = new boolean[bikes.length];
		dfs(workers, bikes, used, 0, 0);
		return sum;
	}

	private void dfs(int[][] workers, int[][] bikes, boolean[] used, int wid, int dis) {
		if (wid == workers.length) {
			sum = Math.min(sum, dis);
			return;
		}

		//pruning
		if (dis > sum) {
			return;
		}

		for (int j = 0; j < bikes.length; j++) {
			if (!used[j]) {
				used[j] = true;
				dfs(workers, bikes, used, wid + 1, dis + getDis(workers[wid], bikes[j]));
				used[j] = false;
			}
		}
	}

	/**
	 * Solution2: DP?
	 * if we used 1st, 3rd, 7th bike, state will be 001001010
	 * state : dp[i][s] = the min distance for first i workers to build the state s ,
	 * transit: dp[i][s] = min(dp[i][s], dp[i - 1][prev] + dis(w[i -1], bike[j)) | 0 < j <m, prev = s ^ (1 << j)
	 * init:dp[0][0] = 0;
	 * result: dp[n][s] s should have n bit
	 *
	 * Time = O(n * 2 ^m)
	 * Space = O(n * (1 << m));
	 */
	public int assignBikes2(int[][] workers, int[][] bikes) {
		int n = workers.length;
		int m = bikes.length;
		int[][] dp = new int[n + 1][1 << m];
		for (int[] d : dp) {
			Arrays.fill(d, Integer.MAX_VALUE / 2);
		}
		dp[0][0] = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			for (int s = 1; s < (1 << m); s++) {
				for (int j = 0; j < m; j++) {
					if ((s & (1 << j)) == 0) {
						continue;
					}

					// set jth bike to unused
					int prev = s ^ (1 << j);
					dp[i][s] = Math.min(dp[i - 1][prev] + getDis(workers[i - 1], bikes[j]), dp[i][s]) ;
					if (i == n) {
						min = Math.min(min, dp[i][s]);
					}
				}
			}
		}
		return min;
	}

	private int getDis(int[] worker, int[] bike) {
		return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
	}

	public static void main(String[] args) {
		LC1066 sol = new LC1066();
		int[][] workers = new int[][]{{0, 0}, {2, 1}};
		int[][] bikes = new int[][]{{1, 2}, {3, 3}};

		System.out.println(sol.assignBikes2(workers, bikes));
	}
}
