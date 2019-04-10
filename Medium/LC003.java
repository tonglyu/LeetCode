package Leetcode.Medium;

import java.util.HashSet;
import java.util.Set;

/**
 * 3. Longest Substring Without Repeating Characters
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class LC003 {
    /**
     * Data structure: two pointers, sliding window
     * left: the start index of the substring without repeating characters
     * right: the current position
     * set: store the existing character
     * init: left = 0, right = 0
     * Traverse the string:
     * 1) if the letter did not exist: add it to the set, update max, right++
     * 2) else: remove the character of left index, left++
     *
     * Tips: if the letter exists, do not remove the current position letter.
     * You should remove from the left side until the letter is not in the set. Then expand the window again.
     * Time = O(n)
     * Space = O(n)
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] input =   s.toCharArray();
        Set<Character> set = new HashSet<>();
        int max = 0;
        int left = 0;
        int right = 0;
        while(right < s.length()) {
            if (!set.contains(input[right])) {
                set.add(input[right]);
                max  = Math.max(max, right - left + 1);
                right++;
            } else {
                set.remove(input[left]);
                left++;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        LC003 sol = new LC003();
        String s = "pwwkew";
        System.out.println(sol.lengthOfLongestSubstring(s));
    }
}
