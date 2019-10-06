package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 809. Expressive Words
 * Sometimes people repeat letters to represent extra feeling, such as "hello" -> "heeellooo", "hi" -> "hiiii".  In these strings like "heeellooo", we have groups of adjacent letters that are all the same:  "h", "eee", "ll", "ooo".
 * For some given string S, a query word is stretchy if it can be made to be equal to S by any number of applications of the following extension operation: choose a group consisting of characters c,
 * and add some number of characters c to the group so that the size of the group is 3 or more.
 * For example, starting with "hello", we could do an extension on the group "o" to get "hellooo", but we cannot get "helloo" since the group "oo" has size less than 3.
 * Also, we could do another extension like "ll" -> "lllll" to get "helllllooo".  If S = "helllllooo", then the query word "hello" would be stretchy because of these two extension operations: query = "hello" -> "hellooo" -> "helllllooo" = S.
 *
 * Given a list of query words, return the number of words that are stretchy.
 *
 * Example:
 * Input:
 * S = "heeellooo"
 * words = ["hello", "hi", "helo"]
 * Output: 1
 * Explanation:
 * We can extend "e" and "o" in the word "hello" to get "heeellooo".
 * We can't extend "helo" to get "heeellooo" because the group "ll" is not size 3 or more.
 *
 * Notes:
 * 0 <= len(S) <= 100.
 * 0 <= len(words) <= 100.
 * 0 <= len(words[i]) <= 100.
 * S and all words in words consist only of lowercase letters
 */
public class LC0809 {
	/**
	 * Solution: two pointers
	 * Firstly, for S and word, we can calculate the length of the susbtrings which contains the repeated letters of the letter currently pointed by the two pointers,
	 * and get lenS and lenW.
	 *
	 * The two letters currently pointed by the two pointers must be equal, otherwise the word is not stretchy, we return false.
	 * Case1: if we find that lenS is smaller than 3, it means the letter cannot be extended, so lenS must equals to lenW
	 * Case2: if lenS equals to or larger than 3, we must have lenS smaller than lenW, otherwise there are not enough letters in S to match the letters in word.
	 *
	 * Finally, if the word is stretchy, we need to guarantee that both of the two pointers has scanned the whole string.
	 *
	 * Time = O(m + n)
	 * Space = O(1)
	 */
	public int expressiveWords(String S, String[] words) {
		int res = 0;
		char[] s = S.toCharArray();
		for (String w: words) {
			if (isStretchy(w.toCharArray(), s)) {
				res++;
			}
		}
		return res;
	}

	private boolean isStretchy(char[] w, char[] s) {
		int i = 0, j = 0;
		int m = w.length, n = s.length;

		while (i < m && j < n) {
			if (w[i] == s[j]) {
				int lenW = getRepeat(w, i);
				int lenS = getRepeat(s, j);
				if (lenS < 3 && lenS != lenW || lenS < lenW) {
					return false;
				}
				i += lenW;
				j += lenS;
			} else {
				return false;
			}
		}

		return i == m && j == n;
	}

	private int getRepeat(char[] w, int i) {
		int ori = i;
		while (i < w.length && w[i] == w[ori]) {
			i++;
		}

		return i - ori;
	}

	@Test
	public void test() {
		String S = "heeellooo";
		String[] words = new String[]{"hello", "hi", "helo"};
		Assert.assertEquals(1, expressiveWords(S, words));
	}

	@Test
	public void test1() {
		String S = "abcd";
		String[] words = new String[]{"abc"};
		Assert.assertEquals(0, expressiveWords(S, words));
	}

	@Test
	public void test2() {
		String S = "aaa";
		String[] words = new String[]{"aaaaa"};
		Assert.assertEquals(0, expressiveWords(S, words));
	}

	public static void main(String[] args) {
//		Result result = JUnitCore.runClasses(LC0809.class);
//		List<Failure> failures = result.getFailures();
//		for (int i = 0; i < failures.size(); i++) {
//			System.out.println("No." + i + " test's failure is " + failures.get(i).toString());
//		}
		JUnitCore.main(LC0809.class.getName());
	}
}
