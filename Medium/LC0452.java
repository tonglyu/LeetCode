package Leetcode.Medium;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 452. Minimum Number of Arrows to Burst Balloons
 * There are a number of spherical balloons spread in two-dimensional space. For each balloon, provided input is the start and end coordinates of the horizontal diameter.
 * Since it's horizontal, y-coordinates don't matter and hence the x-coordinates of start and end of the diameter suffice. Start is always smaller than end. There will be at most 104 balloons.
 *
 * An arrow can be shot up exactly vertically from different points along the x-axis. A balloon with xstart and xend bursts by an arrow shot at x if xstart ≤ x ≤ xend.
 * There is no limit to the number of arrows that can be shot. An arrow once shot keeps travelling up infinitely. The problem is to find the minimum number of arrows that must be shot to burst all balloons.
 *
 * Example:
 * Input:
 * [[10,16], [2,8], [1,6], [7,12]]
 * Output:
 * 2
 *
 * Explanation:
 * One way is to shoot one arrow for example at x = 6 (bursting the balloons [2,8] and [1,6]) and another arrow at x = 11 (bursting the other two balloons).
 */
public class LC0452 {
	/**
	 * Solution1: Greedy
	 * Sort the balloons by the end coordinate, and then check them one by one.
	 * Iterate over all balloons:
	 *      If the balloon starts after first_end:
	 *          Increase the number of arrows by one.
	 *          Set first_end to be equal to the end of the current balloon.
	 * That means that one could always track the end of the current balloon, and ignore all the balloons which end before it. Once the current balloon is ended (= the next balloon starts after the current balloon),
	 * one has to increase the number of arrows by one and start to track the end of the next balloon.
	 *
	 * Time = O(nlogn)
	 * Space = O(1)
	 */
	public int findMinArrowShots(int[][] points) {
		if (points.length == 0) {
			return 0;
		}

		Arrays.sort(points, new Comparator<int[]>(){
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});

		int num = 1;
		int curEnd = points[0][1];
		for (int[] p: points) {
			if (p[0] > curEnd) {
				num++;
				curEnd = p[1];
			}
		}

		return num;
	}

	public static void main(String[] args) {
		LC0452 sol = new LC0452();
		int[][] points = new int[][]{{10,16}, {2,8}, {1,6}, {7,12}};

		System.out.println(sol.findMinArrowShots(points));
	}
}
