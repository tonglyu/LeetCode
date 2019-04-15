package Leetcode.Easy;

import Leetcode.ListNode;

/**
 * 141. Linked List Cycle
 * Given a linked list, determine if it has a cycle in it.
 *
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 *
 * Follow up:
 * Can you solve it using O(1) (i.e. constant) memory?
 */
public class LC0141 {
	/**
	 * Data structure: fast, slow pointers
	 *
	 * init: slow = fast = head;
	 * fast == null || fast.next == null -> means no cycle
	 * slow = slow.next;
	 * fast = fast.next.next;
	 * fast == slow -> has cycle
	 *
	 * Time = O(n)
	 * Space  = O(1)
	 */
	public boolean hasCycle(ListNode head) {
		if (head == null || head.next == null) {
			return false;
		}

		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		LC0141 sol = new LC0141();
		ListNode h1 = new ListNode(3);
		h1.next = new ListNode(2);
		h1.next.next = new ListNode(0);
		h1.next.next.next = new ListNode(-4);
		h1.next.next.next.next = h1.next;

		System.out.println(sol.hasCycle(h1));
	}
}