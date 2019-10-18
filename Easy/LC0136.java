package Leetcode.Easy;

/**
 * 136. Single Number
 * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
 *
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 *
 * Example 1:
 * Input: [2,2,1]
 * Output: 1
 *
 * Example 2:
 * Input: [4,1,2,1,2]
 * Output: 4
 */
public class LC0136 {
	/**
	 * Solution: Bit Manipulation
	 * If we take XOR of zero and some bit, it will return that bit
	 * a ^ 0 = a
	 * If we take XOR of two same bits, it will return 0
	 * a ^ a = 0
	 * a ^ b ^ a = (a ^ a) ^ b = 0 ^ b = b
	 *
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int singleNumber(int[] nums) {
		int res = 0;
		for (int n: nums) {
			res = res ^ n;
		}

		return res;
	}
}
