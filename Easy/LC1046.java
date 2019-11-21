package Leetcode.Easy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.PriorityQueue;

/**
 * 1046. Last Stone Weight
 * We have a collection of rocks, each rock has a positive integer weight.
 * Each turn, we choose the two heaviest rocks and smash them together.  Suppose the stones have weights x and y with x <= y.
 *
 * The result of this smash is:
 * If x == y, both stones are totally destroyed;
 * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
 * At the end, there is at most 1 stone left.  Return the weight of this stone (or 0 if there are no stones left.)
 *
 * Example 1:
 * Input: [2,7,4,1,8,1]
 * Output: 1
 * Explanation:
 * We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
 * we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
 * we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
 * we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of last stone.
 *
 * Note:
 * 1 <= stones.length <= 30
 * 1 <= stones[i] <= 1000
 */
public class LC1046 {
	/**
	 * Solution: max heap
	 * pop out top 2 and compare until pq.size() <= 1
	 *
	 * Time = O(nlogn)
	 * Space = O(n)
	 */
	public int lastStoneWeight(int[] stones) {
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b-a);
		for (int s: stones) {
			pq.offer(s);
		}

		while (pq.size() > 1) {
			int y = pq.poll();
			int x = pq.poll();
			if (y > x) {
				pq.offer(y-x);
			}
		}

		return pq.size() == 0 ? 0 : pq.poll();
	}

	@Test
	public void test1() {
		int[] stones = new int[]{2,7,4,1,8,1};
		Assert.assertEquals(1, lastStoneWeight(stones));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC1046.class.getName());
	}
}
