package Leetcode.Medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 973. K Closest Points to Origin
 * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
 * (Here, the distance between two points on a plane is the Euclidean distance.)
 * You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
 *
 * Example 1:
 *
 * Input: points = [[1,3],[-2,2]], K = 1
 * Output: [[-2,2]]
 * Explanation:
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].
 *
 * Example 2:
 * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
 * Output: [[3,3],[-2,4]]
 * (The answer [[-2,4],[3,3]] would also be accepted.)
 *
 * Note:
 * 1 <= K <= points.length <= 10000
 * -10000 < points[i][0] < 10000
 * -10000 < points[i][1] < 10000
 */
public class LC0973 {
	/**
	 * Solution1: max-heap
	 * Data structure: max-heap of size k
	 * Define a class Point
	 * The heap keeps kth closest points so far
	 *
	 * Time = O(nlogk)
	 * Space = O(k)
	 */
	public int[][] kClosest(int[][] points, int K) {
		if (points.length == 0 || K <= 0) {
			return new int[0][];
		}

		PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>(){
			public int compare(int[] o1, int[] o2) {
				return o2[0] * o2[0] + o2[1] * o2[1] - o1[0] * o1[0] - o1[1] * o1[1];
			}
		});

		for (int[] p: points) {
			pq.offer(p);
			if (pq.size() > K) {
				pq.poll();
			}
		}

		int[][] res = new int[K][2];
		int idx = K - 1;
		while (!pq.isEmpty()) {
			res[idx--] = pq.poll();
		}

		return res;
	}

	/**
	 * Solution2: quick select
	 * Each iteration, we choose a pivot and then find the position p the pivot should be.
	 * Then we compare p with the K, if the p is smaller than the K, meaning the all element on the left of the pivot are all proper candidates but it is not adequate, we have to do the same thing on right side, and vice versa.
	 * If the p is exactly equal to the K, meaning that we've found the K-th position.
	 * Therefore, we just return the first K elements, since they are not greater than the pivot.
	 *
	 * Time = O(n), worst O(n^2)
	 * Space = O(1)
	 */
	public int[][] kClosest2(int[][] points, int K) {
		int len =  points.length, l = 0, r = len - 1;
		while (l <= r) {
			int mid = helper(points, l, r);
			if (mid == K) break;
			if (mid < K) {
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return Arrays.copyOfRange(points, 0, K);
	}

	private int helper(int[][] A, int l, int r) {
		int[] pivot = A[l];
		while (l < r) {
			while (l < r && compare(A[r], pivot) >= 0) {
				r--;
			}
			A[l] = A[r];
			while (l < r && compare(A[l], pivot) <= 0) {
				l++;
			}
			A[r] = A[l];
		}
		A[l] = pivot;
		return l;
	}

	private int compare(int[] p1, int[] p2) {
		return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
	}

	public static void main(String[] args) {
		LC0973 sol = new LC0973();
		int[][] points = new int[][]{{3,3},{5,-1},{-2,4}};
		int K = 2;
		int[][] res = sol.kClosest(points,K);
		for (int[] p : res) {
			System.out.println(p[0] + "," + p[1]);
		}
	}
}
