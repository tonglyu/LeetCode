package Leetcode.Easy;

import java.util.Arrays;

/**
 * 1170. Compare Strings by Frequency of the Smallest Character
 * Let's define a function f(s) over a non-empty string s, which calculates the frequency of the smallest character in s.
 * For example, if s = "dcce" then f(s) = 2 because the smallest character is "c" and its frequency is 2.
 * Now, given string arrays queries and words, return an integer array answer, where each answer[i] is the number of words such that f(queries[i]) < f(W), where W is a word in words.
 *
 * Example 1:
 * Input: queries = ["cbd"], words = ["zaaaz"]
 * Output: [1]
 * Explanation: On the first query we have f("cbd") = 1, f("zaaaz") = 3 so f("cbd") < f("zaaaz").
 *
 * Example 2:
 * Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
 * Output: [1,2]
 * Explanation: On the first query only f("bbb") < f("aaaa"). On the second query both f("aaa") and f("aaaa") are both > f("cc").
 *
 * Constraints:
 * 1 <= queries.length <= 2000
 * 1 <= words.length <= 2000
 * 1 <= queries[i].length, words[i].length <= 10
 * queries[i][j], words[i][j] are English lowercase letters.
 */
public class LC1170 {
	/**
	 * Solution: binary search
	 *
	 * First we count all smallest character frequency in words, and sort the list
	 * And then we find the first greater number's idx of count of queries[i], the number of arr.length - idx would be the result
	 * If there is no greater number, return 0
	 *
	 * m = queries.length, n = words.length
	 * Time = O(m + n + nlogn + mlogn)
	 * Space = O(n)
	 */
	public int[] numSmallerByFrequency(String[] queries, String[] words) {
		int[] res = new int[queries.length];

		int[] cnts = new int[words.length];
		for (int i = 0; i < words.length; i++) {
			cnts[i] = smallestFrequency(words[i]);
		}
		Arrays.sort(cnts);

		for (int i = 0; i < queries.length; i++) {
			int cur = smallestFrequency(queries[i]);
			res[i] = binary(cnts, cur);
		}

		return res;
	}

	private int binary(int[] arr, int target) {
		int left = 0, right = arr.length - 1;

		while (left + 1 < right) {
			int mid = left + (right - left) / 2;
			if (arr[mid] > target) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		if (arr[left] > target) {
			return arr.length - left;
		}

		if (arr[right] > target) {
			return arr.length - right;
		}

		return 0;
	}

	private int smallestFrequency(String w) {
		int count = 0;
		char minChar = (char)200;
		for (char ch : w.toCharArray()) {
			if (ch == minChar) {
				count++;
			} else if (ch < minChar) {
				minChar = ch;
				count = 1;
			}
		}

		return count;
	}

	public static void main(String[] args) {
		LC1170 sol = new LC1170();
		String[] queries = new String[]{"aabbabbb","abbbabaa","aabbbabaa","aabba","abb","a","ba","aa","ba","baabbbaaaa","babaa","bbbbabaa"};
		String[] words = new String[]{"b","aaaba","aaaabba","aa","aabaabab","aabbaaabbb","ababb","bbb","aabbbabb","aab","bbaaababba","baaaaa"};

		for (int n: sol.numSmallerByFrequency(queries,words)) {
			System.out.println(n);
		}
	}
}
