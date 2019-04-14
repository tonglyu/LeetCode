package Leetcode.Hard;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 42. Trapping Rain Water
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
 *
 * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!
 *
 * Example:
 * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 */
public class LC0042 {
	/**
	 * Data structure: dp
	 * Find maximum height of bar from the left end upt o an index i in the array left_max.
	 * Find maximum height of bar from the right end upto an index i in the array right_max.
	 * Iterate over the height array and update ans:
	 *      Add min(max_left[i],max_right[i])−height[i] to ans
	 *
	 * Time = O(3n)
	 * Space = O(2n)
	 */
	public int trap(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int sum = 0;
		int[] leftHeight = new int[height.length];
		int[] rightHeight = new int[height.length];

		leftHeight[0] = height[0];
		for (int i = 1; i < height.length; i++) {
			leftHeight[i] = Math.max(height[i], leftHeight[i - 1]);
		}

		rightHeight[height.length - 1] = height[height.length - 1];
		for (int i = height.length - 2; i >= 0; i--) {
			rightHeight[i] = Math.max(height[i], rightHeight[i + 1]);
		}

		for (int i = 0; i < height.length; i++) {
			int h = Math.min(leftHeight[i], rightHeight[i]);
			sum += (h - height[i]);
		}

		return sum;
	}

	/**
	 * Data structure: Monotonically non-increasing stack
	 * 没看懂
	 *
	 * Time = O(n)
	 * Space = O(n)
	 */
	public int trap2(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}

		int sum = 0;
		int idx = 0;
		Deque<Integer> s = new LinkedList<>();
		while (idx < height.length) {
			while (!s.isEmpty() && height[idx] > height[s.peek()]) {
				int topIdx = s.pollFirst();
				if (s.isEmpty()) break;
				int distance = idx - s.peek() - 1;
				int h = Math.min(height[idx],height[s.peek()]) - height[topIdx];
				sum += distance * h;
			}
			s.offerFirst(idx++);
		}
		return sum;
	}

	/**
	 * Data structure: two pointers with one pass
	 *
	 * Principal: assume we have two bound height: leftMost, rightMost,
	 * we can only update the side with lower height (e.g. leftMost < rightMost)because the height will be bounded by that side
	 * regardless of height btw [left, right]
	 * while we can not decide height[right] if there exists a  height > leftMost btw [left, right]
	 * ref: https://www.youtube.com/watch?v=8BHqSdwyODs
	 *
	 * 1) update leftMost and rightMost
	 * 2) if leftMost < rightMost:
	 *      sum += leftMost - height[left], left++
	 * 3) else
	 *      sum += rightMost - height[right], right--
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int trap3(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int sum = 0;
		int left = 0;
		int right = height.length - 1;
		int leftMax = 0, rightMax = 0;
		while (left < right) {
			leftMax = Math.max(leftMax, height[left]);
			rightMax = Math.max(rightMax, height[right]);
			if (leftMax < rightMax) {
				sum += leftMax - height[left];
				left++;
			} else {
				sum += rightMax - height[right];
				right--;
			}
		}

		return sum;
	}

	public static void main(String[] args) {
		int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
		LC0042 sol = new LC0042();
		System.out.println(sol.trap3(height));
	}
}
