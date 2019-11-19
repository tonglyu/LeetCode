package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 1000. Minimum Cost to Merge Stones
 * There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.
 * A move consists of merging exactly K consecutive piles into one pile, and the cost of this move is equal to the total number of stones in these K piles.
 * Find the minimum cost to merge all piles of stones into one pile.  If it is impossible, return -1.
 *
 * Example 1:
 * Input: stones = [3,2,4,1], K = 2
 * Output: 20
 * Explanation:
 * We start with [3, 2, 4, 1].
 * We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
 * We merge [4, 1] for a cost of 5, and we are left with [5, 5].
 * We merge [5, 5] for a cost of 10, and we are left with [10].
 * The total cost was 20, and this is the minimum possible.
 *
 * Example 2:
 * Input: stones = [3,2,4,1], K = 3
 * Output: -1
 * Explanation: After any merge operation, there are 2 piles left, and we can't merge anymore.  So the task is impossible.
 *
 * Example 3:
 * Input: stones = [3,5,1,2,6], K = 3
 * Output: 25
 * Explanation:
 * We start with [3, 5, 1, 2, 6].
 * We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
 * We merge [3, 8, 6] for a cost of 17, and we are left with [17].
 * The total cost was 25, and this is the minimum possible.
 *
 * Note:
 * 1 <= stones.length <= 30
 * 2 <= K <= 30
 * 1 <= stones[i] <= 100
 */
public class LC1000 {
	/**
	 * Solution1: 3D DP
	 * Where there would be a solution, (K-1,K-1,...,K-1,1)
	 * Ans: each time merge K-1 and 1, then the length should meet (n-1) % (K-1) == 0
	 *
	 * merge cost of range K piles [i,m] ~ [m+1,j] -> into 1 pile sum[i,j] (最后一步的cost是整个数组的和)
	 *
	 * dp[i][j][k] represents min cost to merge subarray i ~ j into k piles
	 * Init: dp[i][i][1] = 0, others are INF
	 * ans: dp[0][n-1][1]
	 * transition:
	 * 1. dp[i][j][k] = min{dp[i][m][k-1] + dp[m+1][j][1]} for all i <= m < j (左大段：左边merge成 k-1 pile，右边只有1个pile)
	 * 2. dp[i][j][1] = dp[i][j][K] + sum(A[i]~A[j])
	 *
	 * Time = O(n^3 * K)
	 * Space = O(n^2 * K)
	 */
	public int mergeStones(int[] stones, int K) {
		int len = stones.length;
		if ((len - 1) % (K - 1) != 0) {
			return -1;
		}

		int[] prefixSum = new int[len + 1];
		for (int i = 0; i < len; i++) {
			prefixSum[i + 1] = prefixSum[i] + stones[i];
		}

		int INF = Integer.MAX_VALUE;
		int[][][] dp = new int[len][len][K + 1];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				for (int k = 1; k <= K; k++) {
					dp[i][j][k] = INF;
				}
			}
		}

		for (int i = 0; i < len; i++) {
			dp[i][i][1] = 0;
		}

		for (int l = 2; l <= len; l++) {
			//start from ith stone
			for (int i = 0; i <= len - l; i++) {
				int j = i + l - 1;
				for (int k = 2; k <= K; k++) {
					//find the separated point mid
					for (int mid = i; mid < j; mid += K-1) {
						if (dp[i][mid][k - 1] == INF || dp[mid + 1][j][1] == INF) {
							continue;
						}
						dp[i][j][k] = Math.min(dp[i][j][k], dp[i][mid][k - 1] + dp[mid + 1][j][1]);
					}
				}
				//if i to j cannot be merged into k piles, ignore
				if (dp[i][j][K] == INF) {
					continue;
				}
				dp[i][j][1] = dp[i][j][K] + prefixSum[j + 1] - prefixSum[i];

			}
		}

		return dp[0][len - 1][1];
	}

	/**
	 * Solution2: 2D DP
	 * stones[i] ~ stones[j] will merge as much as possible.
	 *
	 * Finally (j - i) % (K - 1) + 1 piles will be left.
	 *
	 * It's less than K piles and no more merger happens.
	 *
	 * dp[i][j] represents min cost to merge subarray i ~ j
	 * Init: dp[i][i] = 0 (Already a pile)
	 * Ans: dp[0][len-1]
	 * Transition:
	 * dp[i][j] = min(dp[i][k] + dp[k + 1][j]) (i <= k < j)
	 * if (j-i) % (K-1) which means i to j can be merged into 1 pile, we compute sum
	 *
	 * Time = O(n^3)
	 * Space = O(n^2)
	 */
	public int mergeStones2(int[] stones, int K) {
		int len = stones.length;
		if ((len - 1) % (K - 1) != 0) {
			return -1;
		}

		int[] prefixSum = new int[len+1];
		for (int i = 0; i <  len; i++) {
			prefixSum[i + 1] = prefixSum[i] + stones[i];
		}

		int[][] dp = new int[len][len];
		for (int l = 2; l <= len; ++l)
			for (int i = 0; i <= len - l; ++i) {
				int j = i + l - 1;
				dp[i][j] = Integer.MAX_VALUE;
				for (int mid = i; mid < j; mid += K - 1) {
					dp[i][j] = Math.min(dp[i][j], dp[i][mid] + dp[mid + 1][j]);
				}
				// If the current length can be merged into 1 pile
				// The cost is independent of the merge orders.
				if ((j - i) % (K - 1) == 0) {
					dp[i][j] += prefixSum[j + 1] - prefixSum[i];
				}
			}
		return dp[0][len - 1];
	}

	/**
	 * Solve each step merge[left,right] piles
	 */
	public int mergeStonesFollowUp(int[] stones, int left, int right) {
		int n = stones.length;
		int[] prefixSum = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
		}
		int[][][] dp = new int[n+1][n+1][n+1];
		int INF = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				for (int k = 1; k <= n; k++) {
					dp[i][j][k] = INF;
				}
			}
		}

		for (int i = 1; i <= n; i++) {
			dp[i][i][1] = 0;
		}

		for (int len = 1; len <= n; len++) {
			for (int i = 1; i + len - 1 <= n; i++) {
				int j = i + len - 1;
				for (int k = 2; k <= len; k++) {
					for (int mid = i; mid < j; mid++) {
						dp[i][j][k] = Math.min(dp[i][j][k], dp[i][mid][k - 1] + dp[mid + 1][j][1]);
					}
				}

				for (int k = left; k <= right; k++) {
					dp[i][j][1] = Math.min(dp[i][j][1], dp[i][j][k] + prefixSum[j] - prefixSum[i - 1]);
				}
			}
		}
		if (dp[1][n][1] >= INF) {
			return -1;
		}

		return dp[1][n][1];
	}

	@Test
	public void test1() {
		int[] stones = new int[]{3,2,4,1};
		int K = 2;
		Assert.assertEquals(20, mergeStones(stones,K));
	}

	@Test
	public void test2() {
		int[] stones = new int[]{3,2,4,1};
		int K = 3;
		Assert.assertEquals(-1, mergeStones(stones,K));
	}


	@Test
	public void test3() {
		int[] stones = new int[]{3,5,1,2,6};
		int K = 3;
		Assert.assertEquals(25, mergeStones2(stones,K));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC1000.class.getName());
	}
}
