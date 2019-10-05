package Leetcode.Easy;

/**
 * 482. License Key Formatting
 * You are given a license key represented as a string S which consists only alphanumeric character and dashes. The string is separated into N+1 groups by N dashes.
 * Given a number K, we would want to reformat the strings such that each group contains exactly K characters, except for the first group which could be shorter than K, but still must contain at least one character. Furthermore, there must be a dash inserted between two groups and all lowercase letters should be converted to uppercase.
 * Given a non-empty string S and a number K, format the string according to the rules described above.
 *
 * Example 1:
 * Input: S = "5F3Z-2e-9-w", K = 4
 * Output: "5F3Z-2E9W"
 * Explanation: The string S has been split into two parts, each part has 4 characters.
 * Note that the two extra dashes are not needed and can be removed.
 *
 * Example 2:
 * Input: S = "2-5g-3-J", K = 2
 * Output: "2-5G-3J"
 * Explanation: The string S has been split into three parts, each part has 2 characters except the first part as it could be shorter as mentioned above.
 *
 * Note:
 * The length of string S will not exceed 12,000, and K is a positive integer.
 * String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
 * String S is non-empty.
 */
public class LC0482 {
	/**
	 * Solution1: StringBuilder
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public String licenseKeyFormatting(String S, int K) {
		StringBuilder sb = new StringBuilder();
		int i = S.length() - 1;
		int cnt = 0;
		while (i >= 0) {
			if (S.charAt(i) == '-') {
				i--;
				continue;
			}
			if (cnt < K) {
				sb.append(Character.toUpperCase(S.charAt(i--)));
				cnt++;
			} else if (cnt == K) {
				sb.append('-');
				cnt = 0;
			}
		}

		return sb.reverse().toString();
	}

	/**
	 * Solution2: Fake in-replacement
	 * First check how many occupants we need for all characters in result
	 *
	 * And then use sth like in-replacement, no worry about overwrite an existing character
	 *
	 * Time = O(2n)
	 * Space = O(n)
	 */
	public String licenseKeyFormatting2(String S, int K) {
		int numValid = 0;
		for (int i = 0; i < S.length(); i++) {
			if (S.charAt(i) != '-') {
				numValid++;
			}
		}

		// no numbers or letters '----'
		if (numValid == 0) return "";
		int size = numValid % K == 0 ? numValid / K - 1 : numValid / K;
		char[] res = new char[size + numValid];

		int i = S.length() - 1;
		int j = res.length - 1;
		int cnt = 0;
		while (i >= 0) {
			if (S.charAt(i) == '-') {
				i--;
				continue;
			}
			if (cnt < K) {
				res[j--] = Character.toUpperCase(S.charAt(i--));
				cnt++;
			} else if (cnt == K) {
				res[j--] = '-';
				cnt = 0;
			}
		}

		return new String(res);
	}

	public static void main(String[] args) {
		LC0482 sol = new LC0482();
		String S = "2-4A0r7-4k";
		int K = 3;

		System.out.println(sol.licenseKeyFormatting2(S,K));
	}
}
