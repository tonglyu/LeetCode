package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 1011. Capacity To Ship Packages Within D Days
 * A conveyor belt has packages that must be shipped from one port to another within D days.
 * The i-th package on the conveyor belt has a weight of weights[i].
 * Each day, we load the ship with packages on the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity of the ship.
 *
 * Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within D days.
 *
 * Example 1:
 * Input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
 * Output: 15
 * Explanation:
 * A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
 * 1st day: 1, 2, 3, 4, 5
 * 2nd day: 6, 7
 * 3rd day: 8
 * 4th day: 9
 * 5th day: 10
 *
 * Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and splitting the packages into parts like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed.
 *
 * Example 2:
 * Input: weights = [3,2,2,4,1,4], D = 3
 * Output: 6
 * Explanation:
 * A ship capacity of 6 is the minimum to ship all the packages in 3 days like this:
 * 1st day: 3, 2
 * 2nd day: 2, 4
 * 3rd day: 1, 4
 *
 * Example 3:
 * Input: weights = [1,2,3,1,1], D = 4
 * Output: 3
 * Explanation:
 * 1st day: 1
 * 2nd day: 2
 * 3rd day: 3
 * 4th day: 1, 1
 *
 * Note:
 * 1 <= D <= weights.length <= 50000
 * 1 <= weights[i] <= 500
 */
public class LC1011 {
	/**
	 * Solution: Binary Search
	 * left = maximum element in array, cuz we want ship for that cargo, our capacity needs to be larger than the max
	 * right = total weights
	 *
	 * So we need to find the minimum capacity with ship days <= D
	 * Each time we find the middle capacity to compute how many days it needs to ship all cargoes
	 * Case1: day > D, means we need more capacity, left = mid + 1
	 * Case2: day <= D, means current capacity can handle our problem, but we want to see if there is a less capacity
	 * We jump out of the loop when only 2 capacities left, and first check with the smaller one
	 *
	 * Time = O(n * logm)
	 * Space = O(1)
	 */
	public int shipWithinDays(int[] weights, int D) {
		int left = 0, right = 0;
		for (int w: weights) {
			left = Math.max(left, w);
			right += w;
		}

		while (left + 1 < right) {
			int  mid = left + (right - left) / 2;
			int day = numOfDays(weights, mid);
			if (day <= D) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		if (numOfDays(weights,left) <= D) {
			return left;
		}

		return right;
	}

	private int numOfDays(int[] weights, int cap) {
		int num = 0, cur = 0;
		for (int w: weights) {
			if (cur + w <= cap) {
				cur += w;
			} else {
				num++;
				cur = w;
			}
		}

		return num + 1;
	}

	@Test
	public void test1() {
		int[] weights = new int[]{1,2,3,4,5,6,7,8,9,10};
		int D = 5;
		Assert.assertEquals(shipWithinDays(weights,D), 15);
	}

	@Test
	public void test2() {
		int[] weights = new int[]{3,2,2,4,1,4};
		int D = 3;
		Assert.assertEquals(shipWithinDays(weights,D), 6);
	}

	@Test
	public void test3() {
		int[] weights = new int[]{1,2,3,1,1};
		int D = 4;
		Assert.assertEquals(shipWithinDays(weights,D), 3);
	}

	@Test
	public void test4() {
		int[] weights = new int[]{1,2,3,4,5,6,7,8,9,10};
		int D = 1;
		Assert.assertEquals(shipWithinDays(weights,D), 55);
	}

	public static void main(String[] args) {
		JUnitCore.main(LC1011.class.getName());
	}

}
