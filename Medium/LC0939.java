package Leetcode.Medium;

import java.util.*;

/**
 * 939. Minimum Area Rectangle
 * Given a set of points in the xy-plane, determine the minimum area of a rectangle formed from these points, with sides parallel to the x and y axes.
 * If there isn't any rectangle, return 0.
 *
 * Example 1:
 * Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
 * Output: 4
 *
 * Example 2:
 * Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
 * Output: 2
 *
 * Note:
 * 1 <= points.length <= 500
 * 0 <= points[i][0] <= 40000
 * 0 <= points[i][1] <= 40000
 * All points are distinct.
 */
public class LC0939 {
	/**
	 * Solution1: Map
	 * Use a map to maintain all points, key is the rowidx, value is the column idx in that row
	 *
	 * We have two for loops to find p1 and p2:
	 *      if p1 and p2 are in diagonal:
	 *          we find if p2's y in p1'x list and p1's y in p2's list
	 *          which means we have four points in our list
	 *          and then we update the res
	 *
	 * Time = O(n^2)
	 * Space = O(n)
	 */
	public int minAreaRect(int[][] points) {
		Map<Integer, Set<Integer>> map = new HashMap<>();

		for (int[] p: points) {
			if (map.get(p[0]) == null) {
				map.put(p[0], new HashSet<>());
			}
			map.get(p[0]).add(p[1]);
		}

		int res = Integer.MAX_VALUE;
		for (int[] p1: points) {
			for (int[] p2: points) {
				if (p1[0] == p2[0] || p1[1] == p2[1]) {
					continue;
				}

				if (map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) {
					res = Math.min(res, Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]));
				}
			}
		}

		return res == Integer.MAX_VALUE ? 0 : res;
	}

	/**
	 * Solution2: Set
	 * To make it faster, we store all points in set, we can encode with x and y
	 *
	 * Time = O(n^2)
	 * Space = O(n)
	 */
	public int minAreaRect2(int[][] points) {
		Set<Integer> set = new HashSet<>();
		int BASE = 4000000;
		for (int[] p: points) {
			set.add(p[0] * BASE + p[1]);
		}

		int res = Integer.MAX_VALUE;
		for (int[] p1: points) {
			for (int[] p2 : points) {
				if (p1[0] == p2[0] || p1[1] == p2[1]) {
					continue;
				}

				if (set.contains(p1[0] * BASE + p2[1]) && set.contains(p2[0] * BASE + p1[1])) {
					res = Math.min(res, Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]));
				}
			}
		}

		return res == Integer.MAX_VALUE ? 0 : res;
	}

	public static void main(String[] args) {
		LC0939 sol = new LC0939();
		int[][] points = new int[][]{{1,1},{1,3},{3,1},{3,3},{4,1},{4,3}};

		System.out.println(sol.minAreaRect(points));
	}
}
