package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 837. New 21 Game
 * Alice plays the following game, loosely based on the card game "21".
 *
 * Alice starts with 0 points, and draws numbers while she has less than K points.  During each draw, she gains an integer number of points randomly from the range [1, W], where W is an integer.  Each draw is independent and the outcomes have equal probabilities.
 * Alice stops drawing numbers when she gets K or more points.  What is the probability that she has N or less points?
 *
 * Example 1:
 * Input: N = 10, K = 1, W = 10
 * Output: 1.00000
 * Explanation:  Alice gets a single card, then stops.
 *
 * Example 2:
 * Input: N = 6, K = 1, W = 10
 * Output: 0.60000
 * Explanation:  Alice gets a single card, then stops.
 * In 6 out of W = 10 possibilities, she is at or below N = 6 points.
 *
 * Example 3:
 * Input: N = 21, K = 17, W = 10
 * Output: 0.73278
 *
 * Note:
 * 0 <= K <= N <= 10000
 * 1 <= W <= 10000
 * Answers will be accepted as correct if they are within 10^-5 of the correct answer.
 * The judging time limit has been reduced for this question.
 */
public class LC0837 {
	/**
	 * Solution1: DFS
	 * When the game ends, the point is between K and K-1+W
	 *     What is the probability that the the point is less than N?
	 *     - If N is greater than K-1+W, probability is 1
	 *     - If N is less than K, probability is 0
	 *
	 * So we need to compute the probabilities that point >= K && point <= N
	 * Each level is the rounds of games
	 * In each round, we have 1- W choices to add to curSum, and need to add all probabilities from all the conditions
	 *
	 * Base case: when curSum >= K, we should stop the game
	 * if curSum <= N, it meet the condition, so the probability is 1, else is 0
	 *
	 * Time = O(K ^ W)
	 * Space = O(K)
	 */
	public double new21Game(int N, int K, int W) {
		return dfs(0, N, K, W);
	}

	private double dfs(int curSum, int N, int K, int W) {
		if (curSum >= K) {
			if (curSum <= N) {
				return 1;
			}
			return 0;
		}

		double sum = 0;
		for (int i = 1; i <= W; i++) {
			sum += 1.0/W * dfs(curSum + i, N, K, W);
		}
		return sum;
	}

	/**
	 * Solution2: DP
	 * If W == 3 and we want to find the probability to get a 5
	 *     - You can get a card with value 1, 2, or 3 with equal probability (1/3)
	 *     - If you had a 4 and you get a 1: prob(4) * (1/3)
	 *     - If you had a 3 and you get a 2: prob(3) * (1/3)
	 *     - If you had a 2 and you get a 3: prob(2) * (1/3)
	 *     - If you had a 1, you can never reach a 5 in the next draw
	 *     - prob(5) = prob(4) / 3 + prob(3) / 3 + prob(2) /3
	 *
	 * To generalize:
	 *     The probability to get point K is
	 *     p(K) = p(K-1) / W + p(K-2) / W + p(K-3) / W + ... p(K-W) / W
	 *
	 * So for each point i, we need to accumulate the sum from i-w to i-1
	 * For the basic number in [1,w], we set if j < 0, continue
	 * For some very large number >= K, we set if j >= K, continue, cuz we cannot use these numbers to form our results
	 *
	 * Time = O(N * W)
	 * Space = O(W)
	 */
	public double new21Game2(int N, int K, int W) {
		if (K == 0) {
			return 1;
		}

		double[] dp = new double[N + 1];
		dp[0] = 1;

		for (int i = 1; i <= N; i++) {
			for (int j = i - W; j < i; j++) {
				if (j < 0) continue;
				if (j >= K) continue;
				dp[i] += dp[j] * 1.0 / W;
			}
		}

		double res = 0;
		for (int i = K; i <= N; i++) {
			res += dp[i];
		}

		return res;
	}

	/**
	 * Solution3: DP + sliding window
	 * let wsum = p(K-1) + p(K-2) + ... + p(K-W)
	 *     p(K) = wsum / W
	 *
	 * Each time we need to traverse a window of size w, how about remove the first element and add the latest element in O(1) time
	 * We need to keep track of current sum of window
	 *
	 * At the same time, we also need to consider the basic number and larger numbers
	 * i = 11是不可能一次抽中的（大于w），所以要把一次抽中的概率减去，就是第一次。
	 *
	 * Time = O(N)
	 * Space = O(N)
	 */
	public double new21Game3(int N, int K, int W) {
		if (K == 0) {
			return 1;
		}

		double[] dp = new double[N + 1];
		dp[0] = 1;

		double sum = 0, res = 0;
		for (int i = 1; i <= N; i++) {
			if (i - 1 < K) {
				sum += dp[i - 1];
			}
			if (i - W - 1 >= 0) {
				sum -= dp[i - W - 1];
			}

			dp[i] = sum / W;
			if (i >= K) {
				res += dp[i];
			}
		}

		return res;
	}

	@Test
	public void test1() {
		int N = 10, K = 1, W = 10;
		Assert.assertEquals(new21Game3(N, K, W), 1.0, 0.01);
	}

	@Test
	public void test2() {
		int N = 6, K = 1, W = 10;
		Assert.assertEquals(new21Game3(N, K, W), 0.6, 0.01);
	}

	@Test
	public void test3() {
		int N = 21, K = 17, W = 10;
		Assert.assertEquals(new21Game3(N, K, W), 0.73278, 0.01);
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0837.class.getName());
	}
}
