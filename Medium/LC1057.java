package Leetcode.Medium;

import java.util.*;

/**
 * 1057. Campus Bikes
 * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
 *
 * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we choose the pair with the smallest bike index). We repeat this process until there are no available workers.
 * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
 * Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
 *
 * Example 1:
 * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
 * Output: [1,0]
 * Explanation:
 * Worker 1 grabs Bike 0 as they are closest (without ties), and Worker 0 is assigned Bike 1. So the output is [1, 0].
 *
 * Example 2:
 * Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
 * Output: [0,2,1]
 * Explanation:
 * Worker 0 grabs Bike 0 at first. Worker 1 and Worker 2 share the same distance to Bike 2, thus Worker 1 is assigned to Bike 2, and Worker 2 will take Bike 1. So the output is [0,2,1].
 *
 * Note:
 * 0 <= workers[i][j], bikes[i][j] < 1000
 * All worker and bike locations are distinct.
 * 1 <= workers.length <= bikes.length <= 1000
 */
public class LC1057 {
	/**
	 * Solution1: Greedy + PQ
	 * Maintain a pq of bike and worker pairs. The heap order should be Distance ASC, WorkerIndex ASC, Bike ASC
	 * Thus, when we pop out the element from pq, we can know that this is the current minimum distance for the pair, and should be assigned first.
	 * Set<>: maintain all bikes that are assigned
	 *
	 * when all workers are assigned with a bike, we should jump out of the loop
	 *
	 * Time = O(m * n + n * log(m * n)) -> O(m * n)
	 * Space = O(m * n)
	 */
	public int[] assignBikes(int[][] workers, int[][] bikes) {
		int n = workers.length;
		int m = bikes.length;
		int[] res = new int[n];
		Arrays.fill(res, -1);

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
			if (a[0] != b[0]) {
				return a[0] - b[0];
			} else if (a[1] != b[1]) {
				return a[1] - b[1];
			} else {
				return a[2] - b[2];
			}
		});

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int dis = getDis(workers[i], bikes[j]);
				pq.offer(new int[]{dis, i, j});
			}
		}

		Set<Integer> assigned = new HashSet<>();
		while (assigned.size() < n) {
			int[] pair = pq.poll();
			if (res[pair[1]] == -1 && !assigned.contains(pair[2])) {
				res[pair[1]] = pair[2];
				assigned.add(pair[2]);
			}
		}

		return res;
	}

	/**
	 * Solution2: Bucket Sort
	 * Since 0 <= workers[i][j], bikes[i][j] < 1000, all distances are between [0,2000)
	 * So we can use bucket sort, and find the smaller distance pair.
	 *
	 * When we add pair into the bucket, we already follow the order, the worker with smaller id will be pushed first,
	 * because we are iterating from worker_id 0 to the n - 1. The case for bike_id is similar. In the assignment part.
	 *
	 * Time = O(m * n)
	 * Space = O(2000)
	 */
	public int[] assignBikes2(int[][] workers, int[][] bikes) {
		int n = workers.length;
		int m = bikes.length;

		List<int[]>[] bucket = new List[2000];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int dis = getDis(workers[i], bikes[j]);
				if (bucket[dis] == null) {
					bucket[dis] = new ArrayList<>();
				}
				bucket[dis].add(new int[]{i,j});
			}
		}

		int[] res = new int[n];
		Arrays.fill(res, -1);
		boolean[] used = new boolean[m];
		int assigned = 0;
		for (int i = 0; i < 2000 && assigned < n; i++) {
			List<int[]> pairs = bucket[i];
			if (pairs == null) continue;
			for (int[] pair: pairs) {
				if (res[pair[0]] == -1 && !used[pair[1]]) {
					res[pair[0]] = pair[1];
					used[pair[1]] = true;
					assigned++;
				}
			}
		}

		return res;
	}

	private int getDis(int[] worker, int[] bike) {
		return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
	}

	public static void main(String[] args) {
		LC1057 sol = new LC1057();
		int[][] workers = new int[][]{{0, 0}, {2, 1}};
		int[][] bikes = new int[][]{{1, 2}, {3, 3}};

		for (int id: sol.assignBikes(workers,bikes)) {
			System.out.println(id);
		}
	}
}
