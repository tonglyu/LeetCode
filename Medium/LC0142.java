package Leetcode.Medium;

import Leetcode.ListNode;

/**
 * 142. Linked List Cycle II
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 * Note: Do not modify the linked list.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 * Follow up:
 * Can you solve it without using extra space?
 */
public class LC0142 {
	/**
	 * Data structure: slow and fast
	 *
	 * x       a      y     b   z
	 * 1 -- 2 -- 8 -- 0 -- 3 -- 4
	 *                |         | c
	 *                6    --   7
	 *
	 * Assume: x is start node, y is enter of cycle, z is meeting point
	 * len(x,y) = a, len(y,z) = b, len(z, y) = c
	 * We know that when slow == fast
	 *      path(fast) = 2*path(slow) -> 2 (a + b) = a + b + c + b
	 *      -> a = c
	 * So when we find the meet point, we start from head and meet point
	 * at each time move 1 step forward, and finally they will meet at y
	 *
	 * Time = O(n)
	 * Space = O(1)
	 * ref: https://www.cnblogs.com/hiddenfox/p/3408931.html
	 */
	public ListNode detectCycle(ListNode head) {
		if (head == null || head.next == null) {
			return null;
		}

		ListNode slow = head;
		ListNode fast = head;
		ListNode meet = null;
		while (fast != null && fast.next !=  null) {
			slow = slow.next;
			fast = fast.next.next;
			if (fast == slow) {
				meet = slow;
				break;
			}
		}

		if (meet != null) {
			slow = head;
			fast = meet;
			while (slow != fast) {
				slow = slow.next;
				fast = fast.next;
			}
			return slow;
		}

		return null;
	}

	public static void main(String[] args) {
		LC0142 sol = new LC0142();
		ListNode h1 = new ListNode(3);
		h1.next = new ListNode(2);
		h1.next.next = new ListNode(0);
		h1.next.next.next = new ListNode(-4);
		h1.next.next.next.next = h1.next;

		System.out.println(sol.detectCycle(h1).val);
	}
}
