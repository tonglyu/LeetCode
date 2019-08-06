package Leetcode.Easy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 252. Meeting Rooms
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.
 *
 * Example 1:
 *
 * Input: [[0,30],[5,10],[15,20]]
 * Output: false
 * Example 2:
 *
 * Input: [[7,10],[2,4]]
 * Output: true
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
 */
public class LC0252 {
	/**
	 * Method1:
	 * Compare the intervals with each other, if one pair has an overlap, then return false
	 * Overlap condition:
	 * The earlier meeting ends after the later meeting begins.
	 *
	 * Time = O(n^2)
	 * Space = O(1)
	 */
	public boolean canAttendMeetings(int[][] intervals) {
		for (int i = 0; i < intervals.length; i++) {
			for (int j = i + 1; j < intervals.length; j++) {
				if (overlap(intervals[i], intervals[j]))
					return false;
			}
		}
		return true;
	}

	private boolean overlap(int[] i1, int[] i2) {
		return Math.min(i1[1],i2[1]) > Math.max(i1[0],i2[0]);
		// i1[0] < i2[1] && i2[0] < i1[1]
	}

	/**
	 * Method2: Sorting
	 * The idea here is to sort the meetings by starting time.
	 * Then, go through the meetings one by one and make sure that each meeting ends before the next one starts.
	 *
	 * Time = O(nlogn + n) = O(nlogn)
	 * Space = O(1)
	 */
	public boolean canAttendMeetings2(int[][] intervals) {
		Comparator<int[]> myComparator = new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0] - o2[0];
			}
		};

		Arrays.sort(intervals, myComparator);
		for (int i = 0; i < intervals.length - 1; i++) {
			if (intervals[i][1] > intervals[i + 1][0]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		LC0252 sol = new LC0252();
		int[][] intervals = new int[][]{{0,30},{5,10},{15,20}};

		System.out.println(sol.canAttendMeetings2(intervals));
	}
}
