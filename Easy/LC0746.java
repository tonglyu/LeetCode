package Leetcode.Easy;

import Leetcode.Medium.LC0794;

/**
 * 746. Min Cost Climbing Stairs
 * On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).
 *
 * Once you pay the cost, you can either climb one or two steps. You need to find minimum cost to reach the top of the floor, and you can either start from the step with index 0, or the step with index 1.
 *
 * Example 1:
 * Input: cost = [10, 15, 20]
 * Output: 15
 * Explanation: Cheapest is start on cost[1], pay that cost and go to the top.
 * Example 2:
 * Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
 * Output: 6
 * Explanation: Cheapest is start on cost[0], and only step on 1s, skipping cost[3].
 * Note:
 * cost will have a length in the range [2, 1000].
 * Every cost[i] will be an integer in the range [0, 999].
 */
public class LC0746 {
	/**
	 * DP : n + 1 array (0 - n)
	 * top: nth stair
	 * Init: dp[0] = 0, dp[1] = 0
	 * dp[i] represent the minimum cost to reach ith stair
	 * dp[i] = min(dp[i-2]+cost[i-2], dp[i-1] + cost[i-1])
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int minCostClimbingStairs(int[] cost) {
		int n = cost.length;
		if (n <= 1) return 0;

		int step1 = 0;
		int step2 = 0;
		int res = 0;
		for (int i = 2; i <= n; i++) {
			res = Math.min(step1 + cost[i-2], step2 + cost[i-1]);
			step1 = step2;
			step2 = res;
		}

		return res;
	}

	public static void main(String[] args) {
		int[]  cost = new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
		LC0746 sol = new LC0746();
		System.out.println(sol.minCostClimbingStairs(cost));
	}
}
