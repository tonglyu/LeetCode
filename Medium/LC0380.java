package Leetcode.Medium;

import java.util.*;

/**
 * 380. Insert Delete GetRandom O(1)
 * Design a data structure that supports all following operations in average O(1) time.
 *
 * insert(val): Inserts an item val to the set if not already present.
 * remove(val): Removes an item val from the set if present.
 * getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
 *
 * Example:
 * // Init an empty set.
 * RandomizedSet randomSet = new RandomizedSet();
 *
 * // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 * randomSet.insert(1);
 *
 * // Returns false as 2 does not exist in the set.
 * randomSet.remove(2);
 *
 * // Inserts 2 to the set, returns true. Set now contains [1,2].
 * randomSet.insert(2);
 *
 * // getRandom should return either 1 or 2 randomly.
 * randomSet.getRandom();
 *
 * // Removes 1 from the set, returns true. Set now contains [2].
 * randomSet.remove(1);
 *
 * // 2 was already in the set, so return false.
 * randomSet.insert(2);
 *
 * // Since 2 is the only number in the set, getRandom always return 2.
 * randomSet.getRandom();
 */
class RandomizedSet {
	/**
	 * Use hashmap to store the ele and index <element, index>
	 * Use list to add the elements, because hashmap does not support getRandom
	 */
	public Map<Integer, Integer> map;
	public List<Integer> list;
	/** Initialize your data structure here. */
	public RandomizedSet() {
		map = new HashMap<>();
		list = new ArrayList<>();
	}

	/**
	 * Inserts a value to the set. Returns true if the set did not already contain the specified element.
	 *
	 * add the element to the end of the list
	 * put (ele, list.size() - 1) into the map
	 */
	public boolean insert(int val) {
		if (map.containsKey(val))
			return false;
		list.add(val);
		map.put(val, list.size() - 1);
		return true;
	}

	/**
	 * Removes a value from the set. Returns true if the set contained the specified element.
	 *
	 * Since we need to remove the element into the list, the index of the element will be reset.
	 * So we swap the remove element and the last element, then remove the last element in the list
	 *
	 * 1) get the index of remove element val
	 * 2) swap the last element, set list and map  (index, element)
	 * 3) list.remove(-1), map.remove(val)
	 */
	public boolean remove(int val) {
		if (!map.containsKey(val))
			return false;

		int index = map.get(val);
		if (index < list.size() - 1) {
			int lastEle = list.get(list.size() - 1);
			list.set(index, lastEle);
			map.put(lastEle, index);
		}
		map.remove(val);
		list.remove(list.size() - 1);
		return true;
	}

	/** Get a random element from the set.
	 * r.nextInt() -> [0,1)
	 * r.nextInt((max - min) + 1) + min -> [min, max)
	 *
	 * r.nextInt(list.size()) -> [0, list.size() - 1)
	 */
	public int getRandom() {
		Random r  = new Random();
		return list.get(r.nextInt(list.size()));
	}
}
public class LC0380 {
	public static void main(String[] args) {
		RandomizedSet obj = new RandomizedSet();
		System.out.println(obj.insert(1));
		System.out.println(obj.insert(4));
		System.out.println(obj.insert(9));
		System.out.println(obj.insert(11));
		System.out.println(obj.insert(14));
		System.out.println(obj.remove(2));
		System.out.println(obj.insert(2));
		System.out.println(obj.getRandom());
		System.out.println(obj.remove(1));
		System.out.println(obj.insert(2));
		System.out.println(obj.getRandom());
	}
}
