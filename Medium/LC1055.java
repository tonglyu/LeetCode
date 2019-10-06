package Leetcode.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1055. Shortest Way to Form String
 *
 * From any string, we can form a subsequence of that string by deleting some number of characters (possibly no deletions).
 * Given two strings source and target, return the minimum number of subsequences of source such that their concatenation equals target.
 * If the task is impossible, return -1.
 *
 * Example 1:
 * Input: source = "abc", target = "abcbc"
 * Output: 2
 * Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".
 *
 * Example 2:
 * Input: source = "abc", target = "acdbc"
 * Output: -1
 * Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.
 *
 * Example 3:
 * Input: source = "xyz", target = "xzyxz"
 * Output: 3
 * Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
 *
 * Constraints:
 * Both the source and target strings consist of only lowercase English letters from "a"-"z".
 * The lengths of source and target string are between 1 and 1000.
 */
public class LC1055 {
	/**
	 * Solution1: Use a set
	 * In this solution, we greedy match as many chars from src to tar as possible, which can lead minimum use of src.
	 * Set: save all the char in src, if there exists a char from tar which not exists in set, return -1.
	 * Use two pointer , one iterate src, another iterate tar.
	 * For each tar char, we move j until src[j] == tar[i],
	 * if j is out of bound, means we finish one round match, res++, j = 0;
	 * otherwise, we move forward i and j
	 *
	 * Tips: res should be initialized to be 1 rather than 0, cuz we cannot increase result for the last part of matched string
	 *
	 * Time = O(mn)
	 * Space = O(m)
	 */
	public int shortestWay(String source, String target) {
		char[] cs = source.toCharArray(), ts = target.toCharArray();
		boolean[] map = new boolean[26];
		for (int i = 0; i < cs.length; i++)
			map[cs[i] - 'a'] = true;

		int i = 0, j = 0, res = 1;
		while (i < ts.length) {
			if (!map[ts[i] - 'a']) return -1;
			while (j < cs.length && cs[j] != ts[i]) {
				j++;
			}
			if (j == cs.length) {
				j = 0;
				res++;
			} else {
				i++;
				j++;
			}
		}

		return res;
	}

	/**
	 * Solution2: Brute force
	 * Each round we iterate source str completely, to see how many chars it can match with the target
	 * if there is no match for the current char in tar, means char is not in source, return -1
	 *
	 * Time = O(mn)
	 * Space = O(1)
	 */
	public int shortestWay2(String source, String target) {
		char[] cs = source.toCharArray(), ts = target.toCharArray();

		int i = 0, j = 0, res = 0;
		while (i < ts.length) {
			// record the original place of i
			int oriI = i;
			while (j < cs.length) {
				if (i < ts.length && cs[j] == ts[i]) {
					i++;
				}
				j++;
			}

			if (oriI == i) return -1;
			res++;
		}

		return res;
	}

	/**
	 * Solution3: Binary Search
	 * We use a list for each char, to record its indices in source
	 * For each char in tar, we need loop from j to end, to find a char same as tar[i].
	 *      when we iterate tar[i], we can easily to find the tar[i]'s idx list. to search the first idx which is larger or equal than j.
	 *      if we cannot find such index, means we need to start another round for src
	 *
	 * Time = O(n * logm)
	 * Space = O(m)
	 */
	public int shortestWay3(String source, String target) {
		char[] cs = source.toCharArray(), ts = target.toCharArray();
		List<Integer>[] map = new List[26];
		for (int i = 0; i < cs.length; i++) {
			if (map[cs[i] - 'a'] == null) {
				map[cs[i] - 'a'] = new ArrayList<>();
			}
			map[cs[i] - 'a'].add(i);
		}

		int i = 0, j = 0, res = 1;
		while (i < ts.length) {
			List<Integer> indices = map[ts[i] - 'a'];
			if (indices == null) {
				return -1;
			}

			int k = binarySearch(indices,j);
			if (k == -1) {
				res++;
				j = 0;
			} else {
				j = indices.get(k) + 1;
				i++;
			}
		}
		return res;
	}

	private int binarySearch(List<Integer> arr, int target) {
		int left = 0, right = arr.size() - 1;
		while (left + 1 < right) {
			int mid = left + (right - left) / 2;
			if (arr.get(mid) == target) {
				return mid;
			} else if (arr.get(mid) < target) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}

		if (arr.get(left) >= target) {
			return left;
		}
		if (arr.get(right) >= target) {
			return right;
		}

		return -1;
	}

	/**
	 * Solution4: inverted index
	 *
	 * it sounds like we need switch the map from [26][src.length] to [src.length][26].
	 * and we also need to use O 1 time to know what's next j position.
	 * now we are in the 2rd idx (j = 1), so tar[i] = 'a' we should save the map[1]['a'] the next position of j.
	 * if we are in the last idx, i think the map should be all 0, except the last tar char. for example the char is 'a'
	 * so the map for it is [last+1,0,0,...,0]
	 * how about last -1 idx, if the tar[last - 1] is same as tar[last], we just update it , [last - 1 + 1, 0....0]
	 * if is not same. we can update a 0 with last - 1 + 1
	 *
	 * Time = O(m + n)
	 * Space = O(m)
	 */
	public int shortestWay4(String source, String target) {
		char[] cs = source.toCharArray(), ts = target.toCharArray();

		int[][] idx = new int[cs.length][26];
		idx[cs.length - 1][cs[cs.length - 1] - 'a'] = cs.length;
		for (int i = cs.length - 2; i >= 0; i--) {
			idx[i] = Arrays.copyOf(idx[i + 1],26);
			idx[i][cs[i] - 'a'] = i + 1;
		}
		int j = 0, res = 1;
		for (int i = 0; i < ts.length; i++) {
			if (idx[0][ts[i] - 'a'] == 0) return -1;

			if (j == cs.length) {
				j = 0;
				res++;
			}


			j = idx[j][ts[i] - 'a'];

			// If there are no c's left in source that occur >= idx
			// but there are c's from earlier in the subsequence
			// add 1 to subsequence count.
			if (j == 0) {
				res++;
				i--;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		LC1055 sol = new LC1055();
		String[] source = new String[]{"abc", "abc", "xyz"}, target = new String[]{"abcbc", "acdbc", "xzyxz"};

		for (int i = 0; i < source.length; i++) {
			System.out.println(sol.shortestWay4(source[i], target[i]));
		}

	}
}
