package Leetcode.Medium;

/**
 * 8. String to Integer (atoi)
 * Implement atoi which converts a string to an integer.
 *
 * The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.
 * The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
 * If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
 * If no valid conversion could be performed, a zero value is returned.
 *
 * Note:
 * Only the space character ' ' is considered as whitespace character.
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.
 * Example 1:
 * Input: "42"
 * Output: 42
 *
 * Example 2:
 * Input: "   -42"
 * Output: -42
 * Explanation: The first non-whitespace character is '-', which is the minus sign.
 *              Then take as many numerical digits as possible, which gets 42.
 * Example 3:
 * Input: "4193 with words"
 * Output: 4193
 * Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
 *
 * Example 4:
 * Input: "words and 987"
 * Output: 0
 * Explanation: The first non-whitespace character is 'w', which is not a numerical
 *              digit or a +/- sign. Therefore no valid conversion could be performed.
 * Example 5:
 * Input: "-91283472332"
 * Output: -2147483648
 * Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
 *              Thefore INT_MIN (−231) is returned.
 */
public class LC0008 {
	/**
	 * Pay attention to how to check whether a number overflow:
	 * 1) sum > Integer.MAX_VALUE / 10
	 * 2) sum == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10
	 * we cannot use multiply, cuz overflow
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int myAtoi(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}

		//1. Remove Spaces
		int idx = 0;
		while (idx < str.length() && str.charAt(idx) == ' ') {
			idx++;
		}

		//2. Handle signs
		int sign = 1;
		if(idx < str.length() && (str.charAt(idx) == '+' || str.charAt(idx) == '-')){
			sign = str.charAt(idx) == '+' ? 1 : -1;
			idx++;
		}

		//3. Convert number and avoid overflow
		int sum = 0;
		for (int i = idx; i < str.length(); i++) {
			int digit = getDigit(str.charAt(i));
			if (digit < 0 || digit > 9) {
				break;
			}

			//check if total will be overflow after 10 times and add digit
			if (sum > Integer.MAX_VALUE / 10 || sum == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10) {
				return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			}
			sum = sum * 10 + digit;
		}

		return sum * sign;
	}

	private int getDigit(char c) {
		return c - '0';
	}

	public static void main(String[] args) {
		LC0008 sol = new LC0008();
		String[] strs = new String[]{""," ","+43","    -9", "4193 with words", "words and 987", "-2147483648"};
		for (String str: strs) {
			System.out.println(sol.myAtoi(str));
		}
	}
}
