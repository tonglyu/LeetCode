package Leetcode.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 251. Flatten 2D Vector
 * Design and implement an iterator to flatten a 2d vector. It should support the following operations: next and hasNext.
 *
 * Example:
 * Vector2D iterator = new Vector2D([[1,2],[3],[4]]);
 *
 * iterator.next(); // return 1
 * iterator.next(); // return 2
 * iterator.next(); // return 3
 * iterator.hasNext(); // return true
 * iterator.hasNext(); // return true
 * iterator.next(); // return 4
 * iterator.hasNext(); // return false
 *
 * Notes:
 * Please remember to RESET your class variables declared in Vector2D, as static/class variables are persisted across multiple test cases.
 * Please see here for more details.
 * You may assume that next() call will always be valid, that is, there will be at least a next element in the 2d vector when next() is called.
 *
 * Follow up:
 * As an added challenge, try to code it using only iterators in C++ or iterators in Java.
 */
public class LC0251 {
	public static void main(String[] args) {
		int[][] v = new int[][]{{1,2},{},{3},{4,5}};
		Vector2DIte iterator = new Vector2DIte(v);

		System.out.println(iterator.next()); // return 1
		System.out.println(iterator.next()); // return 2
		System.out.println(iterator.hasNext()); //return true
		System.out.println(iterator.next()); // return 3
		System.out.println(iterator.hasNext()); // return true
		System.out.println(iterator.hasNext()); // return true
		System.out.println(iterator.next()); // return 4
		System.out.println(iterator.next()); // return 5
		System.out.println(iterator.hasNext()); // return false
	}
}

/**
 * Solution1: maintain two variables rIdx and cIdx
 * The important thing is when we reach the end of current row, we need to move forward to next row
 * [[],[],[1]]
 * That is, when column index reach to the last index, we reset cIdx = 0, rIdx++
 *
 * Time = O(n)
 * Space = O(n)
 */
class Vector2D {
	int rIdx;
	int cIdx;
	int[][] v;

	public Vector2D(int[][] v) {
		this.v = v;
		rIdx = 0;
		cIdx = 0;
	}

	public int next() {
		if (!hasNext()) {
			return -1;
		}

		// if there is next element, we just move column index one step forward
		return v[rIdx][cIdx++];
	}

	public boolean hasNext() {
		while (rIdx < v.length && cIdx == v[rIdx].length) {
			rIdx++;
			cIdx = 0;
		}
		return rIdx < v.length;
	}
}

/**
 * Solution2: follow up, use iterator
 * Idea is same as solution1
 *
 * Time = O(n)
 * Space = O(n)
 */
class Vector2DIte {
	Iterator<List<Integer>> rows;
	Iterator<Integer> cols;
	public Vector2DIte(int[][] v) {
		List<List<Integer>> arr = new ArrayList<>();
		for (int[] r: v) {
			arr.add(Arrays.stream(r).boxed().collect(Collectors.toList()));
		}
		rows = arr.iterator();
	}

	public int next() {
		if (!hasNext()) {
			return -1;
		}
		return cols.next();
	}

	public boolean hasNext() {
		while (rows.hasNext() && (cols == null || !cols.hasNext())) {
			cols = rows.next().iterator();
		}

		//need to check cols is null here, which means all list are empty
		return cols != null && cols.hasNext();
	}
}
