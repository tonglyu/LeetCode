package Leetcode.Medium;

import java.util.*;

/**
 * 207. Course Schedule
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
 *
 * Example 1:
 * Input: 2, [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 *              To take course 1 you should have finished course 0. So it is possible.
 *
 * Example 2:
 * Input: 2, [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 *              To take course 1 you should have finished course 0, and to take course 0 you should
 *              also have finished course 1. So it is impossible.
 *
 * Note:
 * The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
 * You may assume that there are no duplicate edges in the input prerequisites.
 */
public class LC0207 {
	/**
	 * Solution1: DFS Topological sort
	 * well explained by Huahua's video https://www.youtube.com/watch?v=M6SBePBMznU
	 *
	 * The question equals to find if there is a cycle in directed graph
	 * visited[i]: represent the status of each node
	 * 0 represents unvisited, 1 represents visiting, 2 represents visited (all neighbors)
	 *
	 * for each unvisited node:
	 *      if hasCycle(node) cannot finish course
	 *
	 * hasCycle(node):
	 *      if the node is visiting: cycle
	 *      else visited: no cycle in current path
	 *
	 *      mark node as visiting
	 *      for neighbors of node:
	 *            if neighbors has cycle, return cycle
	 *      mark node as visited
	 *
	 *      return no cycle;
	 *
	 * Time = O(V + E)
	 * Space = O(V)
	 */
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		if (numCourses <= 0 || prerequisites == null)
			return true;

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
				if (hasCycle(graph, i, visited)) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean hasCycle(Map<Integer, List<Integer>> graph, int node, int[] visited) {
		if (visited[node] == 1) {
			return true;
		}

		if (visited[node] == 2) {
			return false;
		}

		visited[node] = 1;
		for (int neighbor: graph.get(node)) {
			if (hasCycle(graph, neighbor, visited))
				return true;
		}
		visited[node] = 2;

		return false;
	}

	/**
	 * Solution2: BFS
	 * Topological algorithm: put NODE with 0 indgree into the queue, then make indegree of NODE's successor dereasing 1. Keep the above steps with BFS.
	 * Finally, if each node' indgree equals 0, then it is validated DAG (Directed Acyclic Graph), which means the course schedule can be finished.
	 *
	 * Time = O(V + E)
	 * Space = O(V)
	 */
	public boolean canFinish2(int numCourses, int[][] prerequisites) {
		if (numCourses <= 0 || prerequisites == null)
			return true;

		int[] inDegree = new int[numCourses];
		Map<Integer, List<Integer>> graph = new HashMap<>();

		for (int i = 0; i < numCourses; i++) {
			graph.put(i, new ArrayList<>());
		}

		for (int[] pair: prerequisites) {
			inDegree[pair[0]]++;
			graph.get(pair[1]).add(pair[0]);
		}


		Queue<Integer> q = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (inDegree[i] == 0)
				q.add(i);
		}

		int count = q.size();
		while (!q.isEmpty()) {
			int cur = q.poll();
			for (Integer i: graph.get(cur))  {
				inDegree[i]--;
				if (inDegree[i] == 0) {
					count++;
					q.offer(i);
				}
			}
		}

		return count == numCourses;
	}

	public static void main(String[] args) {
		LC0207 sol = new LC0207();
		int numCourses = 3;
		int[][] prerequisites = new int[][]{{0,1},{0,2},{1,2}};
		System.out.println(sol.canFinish(numCourses, prerequisites));
	}
}
