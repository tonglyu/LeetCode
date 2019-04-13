package Leetcode.Medium;

import java.util.Arrays;

/**
 * 91. Decode Ways
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Given a non-empty string containing only digits, determine the total number of ways to decode it.
 *
 * Example 1:
 * Input: "12"
 * Output: 2
 * Explanation: It could be decoded as "AB" (1 2) or "L" (12).
 *
 * Example 2:
 * Input: "226"
 * Output: 3
 * Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 */
public class LC0091 {
	/**
	 * Data structure: dp
	 * dp[0] = 1;
	 * dp[1] = str[0] == '0' ? 0 : 1;
	 * dp[i] = dp[i-1] + dp[i-2];
	 *
	 * Time = O(n)
	 * Space = O(n) which can be optimized to O(1)
	 */
	public int numDecodings(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}

		int[] dp = new int[s.length() + 1];
		dp[0] = 1;
		dp[1] = s.charAt(0) == '0' ? 0 : 1;
		for (int i = 2; i <= s.length(); i++) {
			dp[i] = 0;
			int lastOne = s.charAt(i - 1) - '0';
			if (lastOne >= 1 && lastOne <= 9) {
				dp[i] += dp[i - 1];
			}
			int lastTwo = Integer.valueOf(s.substring(i - 2, i));
			if (lastTwo >= 10 && lastTwo <= 26) {
				dp[i] += dp[i - 2];
			}
		}
		return dp[s.length()];
	}

	/**
	 * Recursion +  Memorization
	 * Time = O(1) * (n + 1) = O(n)
	 * Space = O(n)
	 */
	public int numDecode(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] arr = s.toCharArray();
		int[] m = new int[s.length() + 1];
		Arrays.fill(m,-1);
		return ways(arr, arr.length - 1,m);
	}

	private int ways(char[] arr, int end, int[] m) {
		if (end < 0)  {
			return 1;
		} else if (end == 0) {
			m[end] = arr[end] == '0' ? 0 : 1;
			return m[end];
		}
		if (m[end] != -1) {
			return m[end];
		}

		int count = 0;
		if (arr[end] != '0') count += ways(arr, end -1,m);
		int lastTwo = (arr[end - 1] - '0' )* 10 + (arr[end] - '0');
		if (lastTwo >= 10 && lastTwo <= 26) {
			count += ways(arr,end-2,m);
		}
		m[end] = count;
		return count;
	}

	public static void main(String[] args) {
		LC0091 sol = new LC0091();
		String[] str = new String[]{"0","01","10","12","226"};
		for (String s:str) {
			System.out.println(sol.numDecode(s));
		}
	}

	// 0 1 2 3 4
	// 1 2 2 3 4
	//dp[1] = 1                     i = 1   k = 0
	//dp[2] = 1 + dp[1] = 2         i = 2,  k = 0,1
	//dp[3] = dp[1] + d[2] = 3      i = 3,  k = 1,2
	//dp[4] = dp[2] + dp[3] = 5
	//dp[5] = dp[4] = 5
}
