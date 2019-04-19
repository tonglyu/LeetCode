package Leetcode.Medium;

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
	class Point {
		int x;
		int y;
		double dis;
		public Point(int x, int y, double dis) {
			this.x = x;
			this.y = y;
			this.dis = dis;
		}
	}

	/**
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

		PriorityQueue<Point> pq = new PriorityQueue<>(new Comparator<Point>(){
			public int compare(Point o1, Point o2) {
				return (int)(o2.dis - o1.dis);
			}
		});

		for (int[] p: points) {
			double dis = Math.pow(p[0],2) + Math.pow(p[1],2);
			pq.offer(new Point(p[0],p[1],dis));
			if (pq.size() > K) {
				pq.poll();
			}
		}

		int[][] res = new int[K][2];
		int idx = K - 1;
		while (!pq.isEmpty()) {
			Point p = pq.poll();
			res[idx--] = new int[]{p.x, p.y};
		}

		return res;
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
