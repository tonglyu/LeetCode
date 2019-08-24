package Leetcode.Hard;

/**
 * 273. Integer to English Words
 * Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.
 *
 * Example 1:
 * Input: 123
 * Output: "One Hundred Twenty Three"
 *
 * Example 2:
 * Input: 12345
 * Output: "Twelve Thousand Three Hundred Forty Five"
 *
 * Example 3:
 * Input: 1234567
 * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 *
 * Example 4:
 * Input: 1234567891
 * Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
 */
public class LC0273 {
	private final String[] LESS_THAN_20 = {"", "One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ", "Eight ", "Nine ", "Ten ", "Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Seventeen ", "Eighteen ", "Nineteen "};
	private final String[] TENS = {"", "Ten ", "Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ", "Seventy ", "Eighty ", "Ninety "};
	private final String[] THOUSANDS = {"", "Thousand ", "Million ", "Billion "};

	/**
	 * Solution: Divide and conquer
	 * The format to represent each three digits are same, so we only need to find ways to represent 3-digits number
	 * Every time get last three digits of number:
	 *      if n == 0, we should ignore, nothing add to string
	 *
	 * For each 3 digits:
	 *      if n < 20:
	 *          specific array of representation
	 *      elif n < 100:
	 *          specify for tens digit and same sub problem for ones digit
	 *      else:
	 *          hundreds (digit) + "Hundred " + same sub problem for 2-digit number
	 *
	 * Time = O(n). Intuitively the output is proportional to the number N of digits in the input.
	 * Space = O(1)
	 */
	public String numberToWords(int num) {
		if (num == 0) {
			return "Zero";
		}

		int scale = 0;
		StringBuilder word = new StringBuilder();

		while (num > 0) {
			if (num % 1000 != 0) {
				String cell = helper(num % 1000) + THOUSANDS[scale];
				word.insert(0, cell);
			}
			// no matter whether the number is 0, the scale should increase
			scale++;
			num /= 1000;
		}

		return word.toString().trim();
	}

	private String helper(int n) {
		if (n < 20) {
			return LESS_THAN_20[n];
		} else if (n < 100) {
			return TENS[n / 10] + helper(n % 10);
		} else {
			return LESS_THAN_20[n / 100] + "Hundred " + helper(n % 100);
		}
	}

	public static void main(String[] args) {
		LC0273 sol = new LC0273();
		int[] nums = new int[]{0,100,1010,1000100,999,123000,12345};

		for (int n : nums) {
			System.out.println(sol.numberToWords(n));
		}
	}
}
