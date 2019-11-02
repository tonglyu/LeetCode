package Leetcode.Easy;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * 1099. Two Sum Less Than K
 * Given an array A of integers and integer K, return the maximum S such that there exists i < j with A[i] + A[j] = S and S < K.
 * If no i, j exist satisfying this equation, return -1.
 *
 * Example 1:
 * Input: A = [34,23,1,24,75,33,54,8], K = 60
 * Output: 58
 * Explanation:
 * We can use 34 and 24 to sum 58 which is less than 60.
 *
 * Example 2:
 * Input: A = [10,20,30], K = 15
 * Output: -1
 *
 * Explanation:
 * In this case it's not possible to get a pair sum less that 15.
 *
 * Note:
 * 1 <= A.length <= 100
 * 1 <= A[i] <= 1000
 * 1 <= K <= 2000
 */
public class LC1099 {
	/**
	 * Solution1: treeset
	 * Treeset maintains the difference btw K - A[i]
	 * for each ele:
	 *      We need to find the first element in set which is greater than A[i]
	 *      if it exits: update res
	 *      add(K - A[i]) into set
	 *
	 * Time = O(nlogn)
	 * Space = O(n)
	 */
	public int twoSumLessThanK(int[] A, int K) {
		if (A.length == 0) {
			return -1;
		}

		TreeSet<Integer> map = new TreeSet<>();
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < A.length; i++) {
			if (A[i] > K) continue;
			Integer remain = map.higher(A[i]);
			if (remain != null) {
				res = Math.max(res, A[i] + K - remain);
			}
			map.add(K - A[i]);
		}

		return res == Integer.MIN_VALUE ? -1 : res;
	}

	/**
	 * Solution2: sort
	 * sort the array, implement two pointers from back and front
	 * Case1: sum of two eles < K
	 *      update res, increase the smaller element
	 * Case2: sum >= K
	 *      decrease larger element
	 *
	 * Time = O(nlogn)
	 * Space = O(1)
	 */
	public int twoSumLessThanK2(int[] A, int K) {
		if (A.length == 0) {
			return -1;
		}

		Arrays.sort(A);
		int res = Integer.MIN_VALUE;
		int i = 0, j = A.length - 1;
		while (i < j) {
			int sum = A[i] + A[j];
			if (sum < K) {
				res = Math.max(res, sum);
				// increase the smaller element.
				i++;
			} else {
				// decrease the larger element.
				j--;
			}
		}

		return res == Integer.MIN_VALUE ? -1 : res;
	}

	public static void main(String[] args) {
		LC1099 sol = new LC1099();
		int[] A = new int[]{34,23,1,24,75,33,54,8};
		int K = 60;
		System.out.println(sol.twoSumLessThanK2(A, K));
	}
}
