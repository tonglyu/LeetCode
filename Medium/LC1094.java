package Leetcode.Medium;

import java.util.*;

/**
 * 1094. Car Pooling
 * You are driving a vehicle that has capacity empty seats initially available for passengers.  The vehicle only drives east (ie. it cannot turn around and drive west.)
 *
 * Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information about the i-th trip:
 * the number of passengers that must be picked up, and the locations to pick them up and drop them off.
 * The locations are given as the number of kilometers due east from your vehicle's initial location.
 *
 * Return true if and only if it is possible to pick up and drop off all passengers for all the given trips.
 *
 * Example 1:
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
 * Output: false
 *
 * Example 2:
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
 * Output: true
 *
 * Example 3:
 * Input: trips = [[2,1,5],[3,5,7]], capacity = 3
 * Output: true
 *
 * Example 4:
 * Input: trips = [[3,2,7],[3,7,9],[8,3,9]], capacity = 11
 * Output: true
 *
 * Constraints:
 * trips.length <= 1000
 * trips[i].length == 3
 * 1 <= trips[i][0] <= 100
 * 0 <= trips[i][1] < trips[i][2] <= 1000
 * 1 <= capacity <= 100000
 */
public class LC1094 {
	/**
	 * Solution1: Sorting + PQ
	 * 1) Sorting the array by start time
	 * 2) for each trip in the array:
	 *      when the end time of top of the queue <= current start time
	 *          pop out element and add extra capacity to whole capacity
	 *      if capacity < current passengers:
	 *          return false
	 *      else:
	 *          minus capacity w/ current passengers
	 *          offer current trip to PQ
	 *
	 * Time = O(nlogn)
	 * Space = O(n)
	 */
	public boolean carPooling(int[][] trips, int capacity) {
		if (trips.length == 0) {
			return true;
		}

		Arrays.sort(trips, new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});

		PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				return o1[2] - o2[2];
			}
		});

		for (int[] trip : trips) {
			// any passengers whose end time is less than or equal to current start time
			// needs to get off
			while (!pq.isEmpty() && pq.peek()[2] <= trip[1]) {
				capacity += pq.poll()[0];
			}

			if (capacity < trip[0]) {
				return false;
			} else {
				capacity -= trip[0];
				pq.offer(trip);
			}
		}

		return true;
	}

	/**
	 * Solution2: TreeMap
	 * Map: maintain the number of passengers to pick up or drop off at the time
	 * Process all trips, adding passenger count to the start location, and removing it from the end location.
	 *
	 * Scan all the value in the treemap, (in time order)
	 *  calculate the capacity, if capacity < 0. return false
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public boolean carPooling2(int[][] trips, int capacity) {
		if (trips.length == 0) {
			return true;
		}

		Map<Integer, Integer> map = new TreeMap<>();
		for (int[] trip: trips) {
			map.put(trip[1], map.getOrDefault(trip[1], 0) + trip[0]);
			map.put(trip[2], map.getOrDefault(trip[2], 0) - trip[0]);
		}

		for (int cap: map.values()) {
			capacity -= cap;
			if (capacity < 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		LC1094 sol = new LC1094();
		int[][] trips = new int[][]{{3,2,7},{3,7,9},{8,3,9}};
		int capacity = 11;

		System.out.println(sol.carPooling2(trips, capacity));
	}
}
