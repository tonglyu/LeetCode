package Leetcode.Medium;

import java.util.*;

/**
 * 332. Reconstruct Itinerary
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK.
 * Thus, the itinerary must begin with JFK.
 *
 * Note:
 * If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.
 * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
 * All airports are represented by three capital letters (IATA code).
 * You may assume all tickets form at least one valid itinerary.
 *
 * Example 1:
 * Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 * Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
 *
 * Example 2:
 * Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
 *              But it is larger in lexical order.
 */
public class LC0332 {
	/**
	 * Solution1: brute force dfs
	 * This is a graph problem, we need to find one path across all nodes with all edges
	 * Map<node, list of neighbors>: sort neighbors in alphabet order
	 *
	 * DFS:
	 * base case: when a node in map has no out degree, just return
	 * For each node in neighbors:
	 *      add node into res, remove from neighbors and go to next level
	 *      if path length == #tickets + 1: return
	 *      remove node in res, add in neighbors in original index
	 *
	 * Time = build graph O(n + nlogn) + dfs O(n!)
	 * Space = O(n)
	 */
	public List<String> findItinerary(List<List<String>> tickets) {
		List<String> res = new ArrayList<>();
		Map<String, List<String>> map = new HashMap<>();

		for (List<String> t: tickets) {
			if (map.get(t.get(0)) == null) {
				map.put(t.get(0), new ArrayList<>());
			}
			map.get(t.get(0)).add(t.get(1));
		}

		for (List<String> list: map.values()) {
			Collections.sort(list);
		}

		res.add("JFK");
		dfs("JFK", map, tickets.size() + 1, res);
		return res;
	}

	private void dfs(String node, Map<String, List<String>> map, int count, List<String> res) {
		if (map.get(node) == null) {
			return;
		}

		List<String> neighbors = map.get(node);
		for (int i = 0; i < neighbors.size(); ++i) {
			String des = neighbors.get(i);
			res.add(des);
			neighbors.remove(i);
			dfs(des, map, count, res);
			if (res.size() == count) {
				return;
			}
			neighbors.add(i, des);
			res.remove(res.size() - 1);
		}
	}

	/**
	 * Solution2: Hierholzer algorithm
	 * Eulerian path, is a trial in a finite graph which visits every edge exactly once.
	 *
	 * A directed graph exit a eulerian path iff:
	 *      The graph is connected
	 *      Meet one of the following condition:
	 *          1) Only exist only one node whose outdegree = indegree + 1, this node can be the start; and another only node whose outdegree = indegree - 1, which is end. Other nodes in = out
	 *          2) All nodes indegree = outdegree
	 *
	 * Find an Eulerian path in a graph: Hierholzer algorithm
	 * path = []
	 * dfs(u):
	 *      while edge(u,v) exists:
	 *          mark edge(u,v) visited
	 *          dfs(v)
	 *      end
	 *      path.addFirst(u)
	 *
	 * Put the neighbors in a min-heap. In this way, we always visit the smallest possible neighbor first in our trip.
	 *
	 * Time = build graph O(n + nlogn) + dfs O(n)
	 * Space = O(n)
	 */
	public List<String> findItinerary2(List<List<String>> tickets) {
		List<String> res = new ArrayList<>();
		Map<String, PriorityQueue<String>> map = new HashMap<>();

		for (List<String> t: tickets) {
			if (map.get(t.get(0)) == null) {
				map.put(t.get(0), new PriorityQueue<>());
			}
			map.get(t.get(0)).offer(t.get(1));
		}

		dfs("JFK",map,res);
		return res;
	}

	private void dfs(String node, Map<String, PriorityQueue<String>> map, List<String> res) {
		PriorityQueue<String> q = map.get(node);

		while (q != null && !q.isEmpty()) {
			String des = q.poll();
			dfs(des, map, res);
		}

		res.add(0, node);
	}

	public static void main(String[] args) {
		LC0332 sol = new LC0332();
		String[][] tickets = new String[][]{{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}};

		List<List<String>> input = new ArrayList<>();
		for (String[] t:tickets) {
			input.add(Arrays.asList(t));
		}
		System.out.println(sol.findItinerary2(input));
	}


}
