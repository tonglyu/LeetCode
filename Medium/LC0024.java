package Leetcode.Medium;

import Leetcode.ListNode;

/**
 * 24. Swap Nodes in Pairs
 * Given a linked list, swap every two adjacent nodes and return its head.
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 *
 * Example:
 *
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 */
public class LC0024 {
	/**
	 * Solution1: iterative
	 * Iterate util no nodes left or one node left
	 * Each time
	 * 1. connect prev with cur.next
	 * 2. update cur.next = cur.next.next
	 * 3. connect prev.next.next = cur
	 *
	 * Move forward prev and cur
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public ListNode swapPairs(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode dummy = new ListNode(0), pre = dummy, cur = head;
		while (cur != null && cur.next != null) {
			pre.next = cur.next;
			cur.next = cur.next.next;
			pre.next.next = cur;

			pre = cur;
			cur = cur.next;
		}

		return dummy.next;
	}

	/**
	 * Solution2: recursive
	 * Record the next node
	 * Link head.next to the subquestion result
	 * link next.next = head
	 *
	 * return next
	 *
	 * Time = O(n)
	 * Space = O(n / 2)
	 */
	public ListNode swapPairs2(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode next = head.next;
		head.next = swapPairs(head.next.next);
		next.next = head;
		return next;
	}

		public static void main(String[] args) {
		LC0024 sol = new LC0024();
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);

		ListNode res = sol.swapPairs2(head);
		while (res != null) {
			System.out.println(res.val);
			res = res.next;
		}
	}
}
