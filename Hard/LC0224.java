package Leetcode.Hard;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 224. Basic Calculator
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
 *
 * Example 1:
 * Input: "1 + 1"
 * Output: 2
 *
 * Example 2:
 * Input: " 2-1 + 2 "
 * Output: 3
 *
 * Example 3:
 * Input: "(1+(4+5+2)-3)+(6+8)"
 * Output: 23
 *
 * Note:
 * You may assume that the given expression is always valid.
 * Do not use the eval built-in library function.
 */
public class LC0224 {
	/**
	 * Solution1: single stack
	 * One important thing is that the input is valid, which means the parentheses are always paired and in order.
	 *
	 * Only 5 possible input we need to pay attention:
	 * digit: it should be one digit from the current number
	 * '+': number is over, we can add the previous number and start a new number
	 * '-': same as above
	 * '(': push the previous result and the sign into the stack, set result to 0, just calculate the new result within the parenthesis.
	 * ')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis,
	 *      second is the temporary result before this pair of parenthesis. We add them together.
	 *
	 * Finally if there is only one number, from the above solution, we haven't add the number to the result, so we do a check see if the number is zero.
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int calculate(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}

		Deque<Integer> st = new LinkedList<>();

		int num = 0;
		int res = 0;
		int sign = 1;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				num = num * 10 + c - '0';
			} else if (c == '+') {
				res += sign * num;
				num = 0;
				sign = 1;
			} else if (c == '-') {
				res += sign * num;
				num = 0;
				sign = -1;
			} else if (c == '(') {
				st.push(res);
				st.push(sign);
				sign = 1;
				res = 0;
			} else if (c == ')') {
				res += sign * num;
				num = 0;
				res = res * st.pop() + st.pop();
			}
		}


		return res + sign * num;
	}

	public static void main(String[] args) {
		LC0224 sol= new LC0224();
		String s = "(1+(4+2)-(3-1)+5";
		System.out.println(sol.calculate(s));
	}
}
