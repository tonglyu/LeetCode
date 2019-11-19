package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.PriorityQueue;

/**
 * 1102. Path With Maximum Minimum Value
 * Given a matrix of integers A with R rows and C columns, find the maximum score of a path starting at [0,0] and ending at [R-1,C-1].
 * The score of a path is the minimum value in that path.  For example, the value of the path 8 →  4 →  5 →  9 is 4.
 * A path moves some number of times from one visited cell to any neighbouring unvisited cell in one of the 4 cardinal directions (north, east, west, south).
 *
 * Example 1:
 * Input: [[5,4,5],[1,2,6],[7,4,6]]
 * Output: 4
 * Explanation:
 * The path with the maximum score is highlighted in yellow.
 *
 * Example 2:
 * Input: [[2,2,1,2,2,2],[1,2,2,2,1,2]]
 * Output: 2
 *
 * Example 3:
 * Input: [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
 * Output: 3
 *
 * Note:
 * 1 <= R, C <= 100
 * 0 <= A[i][j] <= 10^9
 */
public class LC1102 {
	/**
	 * Solution: Best-first-search
	 * PQ: each cell records (x,y,min value in current path)
	 * Use a priority queue to choose the next step with the maximum value. Keep track of the mininum value along the path.
	 *
	 * Time = O(m * n)
	 * Space = O(m * n)
	 */
	public int maximumMinimumPath(int[][] A) {
		if (A.length == 0 || A[0].length == 0) {
			return 0;
		}
		int m = A.length;
		int n = A[0].length;

		int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
		int[][] visit = new int[m][n];

		// in the BFS approach, for each step, we are interested in getting the maximum min that we have seen so far,
		// thus we reverse the ordering in the pq
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> (o2[2] - o1[2]));
		pq.offer(new int[]{0,0,A[0][0]});
		visit[0][0] = 1;

		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			if (cur[0] == m - 1 && cur[1] == n - 1) {
				return cur[2];
			}
			for (int i = 0; i < 4; i++) {
				int x = cur[0] + dir[i][0];
				int y = cur[1] + dir[i][1];

				if (x < 0 || x >= m || y < 0 || y >= n || visit[x][y] == 1) {
					continue;
				}
				// we are keeping track of the min element that we have seen until now
				pq.offer(new int[]{x,y,Math.min(cur[2], A[x][y])});
				visit[x][y] = 1;
			}
		}
		// shouldn't get here
		return -1;
	}

	@Test
	public void test1() {
		int[][] A = new int[][]{
				{5,4,5},
				{1,2,6},
				{7,4,6}
		};
		Assert.assertEquals(4,maximumMinimumPath(A));
	}

	@Test
	public void test2() {
		int[][] A = new int[][]{
				{2,2,1,2,2,2},
				{1,2,2,2,1,2}
		};
		Assert.assertEquals(2,maximumMinimumPath(A));
	}

	@Test
	public void test3() {
		int[][] A = new int[][]{
				{3,4,6,3,4},
				{0,2,1,1,7},
				{8,8,3,2,7},
				{3,2,4,9,8},
				{4,1,2,0,0},
				{4,6,5,4,3}};
		Assert.assertEquals(3,maximumMinimumPath(A));
	}

	public static void main(String[] args) {
		JUnitCore.main(LC1102.class.getName());
	}
}
