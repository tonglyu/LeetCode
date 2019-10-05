package Leetcode.Easy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 359. Logger Rate Limiter
 * Design a logger system that receive stream of messages along with its timestamps, each message should be printed if and only if it is not printed in the last 10 seconds.
 * Given a message and a timestamp (in seconds granularity), return true if the message should be printed in the given timestamp, otherwise returns false.
 *
 * It is possible that several messages arrive roughly at the same time.
 *
 * Example:
 * Logger logger = new Logger();
 * // logging string "foo" at timestamp 1
 * logger.shouldPrintMessage(1, "foo"); returns true;
 *
 * // logging string "bar" at timestamp 2
 * logger.shouldPrintMessage(2,"bar"); returns true;
 *
 * // logging string "foo" at timestamp 3
 * logger.shouldPrintMessage(3,"foo"); returns false;
 *
 * // logging string "bar" at timestamp 8
 * logger.shouldPrintMessage(8,"bar"); returns false;
 *
 * // logging string "foo" at timestamp 10
 * logger.shouldPrintMessage(10,"foo"); returns false;
 *
 * // logging string "foo" at timestamp 11
 * logger.shouldPrintMessage(11,"foo"); returns true;
 */
public class LC0359 {
	public static void main(String[] args) {
		LC0359 sol = new LC0359();

		Logger2 logger = new Logger2();
		// logging string "foo" at timestamp 1 returns true;
		System.out.println(logger.shouldPrintMessage(1, "foo"));
		// logging string "bar" at timestamp 2 returns true;
		System.out.println(logger.shouldPrintMessage(2,"bar"));
		// logging string "foo" at timestamp 3 returns false;
		System.out.println(logger.shouldPrintMessage(3,"foo"));
		// logging string "bar" at timestamp 8 returns false;
		System.out.println(logger.shouldPrintMessage(8,"bar"));
		// logging string "foo" at timestamp 10 returns false;
		System.out.println(logger.shouldPrintMessage(10,"foo"));
		// logging string "foo" at timestamp 11 returns true;
		System.out.println(logger.shouldPrintMessage(11,"foo"));
	}
}

/**
 * Solution1: Use Queue + Set
 * Set: use to store all messages in the queue
 * Queue: only store all messages whose timestamp are within 10 seconds of the latest messages
 *
 * When comes a new message:
 *  1. remove all messages, whose timestamp are more than 10 seconds from latest messages, from queue. Also delete from set
 *  2. if set does not contain message, add it to queue and set
 *
 *  Time = O(10)
 *  Space = O(n)
 */
class Logger {
	private class Tuple {
		int t;
		String msg;
		public Tuple(int t, String msg) {
			this.t = t;
			this.msg = msg;
		}
	}

	Queue<Tuple> q;
	Set<String> dict;

	/** Initialize your data structure here. */
	public Logger() {
		q = new LinkedList<>();
		dict = new HashSet<>();
	}

	/** Returns true if the message should be printed in the given timestamp, otherwise returns false.
	 If this method returns false, the message will not be printed.
	 The timestamp is in seconds granularity. */
	public boolean shouldPrintMessage(int timestamp, String message) {
		while (!q.isEmpty() && timestamp - q.peek().t >= 10) {
			Tuple cur = q.poll();
			dict.remove(cur.msg);
		}

		if (!dict.contains(message)) {
			q.offer(new Tuple(timestamp, message));
			dict.add(message);
			return true;
		}

		return false;
	}
}

/**
 * Solution2: Circular bucket
 * We only need two arrays with size 10 to maintain messages in 10s
 * times: use to maintain corresponding timestamp mapping, each bucket only contains latest timestamp for %10
 * sets: record all the messages received in buckets[idx]
 *
 * And every time Logger receive a new message:
 * 1) remove all messages that's contained on the bucket at timestamp%10.
 * 2) Update bucket[id] to keep current timestamp and make room for new messages.
 * 3) Loops thru the buckets and check bucket with only bucket[id] (means last timestamp) on the last 10 second
 */
class Logger2 {
	int[] times;
	Set<String>[] sets;

	public Logger2() {
		times = new int[10];
		sets = new Set[10];
		for (int i = 0; i < sets.length; ++i) {
			sets[i] = new HashSet<>();
		}
	}

	public boolean shouldPrintMessage(int timestamp, String message) {
		int idx = timestamp % 10;
		if (timestamp != times[idx]) {
			sets[idx].clear();
			times[idx] = timestamp;
		}
		for (int i = 0; i < times.length; ++i) {
			if (timestamp - times[i] < 10) {
				if (sets[i].contains(message)) {
					return false;
				}
			}
		}
		sets[idx].add(message);
		return true;
	}
}



