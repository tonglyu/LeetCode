package Leetcode.Hard;

import Leetcode.ListNode;

/**
 * 25. Reverse Nodes in k-Group
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 *
 * Example:
 * Given this linked list: 1->2->3->4->5
 * For k = 2, you should return: 2->1->4->3->5
 * For k = 3, you should return: 3->2->1->4->5
 *
 * Note:
 * Only constant extra memory is allowed.
 * You may not alter the values in the list's nodes, only nodes itself may be changed.
 */
public class LC0025 {
	/**
	 * Solution1: recursive
	 * Iterate k times, we reach k+1 node
	 * if we cannot reach k times, we just return head
	 * otherwise, we reverse the sub list, and connect head to sub result
	 * return newhead
	 *
	 * Time = O(n)
	 * Space = O(n / k)
	 */
	public ListNode reverseKGroup(ListNode head, int k) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode tail = head;
		int cnt = 0;
		while (tail != null && cnt < k) {
			tail = tail.next;
			cnt++;
		}

		if (cnt < k) {
			return head;
		}

		ListNode next = tail;
		ListNode newHead = reverse(head, tail);
		head.next = reverseKGroup(next, k);
		return newHead;
	}

	private ListNode reverse(ListNode head, ListNode tail) {
		ListNode prev = null, cur = head, end = tail;
		while (cur != end) {
			ListNode next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}

		return prev;
	}

	/**
	 * Solution2: iterative
	 * We need to record the prev node, use dummy node
	 * Each time we we meet k nodes:
	 *      we need to maintain the ori head and next after cur
	 *      reverse the list between (prev, cur]
	 *      prev.next = new head;
	 *      orihead.next = next
	 *
	 *      prev = ori head, cur = next
	 * Otherwise, move cur one step forward
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public ListNode reverseKGroup2(ListNode head, int k) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode prev = dummy, cur = head;
		int cnt = 0;
		while (cur != null) {
			cnt++;
			if (cnt % k == 0) {
				ListNode tail = prev.next;
				ListNode next = cur.next;
				prev.next = reverse(prev.next, cur.next);
				tail.next = next;

				prev = tail;
				cur = next;
			} else {
				cur = cur.next;
			}
		}

		return dummy.next;
	}

	public static void main(String[] args) {
		LC0025 sol = new LC0025();
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);

		ListNode res = sol.reverseKGroup2(head, 3);
		while (res != null) {
			System.out.println(res.val);
			res = res.next;
		}
	}
}
