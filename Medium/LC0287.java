package Leetcode.Medium;

/**
 * 287. Find the Duplicate Number
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
 *
 * Example 1:
 * Input: [1,3,4,2,2]
 * Output: 2
 *
 * Example 2:
 * Input: [3,1,3,4,2]
 * Output: 3
 *
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 */
public class LC0287 {
	/**
	 * One of the simple way is to use set but that will take O(n) space,
	 * here we try to find the relationship btw LC0142 LinkedList has Cycle II
	 *
	 * Since the index of array is 0 -> n, and value is 1 -> n, we must have at least one duplicate.
	 * We try to build the linked list by take the value of current index as the next node,
	 * then we will find the cycle, because at least two indices have the same value.
	 * Tip: zero would never occurs in the array, so we start from zero
	 * 0  1  2  3  4
	 * 3  1  3  4  2
	 *
	 * 0 -> 3 -> 4
	 *       \   |
	 *         2
	 * And then we need to find the entry node of the cycle.
	 *
	 * Time = O(n)
	 * Space = O(1)
	 */
	public int findDuplicate(int[] nums) {
		int slow = 0;
		int fast = 0;
		// Always remember to move pointers first, then check if it equals with each other.
		while (true) {
			slow = nums[slow];
			fast = nums[nums[fast]];
			if (fast == slow)
				break;
		}

		fast = 0;
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[fast];
		}

		return slow;
	}

	public static void main(String[] args) {
		LC0287 sol = new LC0287();
		int[] nums = new int[]{1,4,6,6,6,2,3};
		System.out.println(sol.findDuplicate(nums));
	}
}
