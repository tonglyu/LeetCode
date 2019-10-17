package Leetcode.Medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 356. Line Reflection
 * Given n points on a 2D plane, find if there is such a line parallel to y-axis that reflect the given points.
 *
 * Example 1:
 * Input: [[1,1],[-1,1]]
 * Output: true
 *
 * Example 2:
 * Input: [[1,1],[-1,-1]]
 * Output: false
 * Follow up:
 * Could you do better than O(n2)?
 */
public class LC0356 {
	/**
	 * Solution: Use a hashmap to store each points
	 * The median y-line must be in the middle of max and mix x
	 * We can judge if mid - x in the map and their y are same
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public boolean isReflected(int[][] points) {
		if(points.length == 0)
			return true;

		Map<Integer, Integer> map = new HashMap<>();
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int[] p: points) {
			min = Math.min(min, p[0]);
			max = Math.max(max, p[0]);
			map.put(p[0], p[1]);
		}

		int mid = min + max;
		for (int[] p: points) {
			int pair = mid - p[0];
			if (!map.containsKey(pair) || (int)map.get(p[0]) != (int)map.get(pair)) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		LC0356 sol = new LC0356();
		int[][] points = new int[][]{{1,1},{-1,-1}};
		System.out.println(sol.isReflected(points));
		//System.out.println(new BigDecimal(x1).setScale(4,BigDecimal.ROUND_HALF_DOWN));
	}
}
