package Leetcode.Medium;

import java.util.PriorityQueue;

/**
 * 1167. Minimum Cost to Connect Sticks
 * You have some sticks with positive integer lengths.
 * You can connect any two sticks of lengths X and Y into one stick by paying a cost of X + Y.  You perform this action until there is one stick remaining.
 * Return the minimum cost of connecting all the given sticks into one stick in this way.
 *
 * Example 1:
 * Input: sticks = [2,4,3]
 * Output: 14
 *
 * Example 2:
 * Input: sticks = [1,8,3,5]
 * Output: 30
 *
 * Constraints:
 * 1 <= sticks.length <= 10^4
 * 1 <= sticks[i] <= 10^4
 */
public class LC1167 {
	/**
	 * Solution: greedy
	 * A greedy approach will work here. Always take the cheapest operation possible, because the earlier a stick is combined, the more often its length will factor into the total cost.
	 * The sticks with long lengths cost a lot so we should use these the least.
	 *
	 * Time = O(nlogn)
	 * Space = O(n)
	 */
	public int connectSticks(int[] sticks) {
		if (sticks.length == 0) {
			return 0;
		}

		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int s: sticks) {
			pq.offer(s);
		}

		int res = 0;
		while (pq.size() > 1) {
			int sum = pq.poll() + pq.poll();
			res += sum;
			pq.offer(sum);
		}

		return res;
	}
}
