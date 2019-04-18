package Leetcode.Easy;

/**
 * 204. Count Primes
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Example:
 * Input: 10
 * Output: 4
 * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
 */
public class LC0204 {
	/**
	 * Method1
	 * 1) judge half of numbers
	 * 2) i = 2 to sqrt(n) -> isPrime
	 *
	 * Time = O(n^0.5 * n / 2) = O(n^1.5)
	 * Space = O(1)
	 */
	public int countPrimes(int n) {
		if (n <= 1) return 0;
		int count = 1;
		for (int i = 3; i < n; i += 2) {
			if (isPrime(i)) count++;
		}
		return count;
	}

	private boolean isPrime(int n) {
		for (int i = 2; i < Math.sqrt(n); i++) {
			if (n % i == 0) return false;
		}
		return true;
	}

	/**
	 * Method2: sieve of Eratosthenes
	 * To find all the prime numbers less than or equal to a given integer n by Eratosthenes' method:
	 *
	 * Create a list of consecutive integers from 2 through n: (2, 3, 4, ..., n).
	 * Initially, let p equal 2, the smallest prime number.
	 * Enumerate the multiples of p by counting in increments of p from 2p to n, and mark them in the list (these will be 2p, 3p, 4p, ...; the p itself should not be marked).
	 * Find the first number greater than p in the list that is not marked. If there was no such number, stop. Otherwise, let p now equal this new number (which is the next prime), and repeat from step 3.
	 * When the algorithm terminates, the numbers remaining not marked in the list are all the primes below n.
	 *
	 * Time = O(nloglogn) = O(n)
	 * Space = O(n)
	 * https://zh.wikipedia.org/wiki/%E5%9F%83%E6%8B%89%E6%89%98%E6%96%AF%E7%89%B9%E5%B0%BC%E7%AD%9B%E6%B3%95
	 */
	public int countPrimes2(int n) {
		if (n <= 1) return 0;
		int[] prime = new int[n];

		prime[0] = 1;
		prime[1] = 1;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (prime[i] == 1) continue;
			for (int j = i * i; j < n; j += i) {
				prime[j] = 1;
			}
		}

		int count = 0;
		for (int i = 2; i < n; i++) {
			if (prime[i] == 0) {
				count++;
			}
		}

		return count;
	}

	public static void main(String[] args) {
		LC0204 sol = new LC0204();
		int[] nums = new int[]{0, 1, 2, 10, 100};
		for (Integer num : nums) {
			System.out.println(sol.countPrimes(num));
		}
	}
}
