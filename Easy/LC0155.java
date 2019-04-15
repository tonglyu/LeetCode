package Leetcode.Easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 155. Min Stack
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 *
 * Example:
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> Returns -3.
 * minStack.pop();
 * minStack.top();      --> Returns 0.
 * minStack.getMin();   --> Returns -2.
 */

/**
 * Data structure: two stacks, grow and decrease at the same time
 * s   || 3,0,1,-7,-6,-8,-8
 * minS|| 3,0,0,-7,-7,-8,-8
 */
class MinStack {
	public Deque<Integer> stack;
	public Deque<Integer> minStack;

	/** initialize your data structure here. */
	public MinStack() {
		this.stack = new LinkedList<>();
		this.minStack = new LinkedList<>();
	}

	public void push(int x) {
		stack.offerFirst(x);
		if (minStack.isEmpty()) {
			minStack.offerFirst(x);
		} else {
			minStack.offerFirst(Math.min(x, minStack.peekFirst()));
		}
	}

	public void pop() {
		if (stack.isEmpty())
			return;
		stack.pollFirst();
		minStack.pollFirst();
	}

	public int top() {
		if (stack.isEmpty()) return -1;
		return stack.peekFirst();
	}

	public int getMin() {
		if (stack.isEmpty()) return -1;
		return minStack.peekFirst();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}
}

/**
 * stack: record the difference btw current value and min
 *   >=0 : means the current value is larger than or equal to the min, min keeps same as before
 *   <0: means current value is smaller than min, min needs to be updated -> min = x
 * Push:  (non empty)
 *     stack.push(x - min)
 *     min = Math.min(min, x)
 * Pop:
 *     int pop = s.pop()
 *     pop >= 0: min keeps the same
 *     pop < 0: min = min - pop
 * Top:
 *      int top = s.peek()
 *      top >= 0: top = top + min
 *      top < 0: top = min
 */
class MinStack2 {
	Deque<Integer> stack;
	int min;

	/** initialize your data structure here. */
	public MinStack2() {
		stack = new LinkedList<>();
	}

	public void push(int x) {
		if (stack.isEmpty()) {
			stack.push(0);
			min = x;
		} else {
			stack.push(x - min);
			if (x < min) min = x;
		}
	}

	public void pop() {
		if (stack.isEmpty()) return;

		int pop = stack.pop();
		if (pop < 0)
			min = min - pop;
	}

	public int top() {
		int top = stack.peek();
		if (top < 0)
			return min;
		else
			return min + top;
	}

	public int getMin() {
		return min;
	}
}

public class LC0155 {
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
	public static void main(String[] args) {
		MinStack2 minS1 = new MinStack2();
		minS1.push(-2);
		minS1.push(0);
		minS1.push(-3);
		minS1.push(-3);
		System.out.println(minS1.getMin());
		minS1.pop();
		minS1.pop();
		System.out.println(minS1.top());
		System.out.println(minS1.getMin());
	}
}
