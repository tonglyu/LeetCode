package Leetcode.Medium;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 227. Basic Calculator II
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.
 *
 * Example 1:
 * Input: "3+2*2"
 * Output: 7
 *
 * Example 2:
 * Input: " 3/2 "
 * Output: 1
 *
 * Example 3:
 * Input: " 3+5 / 2 "
 * Output: 5
 *
 * Note:
 * You may assume that the given expression is always valid.
 * Do not use the eval built-in library function.
 */
public class LC0227 {
	/**
	 * Solution1: use a stack
	 * We divide the expression into different parts, each parts contains the operation and operator
	 * num: use to record the current number of value
	 * sign: indicate the sign of the incoming result
	 * stack: hold temporary results for partial expressions with lower precedence levels (which means we need to calculate the sub result of higher level operations)
	 *
	 * e.g 3 + 2 * 2 - 8 + 7 / 2 * 3
	 *                             i
	 * num = 0
	 * sign = '3'
	 * s|| 3, 4, -8, 9
	 * For each char c in string:
	 *      if c is digit: calculate current num
	 *      if c is not digit and is not space || reach to the end of string:
	 *          if sign is + or -: push num into stack
	 *          else is * or / : pop out the top element first to compute with current num in higher precedence level operations, then push back
	 *          update next partial sign
	 *          reset num to 0
	 * Finally, add all nums in stack and return
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int calculate(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}

		int num = 0;
		char sign = '+';
		int sum = 0;
		Deque<Integer> st = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				num = num * 10 + s.charAt(i) - '0';
			}

			if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == s.length() - 1) {
				if (sign == '+') {
					st.push(num);
				} else if (sign == '-') {
					st.push(-num);
				} else if (sign == '*') {
					st.push(st.pop() * num);
				} else {
					st.push(st.pop() / num);
				}

				sign = s.charAt(i);
				num = 0;
			}
		}

		while (!st.isEmpty()) {
			sum += st.pop();
		}

		return sum;
	}

	/**
	 * Solution2: just use the variable
	 * For the current problem, we only have two precedence levels, lower level with '+' & '-' operations and higher level with '*' & '/' operations.
	 * So the stack can be replaced by two variables, one for the lower level and the other for the higher level.
	 * num: use to record the current number of value
	 * sign: indicate the sign of the incoming result
	 * preNum: use to maintain the current lower precedence level number, if high level comes, update it on preNum
	 * sum: the use of stack, the sum of lower level sum
	 *
	 * e.g 3 + 2 * 2 - 8 + 7 / 2 * 3
	 *                             i
	 * num = 0
	 * sign = '3'
	 * preNum = 9
	 * sum = 3 + 4 + (-8) + 9
	 *
	 * For each char c in string:
	 * 	 if c is digit: calculate current num
	 * 	 if c is not digit and is not space || reach to the end of string:
	 * 	    if sign is + or -: add preNum to sum, update preNum to current num
	 * 	    else is * or / : update preNum
	 * 	    update next partial sign
	 * 	    reset num to 0
	 * 	 return sum + preNum
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int calculate2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}

		int num = 0;
		int preNum = 0;
		char sign = '+';
		int sum = 0;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				num = num * 10 + s.charAt(i) - '0';
			}

			if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == s.length() - 1) {
				if (sign == '+') {
					sum += preNum;
					preNum = num;
				} else if (sign == '-') {
					sum += preNum;
					preNum = -num;
				} else if (sign == '*') {
					preNum = preNum * num;
				} else {
					preNum = preNum / num;
				}

				sign = s.charAt(i);
				num = 0;
			}
		}

		return sum + preNum;
	}

	public static void main(String[] args) {
		LC0227 sol= new LC0227();
		String s = "13+2*2 - 25/2";
		System.out.println(sol.calculate2(s));
	}
}
