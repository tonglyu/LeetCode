package Leetcode.Easy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 937. Reorder Data in Log Files
 * You have an array of logs.  Each log is a space delimited string of words.
 *
 * For each log, the first word in each log is an alphanumeric identifier.  Then, either:
 * Each word after the identifier will consist only of lowercase letters, or;
 * Each word after the identifier will consist only of digits.
 * We will call these two varieties of logs letter-logs and digit-logs.
 * It is guaranteed that each log has at least one word after its identifier.
 *
 * Reorder the logs so that all of the letter-logs come before any digit-log.
 * The letter-logs are ordered lexicographically ignoring identifier, with the identifier used in case of ties.
 * The digit-logs should be put in their original order.
 * Return the final order of the logs.
 *
 * Example 1:
 * Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
 * Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
 *
 * Constraints:
 * 0 <= logs.length <= 100
 * 3 <= logs[i].length <= 100
 * logs[i] is guaranteed to have an identifier, and a word after the identifier.
 */
public class LC0937 {
	/**
	 * Time = O(nlogn)
	 * Space = O(n)
	 */
	public String[] reorderLogFiles(String[] logs) {
		if (logs.length == 0) {
			return logs;
		}

		// o1 digit, o2 digit, return 0, keep origin order
		// o1 digit, o2 not digit, return 1
		// o1 not digit, o2 digit, return  -1
		// o1 not, o2 not, compare
		Arrays.sort(logs, new Comparator<String>(){
			public int compare(String o1, String o2) {
				String[] arr1 = o1.split(" ");
				String[] arr2 = o2.split(" ");

				char mark1 = arr1[1].charAt(0);
				char mark2 = arr2[1].charAt(0);
				if (Character.isDigit(mark1)) {
					if (Character.isDigit(mark2)) {
						return 0;
					} else {
						return 1;
					}
				} else if (Character.isDigit(mark2)) {
					return -1;
				} else {
					int i = 1, j = 1;
					while (i < arr1.length && j < arr2.length) {
						if (!arr1[i].equals(arr2[j])) {
							return arr1[i].compareTo(arr2[j]);
						}
						i++;
						j++;
					}
					if (i < arr1.length) {
						return 1;
					}
					if (j < arr1.length) {
						return -1;
					}
				}
				//compare identifier
				return arr1[0].compareTo(arr2[0]);
			}
		});

		return logs;
	}
}
