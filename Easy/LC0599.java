package Leetcode.Easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 599. Minimum Index Sum of Two Lists
 * Suppose Andy and Doris want to choose a restaurant for dinner, and they both have a list of favorite restaurants represented by strings.
 * You need to help them find out their common interest with the least list index sum. If there is a choice tie between answers, output all of them with no order requirement. You could assume there always exists an answer.
 *
 * Example 1:
 * Input:
 * ["Shogun", "Tapioca Express", "Burger King", "KFC"]
 * ["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
 * Output: ["Shogun"]
 * Explanation: The only restaurant they both like is "Shogun".
 *
 * Example 2:
 * Input:
 * ["Shogun", "Tapioca Express", "Burger King", "KFC"]
 * ["KFC", "Shogun", "Burger King"]
 * Output: ["Shogun"]
 * Explanation: The restaurant they both like and have the least index sum is "Shogun" with index sum 1 (0+1).
 *
 * Note:
 * The length of both lists will be in the range of [1, 1000].
 * The length of strings in both lists will be in the range of [1, 30].
 * The index is starting from 0 to the list length minus 1.
 * No duplicates in both lists.
 */
public class LC0599 {
	/**
	 * Solution: Use a hashmap
	 * map(string, index): Create a mapping from the elements of list1 to their indices.
	 * minSum = the min sum of two indices
	 * Iterate each ele in list2:
	 *      if ele also in map:
	 *          curSum = map.get(ele) + index_of_ele_in_list2
	 *          if (curSum == minSum):
	 *              add ele into res
	 *          else if (curSum < minSum):
	 *              update minSum;
	 *              res clear, add ele
	 *
	 * Time = O(l1 + l2)
	 * Space = O(min(l1, l2) * x) -> x represents the avg length of string
	 */
	public String[] findRestaurant(String[] list1, String[] list2) {
		if (list1.length == 0 || list2.length == 0) {
			return new String[0];
		}

		Map<String, Integer> map = new HashMap<>();
		List<String> res = new ArrayList<>();

		for (int i = 0; i < list1.length; i++) {
			map.put(list1[i], i);
		}

		int sum = Integer.MAX_VALUE;
		for (int i = 0; i < list2.length; i++) {
			if (map.containsKey(list2[i])) {
				int curSum = map.get(list2[i]) + i;
				if (curSum == sum) {
					res.add(list2[i]);
				} else if (curSum < sum) {
					sum = curSum;
					res.clear();
					res.add(list2[i]);
				}
			}
		}

		return res.toArray(new String[res.size()]);
	}

	public static void main(String[] args) {
		LC0599 sol = new LC0599();
		String[] list1 = new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"};
		String[] list2 = new String[]{"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"};

		String[] res = sol.findRestaurant(list1,list2);
		for (String str: res) {
			System.out.println(str);
		}
	}
}
