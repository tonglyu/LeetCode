package Leetcode.Medium;

import Leetcode.ListNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * 2. Add Two Numbers
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example:
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */
public class LC0002 {
    /**
     * Data structure: dummy node to store the head
     * Steps:
     * Traverse two list with a carry, value = l1.val + l2.val + carry
     * while there is still one list left, check whether the node is null
     * After the loop, if carry != 0, add the most significant digit
     *
     * Time = O(m + n)
     * Space = O(1)
     */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            curr.next = new ListNode(sum % 10);
            carry = sum / 10;
            curr = curr.next;
        }
        // The carry is the most significant digit (have an extra carry of one at the end)
        if (carry != 0) {
            curr.next = new ListNode(carry);
        }
        return dummy.next;
    }

    @Test
    public void test1() {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode exp = new ListNode(7);
        exp.next = new ListNode(0);
        exp.next.next = new ListNode(8);

        ListNode act = addTwoNumbers(l1, l2);
        while (exp != null || act != null) {
            Assert.assertTrue(exp.val == act.val);
            exp = exp.next;
            act = act.next;
        }
    }

    @Test
    public void test2() {
        ListNode l1 = new ListNode(0);
        l1.next = new ListNode(1);
        ListNode l2 = new ListNode(0);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(2);

        ListNode exp = new ListNode(0);
        exp.next = new ListNode(0);
        exp.next.next = new ListNode(3);

        ListNode act = addTwoNumbers(l1, l2);
        while (exp != null || act != null) {
            Assert.assertTrue(exp.val == act.val);
            exp = exp.next;
            act = act.next;
        }
    }

    @Test
    public void test3() {
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        ListNode l2 = new ListNode(1);

        ListNode exp = new ListNode(0);
        exp.next = new ListNode(0);
        exp.next.next = new ListNode(1);

        ListNode act = addTwoNumbers(l1, l2);
        while (exp != null || act != null) {
            Assert.assertTrue(exp.val == act.val);
            exp = exp.next;
            act = act.next;
        }
    }

    public static void main(String[] args) {
        JUnitCore.main(LC0002.class.getName());
    }
}
