package Leetcode.Easy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.HashMap;
import java.util.Map;

/**
 * 246. Strobogrammatic Number
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 *
 * Write a function to determine if a number is strobogrammatic. The number is represented as a string.
 *
 * Example 1:
 *
 * Input:  "69"
 * Output: true
 * Example 2:
 *
 * Input:  "88"
 * Output: true
 * Example 3:
 *
 * Input:  "962"
 * Output: false
 */
public class LC0246 {
	/**
	 * Solution: Map
	 * Use a hashmap to store all the valid pairs and compare when scanning the str
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public boolean isStrobogrammatic(String num) {
		if (num.length() == 0) {
			return false;
		}

		Map<Integer, Integer> map = new HashMap<>();
		map.put(1,1);
		map.put(6,9);
		map.put(8,8);
		map.put(9,6);
		map.put(0,0);

		int left = 0, right = num.length() - 1;
		while (left <= right) {
			int l = num.charAt(left) - '0';
			int r =  num.charAt(right) - '0';
			if (map.get(l) == null || map.get(l) != r) {
				return false;
			}

			left++;
			right--;
		}

		return true;
	}

	@Test
	public void test1() {
		String num = "69";
		Assert.assertEquals(isStrobogrammatic(num), true);
	}

	@Test
	public void test2() {
		String num = "25";
		Assert.assertEquals(isStrobogrammatic(num), false);
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0246.class.getName());
	}
}
