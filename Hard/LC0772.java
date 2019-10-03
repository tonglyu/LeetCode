package Leetcode.Hard;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 772. Basic Calculator III
 * Implement a basic calculator to evaluate a simple expression string.
 *
 * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
 * The expression string contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces .
 * The integer division should truncate toward zero.
 *
 * You may assume that the given expression is always valid. All intermediate results will be in the range of [-2147483648, 2147483647].
 *
 * Some examples:
 * "1 + 1" = 2
 * " 6-4 / 2 " = 4
 * "2*(5+5*2)/3+(6/2+8)" = 21
 * "(2+6* 3+5- (3*14/7+2)*5)+3"=-12
 */
public class LC0772 {
	/**
	 * Solution: monotonically increasing stack
	 * s1: use to store all numbers in the expression
	 * s2: monotonically increasing stack, use to store operator and its level of parenthesis, the level of operator is always increasing in stack
	 *
	 * boolean flag: we need to consider about negative number, so at the beginning of string or we find a left parenthesis, we set flag = true;
	 *              when we meet numbers, we set it to false
	 * for each character c in s:
	 *  Case1: c == ' ', ignore
	 *  Case2: c is digit, read in all digits of that num, and push it into s1. flag = false
	 *  Case3: c == '(', level++, flag = true
	 *  Case4: c == ')', level--
	 *  Case5: c is an operator
	 *      if c == '-' and flag: push 0 into s1
	 *      while (s2 is not empty and s2's top operator has higher preference than current operator) :
	 *          s1.push( pop two numbers from s1 and pop out top operator)
	 *      push current operator into s2
	 * calculate all expressions until all operator are used.
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int calculate(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}

		Deque<Integer> nums = new LinkedList<>();
		Deque<Operator> op = new LinkedList<>();

		int num = 0;
		int level = 0;
		boolean begin = true;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ') continue;
			if (Character.isDigit(c)) {
				num = c - '0';
				while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
					num = num * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				nums.push(num);
				begin = false;
			} else if (c == '(') {
				level++;
				begin = true;
			} else if (c == ')') {
				level--;
			} else {
				if (c == '-' && begin) {
					nums.push(0);
				}
				while (!op.isEmpty() && hasPreference(op.peek().sign, op.peek().level, c, level)) {
					int v2 = nums.pop();
					int v1 = nums.pop();
					char o = op.pop().sign;
					nums.push(helper(v1,v2,o));
				}

				op.push(new Operator(c,level));
			}
		}

		while (!op.isEmpty()) {
			int v2 = nums.pop();
			int v1 = nums.pop();
			char operator = op.pop().sign;
			nums.push(helper(v1, v2, operator));
		}

		return nums.pop();
	}

	public static void main(String[] args) {
		LC0772 sol = new LC0772();
		String[] s = new String[]{"(2+6* 3+5- (3*14/7+2)*5)+3","-1+4*3/3/3","2*(5+5*2)/3+(6/2+8)"};
		for (String t: s) {
			System.out.println(sol.calculate(t));
		}
	}

	private boolean hasPreference(char o1, int l1, char o2, int l2) {
		if (l1 < l2 || (l1 == l2 && isLower(o1) && !isLower(o2))) {
			return false;
		}
		return true;
	}

	private boolean isLower(char o) {
		return o == '+' || o == '-';
	}

	private int helper(int v1, int v2, char operator) {
		if (operator == '+') {
			return v1 + v2;
		} else if (operator == '-') {
			return v1 - v2;
		} else if (operator == '*') {
			return v1 * v2;
		} else {
			return v1 / v2;
		}
	}
}

class Operator {
	char sign;
	int level;
	public Operator(char sign, int level) {
		this.sign = sign;
		this.level = level;
	}
}