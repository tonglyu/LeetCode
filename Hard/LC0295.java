package Leetcode.Hard;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 295. Find Median from Data Stream
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
 *
 * For example,
 * [2,3,4], the median is 3
 *
 * [2,3], the median is (2 + 3) / 2 = 2.5
 *
 * Design a data structure that supports the following two operations:
 *
 * void addNum(int num) - Add a integer number from the data stream to the data structure.
 * double findMedian() - Return the median of all elements so far.
 *
 *
 * Example:
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 *
 * Follow up:
 * If all integer numbers from the stream are between 0 and 100, how would you optimize it?
 * If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?
 */
class MedianFinder {
	/**
	 * Two heaps:
	 * max_heap: left half
	 * min_heap: right half
	 * 0 <= max.size - min.size <=1
	 *
	 * addNum: O(logn)
	 * findMedian: O(1)
	 * Space: O(n)
	 *
	 * Follow up1: 0-100 buckets, each store the number of occurrence, also store total number n
	 * count = 0;
	 * n is odd: find (n + 1) / 2 = x
	 * n is even: find n/2 =x and n/2 + 1 = y
	 * Search the array from left to right:
	 *      count += arr[i]
	 *      if count >= x/y
	 *          that is our candidate
	 *
	 * Follow up2: use bucket and two heaps
	 */
	PriorityQueue<Integer> min; //right half
	PriorityQueue<Integer> max; //left half

	/** initialize your data structure here. */
	public MedianFinder() {
		min = new PriorityQueue<Integer>();
		max = new PriorityQueue<Integer>(Collections.reverseOrder());
	}

	// Time = O(5 * logn)
	public void addNum(int num) {
		// always add the new number into left half
		max.offer(num);
		// always balance the size
		// 1) if num should be in the max-heap, then the median number will be remove to the min-heap
		// 2) if num should be in the min-heap, then it is exactly the top of max-heap
		min.offer(max.poll());

		// we should keep max.size - min.size = 0 or 1
		if (max.size() < min.size()) {
			max.offer(min.poll());
		}
	}

	// Time = O(1)
	public double findMedian() {
		try {
			if (max.size() > min.size()) {
				return max.peek();
			} else {
				return (max.peek() + min.peek()) * 0.5;
			}
		} catch (Exception e){
			throw e;
		}
	}
}

public class LC0295 {
	public static void main(String[] args) {
		LC0295 sol = new LC0295();
		MedianFinder obj = new MedianFinder();
		//obj.findMedian();
		obj.addNum(1);
		obj.addNum(2);
		System.out.println(obj.findMedian());
		obj.addNum(3);
		System.out.println(obj.findMedian());
		obj.addNum(4);
		System.out.println(obj.findMedian());
		obj.addNum(5);
		obj.addNum(1);
		System.out.println(obj.findMedian());
	}
}
