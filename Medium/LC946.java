package Leetcode.Medium;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 946. Validate Stack Sequences
 * Given two sequences pushed and popped with distinct values, return true if and only if this could have been the result of a sequence of push and pop operations on an initially empty stack.
 *
 * Example 1:
 * Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * Output: true
 * Explanation: We might do the following sequence:
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 *
 * Example 2:
 * Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * Output: false
 * Explanation: 1 cannot be popped before 2.
 *
 * Note:
 * 0 <= pushed.length == popped.length <= 1000
 * 0 <= pushed[i], popped[i] < 1000
 * pushed is a permutation of popped.
 * pushed and popped have distinct values.
 */
public class LC946 {
	/**
	 * Simulate the push/pop operation.
	 *
	 * Push element from |pushed sequence| onto stack s one by one and pop when top of the stack s is equal the current element in the |popped sequence|.
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public boolean validateStackSequences(int[] pushed, int[] popped) {
		Deque<Integer> s = new LinkedList<Integer>();
		int idx = 0;
		for (int i = 0; i < pushed.length; i++) {
			s.offerFirst(pushed[i]);
			while (!s.isEmpty() && s.peek() == popped[idx]) {
				s.pollFirst();
				idx++;
			}
		}
		return s.isEmpty();
	}

	public static void main(String[] args) {
		LC946 sol = new LC946();
		int[] pushed = new int[]{1,2,3,4,5};
		int[] popped = new int[]{4,5,3,2,1};
		System.out.println(sol.validateStackSequences(pushed,popped));
	}
}
