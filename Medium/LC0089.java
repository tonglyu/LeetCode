package Leetcode.Medium;

import java.util.ArrayList;
import java.util.List;

/**
 * 89. Gray Code
 * The gray code is a binary numeral system where two successive values differ in only one bit.
 *
 * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.
 *
 * Example 1:
 * Input: 2
 * Output: [0,1,3,2]
 * Explanation:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 *
 * For a given n, a gray code sequence may not be uniquely defined.
 * For example, [0,2,3,1] is also a valid gray code sequence.
 *
 * 00 - 0
 * 10 - 2
 * 11 - 3
 * 01 - 1
 *
 * Example 2:
 * Input: 0
 * Output: [0]
 * Explanation: We define the gray code sequence to begin with 0.
 *              A gray code sequence of n has size = 2n, which for n = 0 the size is 20 = 1.
 *              Therefore, for n = 0 the gray code sequence is [0].
 */
public class LC0089 {
	/**
	 * n 位的格雷码可由 n-1位的格雷码递推，在最高位前顺序加0，逆序加1
	 * G(i) = G(i - 1) | (1 << k)
	 * For example, when n=3, we can get the result based on n=2.
	 * 00,01,11,10 -> (000,001,011,010 ) (110,111,101,100).
	 *
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public List<Integer> grayCode(int n) {
		List<Integer> res = new ArrayList<>();
		res.add(0);
		if (n == 0) {
			return res;
		}

		for (int i = 0; i < n; i++) {
			for (int j = res.size() - 1; j >= 0; j--) {
				int cur = res.get(j);
				res.add(cur | (1 << i));
			}

		}

		return res;
	}

	public static void main(String[] args) {
		LC0089 sol = new LC0089();
		List<Integer> res = sol.grayCode(2);
		System.out.println(res);
	}
}
