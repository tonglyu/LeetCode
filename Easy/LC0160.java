package Leetcode.Easy;

import Leetcode.ListNode;

/**
 * 160. Intersection of Two Linked Lists
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 *
 * Example 1:
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
 * Output: Reference of the node with value = 8
 * Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,0,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
 *
 * Example 2:
 * Input: intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Reference of the node with value = 2
 * Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [0,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
 *
 * Example 3:
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: null
 * Input Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
 * Explanation: The two lists do not intersect, so return null.
 *
 * Notes:
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * Your code should preferably run in O(n) time and use only O(1) memory.
 */
public class LC0160 {
	/**                x
	 * A          a1 -> a2  \       z
	 *         y              c1 -> c2 -> c3
	 * B    b1 -> b2 -> b3  /
	 *
	 * x is the common length before intersection, z is length after intersection.
	 * y is the length B is longer than A.
	 *
	 * When each list reaches the tail, we set it to another list's head,
	 * in this case, after 1 round, we can get curA = b2, curB = a1
	 * because path of curA = x + z + y, path of cur B = y + x + z
	 *
	 * And move step by step for two pointers until they reaches intersection or the null (no intersection)
	 *
	 * Time = O(m + n)
	 * Space = O(1)
	 */
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		if (headA == null || headB == null) {
			return null;
		}

		ListNode cur1 = headA;
		ListNode cur2 = headB;
		while (cur1 != cur2) {
			if (cur1 == null) {
				cur1 = headB;
			} else {
				cur1 = cur1.next;
			}
			if (cur2 == null) {
				cur2 = headA;
			} else {
				cur2 = cur2.next;
			}
		}

		return cur1;
	}

	public static void main(String[] args) {
		LC0160 sol = new LC0160();
		ListNode cross = new ListNode(8);
		cross.next = new ListNode(4);
		cross.next.next = new ListNode(5);

		ListNode h1 = new ListNode(4);
		h1.next = new ListNode(1);
		h1.next.next = cross;

		ListNode h2 = new ListNode(5);
		h2.next = new ListNode(0);
		h2.next.next = new ListNode(1);
		h2.next.next.next = cross;

		System.out.println(sol.getIntersectionNode(h1,h2).val);
	}
}
