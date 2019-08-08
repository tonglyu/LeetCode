package Leetcode.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 56. Merge Intervals
 * Given a collection of intervals, merge all overlapping intervals.
 *
 * Example 1:
 * Input: [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 *
 * Example 2:
 * Input: [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
 */
public class LC0056 {
	/**
	 * Solution1: sorting
	 * 1) Sorting the int[] by start time
	 * 2) check with each interval with previous one
	 *      if cur.start > prev.end: add interval
	 *      else: update prev
	 *          prev.end = Math.max(prev.end, cur.end)
	 *
	 * Time = O(nlogn + n) = O(nlogn)
	 * Space = O(n) for list
	 */
	public int[][] merge(int[][] intervals) {
		if (intervals.length <= 1) {
			return intervals;
		}

		Arrays.sort(intervals, new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				return o1[0] - o2[0];
			}
		});

		List<int[]> res = new ArrayList<>();

		for (int i = 0; i < intervals.length; i++) {
			if (res.size() == 0 || intervals[i][0] > res.get(res.size() - 1)[1]) {
				res.add(intervals[i]);
			} else {
				int[] prev = res.get(res.size() - 1);
				res.get(res.size() - 1)[1] = Math.max(prev[1], intervals[i][1]);
			}
		}

		return res.toArray(new int[res.size()][]);
	}

	public static void main(String[] args) {
		LC0056 sol = new LC0056();
		int[][] intervals = new int[][]{{1,3},{2,6},{8,10},{15,18}};
		int[][] res = sol.merge(intervals);

		for (int[] interval: res) {
			System.out.println(interval[0] + "," + interval[1]);
		}
	}
}
