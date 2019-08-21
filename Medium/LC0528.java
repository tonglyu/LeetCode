package Leetcode.Medium;

import java.util.Random;

/**
 * 528. Random Pick with Weight
 * Given an array w of positive integers, where w[i] describes the weight of index i, write a function pickIndex which randomly picks an index in proportion to its weight.
 *
 * Note:
 * 1 <= w.length <= 10000
 * 1 <= w[i] <= 10^5
 * pickIndex will be called at most 10000 times.
 *
 * Example 1:
 * Input:
 * ["Solution","pickIndex"]
 * [[[1]],[]]
 * Output: [null,0]
 *
 * Example 2:
 * Input:
 * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
 * [[[1,3]],[],[],[],[],[]]
 * Output: [null,0,1,1,1,0]
 *
 * Explanation of Input Syntax:
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has one argument, the array w.
 * pickIndex has no arguments. Arguments are always wrapped with a list, even if there aren't any.
 */
public class LC0528 {
	/**
	 * Solution: accumulate sum
	 * w = [1,2,3,4]
	 * so, the probability for pick each ele is 1/10, 2/10, 3/10, 4/10
	 * We can construct a accumulate sum array, use a range to represent the number of units we may pick for this index
	 * [1,3,6,10], say all numbers generated up to 1 is index 0
	 * range: [1], [2,3], [4,5,6],[7,8,9,10]
	 * index: 0,    1,      2,      3
	 *
	 * Time: init O(n), search O(logn)
	 */
	int[] weights;
	int wsum;
	Random rand = new Random();

	public LC0528(int[] w) {
		wsum = 0;
		weights = new int[w.length];
		for(int i = 0; i < w.length; i++) {
			wsum += w[i];
			weights[i] = wsum;
		}
	}

	/**
	 * target = [0,wsum) -> [0,9]
	 * if sum[mid] <= target, means sum = target + 1 must be on the right side
	 * else
	 *      means sum[mid] must be on the left or current
	 */
	public int pickIndex() {
		int target = rand.nextInt(wsum);

		int l = 0, r = weights.length - 1;
		while(l < r) {
			int mid = l + (r - l) / 2;
			if(weights[mid] > target) {
				r = mid;
			} else {
				l = mid + 1;
			}
		}
		return l;
	}

	public static void main(String[] args) {
		int[] w = new int[]{1,2,3,4};
		LC0528 sol = new LC0528(w);
		System.out.println(sol.pickIndex());
	}
}
