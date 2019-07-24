package Leetcode.Medium;

import Leetcode.ListNode;

/**
 * 82. Remove Duplicates from Sorted List II
 * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
 *
 * Example 1:
 * Input: 1->2->3->3->4->4->5
 * Output: 1->2->5
 *
 * Example 2:
 * Input: 1->1->1->2->3
 * Output: 2->3
 */
public class LC0082 {
	/**
	 * Iterative way: two pointers, prev and curr
	 * Init: prev = dummy, curr = head
	 * if curr.val = curr.next.val
	 *      1) move curr to the last duplicate
	 *      2) curr = curr.next;
	 *      3) prev.next = curr; //link prev to the curr
	 * else:
	 *      move forward prev and curr
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode dummy = new ListNode(0);
		dummy.next = head;

		ListNode prev = dummy, curr = head;
		while (curr != null && curr.next != null) {
			if (curr.val == curr.next.val) {
				while (curr.next != null && curr.val == curr.next.val) {
					curr = curr.next;
				}
				curr = curr.next;
				prev.next = curr;
			} else {
				prev = curr;
				curr = curr.next;
			}
		}

		return dummy.next;
	}

	public static void main(String[] args) {
		LC0082 sol = new LC0082();
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(3);
		head.next.next.next.next = new ListNode(4);
		head.next.next.next.next.next = new ListNode(4);
		head.next.next.next.next.next.next = new ListNode(5);

		ListNode newHead = sol.deleteDuplicates(head);
		while (newHead != null) {
			System.out.println(newHead.val);
			newHead = newHead.next;
		}
	}
}
