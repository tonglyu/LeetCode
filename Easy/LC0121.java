package Leetcode.Easy;

/**
 * 121. Best Time to Buy and Sell Stock
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.
 *
 * Note that you cannot sell a stock before you buy one.
 * Example 1:
 * Input: [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 *              Not 7-1 = 6, as selling price needs to be larger than buying price.
 *
 * Example 2:
 * Input: [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 */
public class LC0121 {
	/**
	 * Observation:
	 *      buy: prices[i] = min(prices[k]), k <= i
	 *      sell: prices[j] = max(prices[k]), k >= j
	 * Data structure: dp[min_price]
	 * keep tracking min price so far
	 *  low[i]: lowest price up to i-th day
	 *  profit[i]: max profit up to i-th day
	 *  profit[i] = max(profit[i-1], profit[i] - low[i])
	 * return profit[n-1]
	 *
	 * Time = O(n)
	 * Space = O(1)
	 * ref: https://www.youtube.com/watch?v=8pVhUpF1INw
	 */
	public int maxProfit(int[] prices) {
		if (prices.length == 0) {
			return 0;
		}

		int minPrice = Integer.MAX_VALUE;
		int maxProfit = 0;
		for (int i = 0; i < prices.length; i++) {
			minPrice = Math.min(minPrice, prices[i]);
			maxProfit = Math.max(maxProfit, prices[i] - minPrice);
		}
		return maxProfit;
	}

	/**
	 * Data structure: dp[max_subarray]
	 * 1) Compute the gain of each day from the second day:
	 *      -6, (4, -2, 3), -2
	 * 2) Find the maximum sub array of gain[]
	 *      p[i] = max(gain[i], p[i-1] + gain[i])
	 *      -6, 4, 2, 5, 3
	 *      the maximum subarray(i,j) means you buy the stock at the i-1th day and sell it at jth day
	 *
	 * Time = O(2n)
	 * Space = O(n)
	 */
	public int maxProfit2(int[] prices) {
		if (prices.length == 0) {
			return 0;
		}

		int[] curProfit = new int[prices.length - 1];
		for (int i = 1; i < prices.length; i++) {
			curProfit[i - 1] = prices[i] - prices[i - 1];
		}

		int[] subSum = new int[prices.length - 1];
		subSum[0] = curProfit[0];
		int maxSum = subSum[0];
		for (int i = 1; i < curProfit.length; i++) {
			subSum[i] = Math.max(curProfit[i], subSum[i - 1] + curProfit[i]);
			maxSum = Math.max(maxSum, subSum[i]);
		}

		return Math.max(0, maxSum);
	}

	public static void main(String[] args) {
		LC0121 sol = new LC0121();
		int[] prices = new int[]{7,1,5,3,6,4};
		System.out.println(sol.maxProfit2(prices));
	}
}
