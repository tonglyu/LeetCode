package Leetcode.Hard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 564. Find the Closest Palindrome
 * Given an integer n, find the closest integer (not including itself), which is a palindrome.
 *
 * The 'closest' is defined as absolute difference minimized between two integers.
 * Example 1:
 * Input: "123"
 * Output: "121"
 *
 * Note:
 * The input n is a positive integer represented by string, whose length will not exceed 18.
 * If there is a tie, return the smaller one as answer.
 */
public class LC0564 {
	/**
	 * Solution:
	 * For abcde, we can change it to abcba to make it as the candidate close palindrome
	 * 10987
	 * However, we need to consider about to conditions:
	 * 1. the mid digit is 0, which means we might get a closer number by substract 1 from the first mid part. 20001 200xx -> 199xx
	 * 2. the mid digit is 9, which means we might get a closer number by adding 1 from the first part, 10987 -> 110xx
	 *
	 * So, we can get 3 candidates and return the minimum difference one
	 *
	 * Time = O(l)
	 * Space = O(l)
	 */
	public String nearestPalindromic(String n) {
		if (n.equals("1")) {
			return "0";
		}

		int len = n.length();
		int mid = (len + 1) / 2;
		String res1 = helper(n);
		long diff1 = Math.abs(Long.parseLong(n) - Long.parseLong(res1));
		diff1 = diff1 == 0 ? Long.MAX_VALUE : diff1;

		StringBuilder s2 = new StringBuilder(n);
		int i = mid - 1;
		// check from the mid position, if it is 0, then change it to 9
		while (i >= 0 && s2.charAt(i) == '0') {
			s2.setCharAt(i, '9');
			i--;
		}
		// if we meet at the first position and is 1, which means 1000 -> 0900
		// we need to remove the first char, and set the latest mid as '9'
		if (i == 0 && s2.charAt(i) == '1') {
			//1000 -> 900 -> 990
			s2.deleteCharAt(0);
			//we need to set the after mid position element to 9
			s2.setCharAt(mid - 1, '9');
		} else {
			// otherwise, we only decrease digit by 1, 200 -> 199
			s2.setCharAt(i, (char)(s2.charAt(i) - 1));
		}
		String res2 = helper(s2.toString());
		long diff2 = Math.abs(Long.parseLong(n) - Long.parseLong(res2));


		StringBuilder s3 = new StringBuilder(n);
		i = mid - 1;
		// check from the mid position, if it is 9, then change it to 0
		while (i >= 0 && s3.charAt(i) == '9') {
			s3.setCharAt(i, '0');
			i--;
		}
		// if i is out of bound, which means the number start from '9xxx', we need to add 1 as the first digit
		if (i < 0) {
			s3.insert(0,'1');
		} else {
			// otherwise, we only increase digit by 1, 399 -> 409
			s3.setCharAt(i, (char)(s3.charAt(i) + 1));
		}
		String res3 = helper(s3.toString());
		long diff3 = Math.abs(Long.parseLong(n) - Long.parseLong(res3));

		// always return the smaller one first
		if (diff2 <= diff3 && diff2 <= diff1) {
			return res2;
		} else if (diff1 <= diff3 && diff1 <= diff2) {
			return res1;
		} else {
			return res3;
		}
	}

	private String helper(String s) {
		char[] arr = s.toCharArray();

		for (int i = 0; i < arr.length; i++) {
			arr[arr.length - 1 - i] = arr[i];
		}

		return new String(arr);
	}

	@Test
	public void test1() {
		String s = "2";
		Assert.assertEquals("1", nearestPalindromic(s));
	}

	@Test
	public void test2() {
		String s = "12";
		Assert.assertEquals("11", nearestPalindromic(s));
	}

	@Test
	public void test3() {
		String s = "100000";
		Assert.assertEquals("99999", nearestPalindromic(s));
	}

	@Test
	public void test4() {
		String s = "299";
		Assert.assertEquals("303", nearestPalindromic(s));
	}

	@Test
	public void test5() {
		String s = "999";
		Assert.assertEquals("1001", nearestPalindromic(s));
	}


	@Test
	public void test6() {
		String s = "123";
		Assert.assertEquals("121", nearestPalindromic(s));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0564.class.getName());
	}
}
