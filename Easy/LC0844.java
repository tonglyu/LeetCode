package Leetcode.Easy;

/**
 * 844. Backspace String Compare
 * Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.
 *
 * Example 1:
 * Input: S = "ab#c", T = "ad#c"
 * Output: true
 * Explanation: Both S and T become "ac".
 *
 * Example 2:
 * Input: S = "ab##", T = "c#d#"
 * Output: true
 * Explanation: Both S and T become "".
 *
 * Example 3:
 * Input: S = "a##c", T = "#a#c"
 * Output: true
 * Explanation: Both S and T become "c".
 *
 * Example 4:
 * Input: S = "a#c", T = "b"
 * Output: false
 * Explanation: S becomes "c" while T becomes "b".
 *
 * Note:
 * 1 <= S.length <= 200
 * 1 <= T.length <= 200
 * S and T only contain lowercase letters and '#' characters.
 *
 * Follow up:
 * Can you solve it in O(N) time and O(1) space?
 */
public class LC0844 {
	/**
	 * Solution1: In-place replacement
	 *
	 * For each string, we return the result after manipulation
	 * i: all characters left to the i not including i are in result
	 * j: current index
	 *
	 * Case1: arr[j] is '#', move one step back of i if i > 0, j++
	 * Case2: arr[j] is not '#', arr[i] = arr[j], i++, j++
	 *
	 * Time = O(m + n)
	 * Space = O(m + n)???
	 */
	public boolean backspaceCompare(String S, String T) {
		return process(S.toCharArray()).equals(process(T.toCharArray()));
	}

	private String process(char[] arr) {
		int i = 0, j = 0;

		while (j < arr.length) {
			if (arr[j] != '#') {
				arr[i++] = arr[j++];
			} else {
				if (i > 0) {
					i--;
				}
				j++;
			}
		}

		return new String(arr, 0, i);
	}

	/**
	 * Solution2: use two pointers
	 * Traverse from right to left,
	 * each time we move i and j in its string until we find the first valid character in the string that should not be delete, and compare
	 * cnt: record the number of characters that should be deleted
	 *
	 * while we don't meet at the end of neither string:
	 *      Case1: c is '#', cnt++, i--;
	 *      Case2: c is not '#' && cnt > 0, cnt--, i--;
	 *      Case3: c is not '#' && cnt == 0, which means we find the first valid character from back to front, jump out of the loop
	 *
	 *      post-processing: if i < 0, which means the we are at the end, we should assign it with an empty char
	 *
	 *      if s != t, return false
	 *
	 * Time = O(m + n)
	 * Space = O(1)
	 */
	public boolean backspaceCompare2(String S, String T) {
		int i = S.length() - 1, j = T.length() - 1;

		int cnt1 = 0, cnt2 = 0;
		while (i >= 0 || j >= 0) {
			while (i >= 0 && (cnt1 > 0 || S.charAt(i) == '#')) {
				if (S.charAt(i) == '#') {
					cnt1++;
				} else {
					cnt1--;
				}
				i--;
			}

			while (j >= 0 && (cnt2 > 0 || T.charAt(j) == '#')) {
				if (T.charAt(j) == '#') {
					cnt2++;
				} else {
					cnt2--;
				}
				j--;
			}

			char s = i >= 0 ? S.charAt(i) : '#';
			char t = j >= 0 ? T.charAt(j) : '#';

			if (s != t) {
				return false;
			}

			i--;
			j--;
		}

		return true;
	}

	public static void main(String[] args) {
		LC0844 sol = new LC0844();
		String[] S = new String[]{"ab#c","ab##","a##c", "a#c"}, T = new String[]{"ad#c","c#d#","#a#c","b"};

		for (int i = 0; i < S.length; i++) {
			System.out.println(sol.backspaceCompare2(S[i], T[i]));
		}
	}
}
