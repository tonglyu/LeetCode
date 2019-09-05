package Leetcode.Medium;

import java.util.*;

/**
 * 210. Course Schedule II
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.
 *
 * Example 1:
 * Input: 2, [[1,0]]
 * Output: [0,1]
 * Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
 *              course 0. So the correct course order is [0,1] .
 *
 * Example 2:
 * Input: 4, [[1,0],[2,0],[3,1],[3,2]]
 * Output: [0,1,2,3] or [0,2,1,3]
 * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
 *              courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 *              So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
 *
 * Note:
 * The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 */
public class LC0210 {
	/**
	 * Solution1: BFS
	 * The algorithm is the same as 207. Course Schedule
	 *
	 * Each time we pop up a course from queue, we add it into the result since all course in the queue are those with 0 in degree.
	 * We still need to maintain a variable to record the amount of courses with 0 in degree.
	 * If that number does not equal to the number of courses, that means there is a cycle, we return empty array.
	 *
	 * Time = O(V + E)
	 * Space = O(V)
	 */
	public int[] findOrder(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int i = 0; i < numCourses; i++) {
			graph.put(i, new ArrayList<>());
		}

		int[] inDegree = new int[numCourses];
		for (int[] pair: prerequisites) {
			inDegree[pair[0]]++;
			graph.get(pair[1]).add(pair[0]);
		}

		int[] res = new int[numCourses];
		Queue<Integer> q = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (inDegree[i] == 0) {
				q.add(i);
			}
		}

		int idx = 0;
		int count = q.size();
		while (!q.isEmpty()) {
			int cur = q.poll();
			res[idx++] = cur;
			for (int neighbor: graph.get(cur)) {
				inDegree[neighbor]--;
				if (inDegree[neighbor] == 0) {
					count++;
					q.offer(neighbor);
				}
			}
		}

		return count == numCourses ? res : new int[0];
	}

	/**
	 * Solution2: DFS
	 * Basic idea is same, the only concerned thing is the order of visited nodes added into result list,
	 * which is the reverse order of visited.
	 *
	 * Time = O(V + E)
	 * Space = O(V)
	 */
	public int[] findOrder2(int numCourses, int[][] prerequisites) {
		List<Integer> res = new ArrayList<>();
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int i = 0; i < numCourses; i++) {
			graph.put(i, new ArrayList<>());
		}

		for (int[] pairs: prerequisites) {
			graph.get(pairs[1]).add(pairs[0]);
		}

		int[] visited = new int[numCourses];
		for (int i = 0; i < numCourses; i++) {
			if (visited[i] == 0) {
				if (hasCycle(graph,i,visited,res)) {
					return new int[0];
				}
			}
		}

		return res.stream().mapToInt(i->i).toArray();
	}

	private boolean hasCycle(Map<Integer, List<Integer>> graph, int node, int[] visited, List<Integer> res) {
		if (visited[node] == 1) {
			return true;
		}

		if (visited[node] == 2) {
			return false;
		}

		List<Integer> neighbors = graph.get(node);
		visited[node] = 1;

		for (Integer neighbor: neighbors) {
			if (hasCycle(graph, neighbor, visited, res)) {
				return true;
			}
		}
		visited[node] = 2;
		res.add(0, node);

		return false;
	}

	public static void main(String[] args) {
		LC0210 sol = new LC0210();
		int numCourses = 4;
		int[][] prerequisites = new int[][]{{1,0},{2,0},{3,1},{3,2}};
		int[] order = sol.findOrder(numCourses, prerequisites);

		for (int course: order) {
			System.out.println(course);
		}
	}
}
