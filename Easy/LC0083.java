package Leetcode.Easy;

import Leetcode.ListNode;

/**
 * 83. Remove Duplicates from Sorted List
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * Example 1:
 * Input: 1->1->2
 * Output: 1->2
 *
 * Example 2:
 * Input: 1->1->2->3->3
 * Output: 1->2->3
 */
public class LC0083 {
	/**
	 * Iterative way:
	 * while curr is not the last node:
	 * if cur.val == cur.next.val: link to the next node
	 * else: move forward curr
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode curr = head;
		while (curr.next != null) {
			if (curr.val == curr.next.val) {
				curr.next = curr.next.next;
			} else {
				curr = curr.next;
			}
		}

		return head;
	}

	public static void main(String[] args) {
		LC0083 sol = new LC0083();
		ListNode head = new ListNode(1);
		head.next = new ListNode(1);
		head.next.next = new ListNode(2);
		head.next.next.next = new ListNode(3);
		head.next.next.next.next = new ListNode(3);

		ListNode newHead = sol.deleteDuplicates(head);
		while (newHead != null) {
			System.out.println(newHead.val);
			newHead = newHead.next;
		}
	}
}
