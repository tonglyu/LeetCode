package Leetcode.Easy;

import Leetcode.ListNode;

/**
 * 21. Merge Two Sorted Lists
 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
 *
 * Example:
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 */
public class LC0021 {
	/**
	 * Data structure: dummy head
	 * Similar to merge sort
	 *  1) l1 < l2 -> cur.next = l1;
	 *  2) else cur.next = l2;
	 *  Post processing:
	 *  We do not need while loop like array, we only need if statement.
	 *
	 *  Time = O(m + n)
	 *  Space = O(1)
	 */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        if (l1 != null) {
            cur.next = l1;
        }

        if (l2 != null) {
            cur.next = l2;
        }

        return dummy.next;
    }

	public static void main(String[] args) {
		LC0021 sol = new LC0021();
		ListNode h1 = new ListNode(1);
		h1.next = new ListNode(2);
		h1.next.next = new ListNode(4);

		ListNode h2 = new ListNode(1);
		h2.next = new ListNode(3);
		h2.next.next = new ListNode(4);

		ListNode head = sol.mergeTwoLists(h1,h2);
		while (head != null) {
			System.out.println(head.val);
			head = head.next;
		}
	}
}
