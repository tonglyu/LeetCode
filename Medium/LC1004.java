package Leetcode.Medium;

/**
 * 1004. Max Consecutive Ones III
 * Given an array A of 0s and 1s, we may change up to K values from 0 to 1.
 * Return the length of the longest (contiguous) subarray that contains only 1s.
 *
 * Example 1:
 * Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
 * Output: 6
 * Explanation:
 * [1,1,1,0,0,1,1,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
 *
 * Example 2:
 * Input: A = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
 * Output: 10
 * Explanation:
 * [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
 *
 * Note:
 * 1 <= A.length <= 20000
 * 0 <= K <= A.length
 * A[i] is 0 or 1
 */
public class LC1004 {
	/**
	 * Data structure: sliding window
	 * the sliding window can contain at most k zeros
	 * when to move left: when zero is larger than k, move left forward until zero == K
	 *
	 * Time = O(2n)
	 * Space = O(1)
	 */
	public int longestOnes(int[] A, int K) {
		int left = 0;
		int right = 0;
		int zero = 0;
		int longest = 0;
		while (right < A.length) {
			if (A[right] == 0) {
				zero++;
			}

			while (zero > K) {
				if (A[left] == 0) {
					zero--;
				}
				left++;
			}

			longest = Math.max(longest, right - left + 1);
			right++;
		}
		return longest;
	}

	public static void main(String[] args) {
		int[] A = new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1};
		int K = 3;
		LC1004 sol = new LC1004();
		System.out.println(sol.longestOnes(A,K));
	}
}
