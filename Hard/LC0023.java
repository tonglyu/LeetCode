package Leetcode.Hard;

import Leetcode.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 23. Merge k Sorted Lists
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 *
 * Example:
 * Input:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * Output: 1->1->2->3->4->4->5->6
 */
public class LC0023 {
	/**
	 * Data structure: leftNodes record how many heads left are not null
	 * When leftNodes > 0:
	 *      1) Find the min node O(k)
	 *      2) construct cur list
	 *      3) lists[min] = lists[min].next; if lists[min] == null, leftNodes--
	 *
	 * Time = O((k + 1) * n)
	 * Space = O(1)
	 */
	public ListNode mergeKLists(ListNode[] lists) {
		if (lists.length == 0) {
			return null;
		}
		int leftNodes = 0;
		for (ListNode node: lists) {
			if (node != null) {
				leftNodes++;
			}
		}
		ListNode dummy = new ListNode(0);
		ListNode cur = dummy;
		while (leftNodes > 0) {
			int min = -1;
			for (int i = 0; i < lists.length; i++) {
				if (lists[i] != null && (min == -1 || lists[i].val < lists[min].val)) {
					min = i;
				}
			}
			cur.next = new ListNode(lists[min].val);
			cur = cur.next;
			lists[min] = lists[min].next;
			if (lists[min] == null) {
				leftNodes--;
			}
		}
		return dummy.next;
	}

	/**
	 * Data structure: use PQ to optimize
	 * Step1: add all heads to PQ (care about null)
	 * Step2: while pq is not empty:
	 *      1) poll the top, add to list
	 *      2) if (top.next) != null, push into pq
	 *
	 * Time = O(n * logk)
	 * Space = O(k)
	 */
	public ListNode mergeKLists2(ListNode[] lists) {
		if (lists.length == 0) {
			return null;
		}

		PriorityQueue<ListNode> pq = new PriorityQueue<>(new Comparator<ListNode>() {
			@Override
			public int compare(ListNode o1, ListNode o2) {
				return o1.val - o2.val;
			}
		});

		for (ListNode node: lists) {
			if (node != null) {
				pq.offer(node);
			}
		}

		ListNode dummy = new ListNode(0);
		ListNode cur = dummy;
		while (!pq.isEmpty()) {
			ListNode node = pq.poll();
			cur.next = node;
			if (node.next != null) {
				pq.offer(node.next);
			}
			cur = cur.next;
		}

		return dummy.next;
	}

	public static void main(String[] args) {
		LC0023 sol = new LC0023();
		ListNode h1 = new ListNode(2);
		//h1.next = new ListNode(2);
		//h1.next.next = new ListNode(4);
		ListNode h2 = new ListNode(-1);

		ListNode[] lists = new ListNode[2];
		lists[0] = h1;
		lists[1] = null;
		//lists[2] = h2;

		ListNode head = sol.mergeKLists2(lists);
		while (head != null) {
			System.out.println(head.val);
			head = head.next;
		}
	}
}
