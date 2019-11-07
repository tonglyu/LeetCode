package Leetcode.Medium;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 150. Evaluate Reverse Polish Notation
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, /. Each operand may be an integer or another expression.
 *
 * Note:
 * Division between two integers should truncate toward zero.
 * The given RPN expression is always valid. That means the expression would always evaluate to a result and there won't be any divide by zero operation.
 *
 * Example 1:
 * Input: ["2", "1", "+", "3", "*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 *
 * Example 2:
 * Input: ["4", "13", "5", "/", "+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 *
 * Example 3:
 * Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
 * Output: 22
 * Explanation:
 *   ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 */
public class LC0150 {
	/**
	 * Solution: stack
	 * if it is a number, push into stack
	 * otherwise, pop out 2 nums to do the computation and push back into stack
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int evalRPN(String[] tokens) {
		Deque<Integer> nums = new LinkedList<>();

		for (String t: tokens) {
			if (t.equals("+")) {
				nums.push(nums.pop() + nums.pop());
			} else if (t.equals("-")) {
				int v2 = nums.pop();
				int v1 = nums.pop();
				nums.push(v1 - v2);
			} else if (t.equals("*")) {
				nums.push(nums.pop() * nums.pop());
			} else if (t.equals("/")) {
				int v2 = nums.pop();
				int v1 = nums.pop();
				nums.push(v1 / v2);
			} else {
				nums.push(Integer.valueOf(t));
			}
		}

		return nums.pop();
	}
}
