package Leetcode.Medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 957. Prison Cells After N Days
 * There are 8 prison cells in a row, and each cell is either occupied or vacant.
 *
 * Each day, whether the cell is occupied or vacant changes according to the following rules:
 * If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell becomes occupied.
 * Otherwise, it becomes vacant.
 * (Note that because the prison is a row, the first and the last cells in the row can't have two adjacent neighbors.)
 * We describe the current state of the prison in the following way: cells[i] == 1 if the i-th cell is occupied, else cells[i] == 0.
 * Given the initial state of the prison, return the state of the prison after N days (and N such changes described above.)
 *
 * Example 1:
 * Input: cells = [0,1,0,1,1,0,0,1], N = 7
 * Output: [0,0,1,1,0,0,0,0]
 * Explanation:
 * The following table summarizes the state of the prison on each day:
 * Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
 * Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
 * Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
 * Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
 * Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
 * Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
 * Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
 * Day 7: [0, 0, 1, 1, 0, 0, 0, 0]
 *
 * Example 2:
 * Input: cells = [1,0,0,1,0,0,1,0], N = 1000000000
 * Output: [0,0,1,1,1,1,1,0]
 *
 * Note:
 * cells.length == 8
 * cells[i] is in {0, 1}
 * 1 <= N <= 10^9
 */
public class LC0957 {
	/**
	 * Solution: map
	 * Note that cells.length = 8, and cells[0] and cells[7] will become 0.
	 * In fact, cells have only 2 ^ 6 = 64 different states, and there will be a loop.
	 * However, we don't know the length of period
	 *
	 * Use a map to record the seen state, (state, left N days)
	 * If we find the seen state, means we find the length of period map.get(state) - N
	 * So, we only need to find the modules N % len of period, then we need to compute the states in one cycle
	 *
	 * Time = O(2^n) (all states in one loop)
	 * Space = O(2^n * n)
	 */
	public int[] prisonAfterNDays(int[] cells, int N) {
		Map<String, Integer> seen = new HashMap<>();
		while (N > 0) {
			// If this is a cycle, fast forward by
			// seen.get(state) - N, the period of the cycle.
			if (seen.containsKey(Arrays.toString(cells))) {
				N %= seen.get(Arrays.toString(cells)) - N;
			}
			seen.put(Arrays.toString(cells), N);
			// if N == 0, means we finish all cycle and do not need to update again
			if (N >= 1) {
				cells = nextDay(cells);
			}
			N--;
		}
		return cells;
	}

	private int[] nextDay(int[] cells) {
		int[] cells2 = new int[8];
		for (int i = 1; i < 7; ++i) {
			cells2[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
		}

		return cells2;
	}

	public static void main(String[] args) {
		int[] cells = new int[]{1,0,0,1,0,0,1,0};
		int N = 1000000000;
		LC0957 sol = new LC0957();
		int[] res = sol.prisonAfterNDays(cells, N);
		for (int p: res) {
			System.out.println(p);
		}
	}
}
