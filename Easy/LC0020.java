package Leetcode.Easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 20. Valid Parentheses
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Example 1:
 * Input: "()"
 * Output: true
 *
 * Example 2:
 * Input: "()[]{}"
 * Output: true
 *
 * Example 3:
 * Input: "(]"
 * Output: false
 *
 * Example 4:
 * Input: "([)]"
 * Output: false
 *
 * Example 5:
 * Input: "{[]}"
 * Output: true
 */
public class LC0020 {
	/**
	 * Here we also considered about other letters in the string, we just ignore
	 * Data structure: stack
	 * From left to right
	 *      1) left brackets -> push into stack
	 *      2) right brackets -> if stack is empty || s.pop() != corresponding bracket, return false
	 *      2) other letters -> ignore
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public boolean isValid(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		Deque<Character> stack = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (cur == ')') {
				if (stack.isEmpty() || stack.pollFirst() != '(') return false;
			} else if (cur == ']') {
				if (stack.isEmpty() || stack.pollFirst() != '[') return false;
			} else if (cur == '}') {
				if (stack.isEmpty() || stack.pollFirst() != '{') return false;
			}  else if (cur == '(' || cur == '[' || cur == '{'){
				stack.offerFirst(cur);
			}
		}
		return stack.isEmpty();
	}

	public boolean isValid2(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		Deque<Character> stack = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (cur == '(') {
				stack.offerFirst(')');
			} else if (cur == '[') {
				stack.offerFirst(']');
			} else if (cur == '{') {
				stack.offerFirst('}');
			}  else if (cur == ')' || cur == ']' || cur == '}'){
				if (stack.isEmpty() || stack.pollFirst() != cur) return false;
			}
		}
		return stack.isEmpty();
	}

	public static void main(String[] args) {
		LC0020 sol = new LC0020();
		String[] strs = new String[]{"(8978dsdsa)","(asdf)sdas[]{}","(]","(safs[)]","{[safs]}"};
		for (String s: strs) {
			System.out.println(sol.isValid2(s));
		}
	}
}
