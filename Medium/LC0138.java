package Leetcode.Medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 138. Copy List with Random Pointer
 * A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
 *
 * Return a deep copy of the list.
 *
 * Example 1:
 * Input:
 * {"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}
 * Explanation:
 * Node 1's value is 1, both of its next and random pointer points to Node 2.
 * Node 2's value is 2, its next pointer points to null and its random pointer points to itself.
 *
 * Note:
 * You must return the copy of the given head as a reference to the cloned list.
 */
public class LC0138 {
	/**
	 * Data structure: hashmap to avoid copy multiple times for the same node
	 * while head != null:
	 *      1) if head is not in map, push <head, copy head>
	 *      2) cur.next = map.get(head);
	 *      3) if random exist:
	 *             if random is not in map, push random
	 *             cur.next.random = map.get(random)
	 *      4) move forward cur and head
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public Node copyRandomList(Node head) {
		if (head == null) {
			return head;
		}

		// Sentinel node to help construct deep copy
		Node dummy = new Node();
		Node cur = dummy;
		// Maintain the mapping between the node in the original list
		// and corresponding node in the new list
		Map<Node,Node> map = new HashMap<>();
		while (head != null) {
			// Copy the current node if necessary
			if (map.get(head) == null) {
				map.put(head, new Node(head.val,null,null));
			}

			// Connect the copied node to the deep copy list
			cur.next = map.get(head);
			if (head.random != null) {
				// Copy the random node if necessary
				if (map.get(head.random) == null) {
					map.put(head.random, new Node(head.random.val, null,null));
				}
				// Connect the copied node to the random pointer
				cur.next.random = map.get(head.random);
			}

			cur = cur.next;
			head = head.next;
		}

		return dummy.next;
	}

	public static void main(String[] args) {
		LC0138 sol = new LC0138();
		Node h1 = new Node(1, null,null);
		Node h2 = new Node(2,null,null);
		h1.next = h2;
		h1.random = h2;

		h2.random = h2;

		Node copy = sol.copyRandomList(h1);

		while (copy != null) {
			System.out.println(copy.val + "," +copy.random.val);
			copy = copy.next;
		}
	}
}

class Node {
	public int val;
	public Node next;
	public Node random;

	public Node() {}

	public Node(int _val,Node _next,Node _random) {
		val = _val;
		next = _next;
		random = _random;
	}
}