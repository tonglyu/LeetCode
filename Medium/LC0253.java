package Leetcode.Medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 253. Meeting Rooms II
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 *
 * Example 1:
 * Input: [[0, 30],[5, 10],[15, 20]]
 * Output: 2
 *
 * Example 2:
 * Input: [[7,10],[2,4]]
 * Output: 1
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
 */
public class LC0253 {
	/**
	 * Method1: Greedy algorithm
	 * Sort the events by start time, maintain a min-heap to store end time of all conflicting events, which must be resolved by independent rooms.
	 * The heap's head is the event that has earliest end/finish time. All other events collide with each other mutually in the heap.
	 *
	 * When a new event comes, we greedily choose the event A that finished the earliest:
	 *  Case1: new event does not collide with A, then the new event can re-use A's room, or simply extend A's room to the new event's end time.
	 *  Case2: new event collides with A, then it must collide with all events in the heap. So a new room must be created.
	 *
	 * Time = O(nlogn)
	 * Space = O(n)
	 */
	public int minMeetingRooms(int[][] intervals) {
		if (intervals.length == 0) {
			return 0;
		}

		PriorityQueue<Integer> allocator = new PriorityQueue<Integer>(intervals.length, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return a - b;
			}
		});

		Arrays.sort(intervals, new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				return o1[0] - o2[0];
			}
		});

		allocator.add(intervals[0][1]);
		for (int i = 1; i < intervals.length; i++) {
			// If the room due to free up the earliest is free, assign that room to this meeting.
			if (intervals[i][0] >= allocator.peek()) {
				allocator.poll();
			}

			// If a new room is to be assigned, then also we add to the heap,
			// If an old room is allocated, then also we have to add to the heap with updated end time.
			allocator.add(intervals[i][1]);
		}

		return allocator.size();
	}

	/**
	 * Method2:
	 * active: the least meeting rooms are currently used
	 * rooms: the result, min rooms required
	 * 1) Sort the start time and end time array, set two pointers i,j for two arrays
	 * 2) Move two pointers forward,
	 *      Case1: if start[i] < end[j], means we need to allocate a new room,
	 *          active++; update rooms; i++
	 *      Case2: start[i] <= end[j], means a meeting ends,
	 *          active--; j++
	 *
	 * Time = O(nlogn + n) = O(logn)
	 * Space = O(n)
	 */
	public int minMeetingRooms2(int[][] intervals) {
		if (intervals.length == 0) {
			return 0;
		}

		int[] starts = new int[intervals.length];
		int[] ends = new int[intervals.length];

		for (int i = 0; i < intervals.length; i++) {
			starts[i] = intervals[i][0];
			ends[i] = intervals[i][1];
		}

		Arrays.sort(starts);
		Arrays.sort(ends);

		int active = 0;
		int rooms = 0;

		int i = 0, j = 0;

		while (i < starts.length) {
			if (starts[i] < ends[j]) {
				active++;
				rooms = Math.max(active, rooms);
				i++;
			} else {
				active--;
				j++;
			}
		}

		return rooms;
	}

	public static void main(String[] args) {
		LC0253 sol = new LC0253();
		int[][] intervals = new int[][]{{0, 30}, {5, 10}, {15, 20}};

		int rooms = sol.minMeetingRooms2(intervals);
		System.out.println(rooms);
	}
}
