package Leetcode.Easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC438 {
	/**
	 * 438. Find All Anagrams in a String
	 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
	 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
	 * The order of output does not matter.
	 *
	 * Example 1:
	 * Input:
	 * s: "cbaebabacd" p: "abc"
	 * Output:
	 * [0, 6]
	 * Explanation:
	 * The substring with start index = 0 is "cba", which is an anagram of "abc".
	 * The substring with start index = 6 is "bac", which is an anagram of "abc".
	 *
	 * Example 2:
	 * Input:
	 * s: "abab" p: "ab"
	 * Output:
	 * [0, 1, 2]
	 * Explanation:
	 * The substring with start index = 0 is "ab", which is an anagram of "ab".
	 * The substring with start index = 1 is "ba", which is an anagram of "ab".
	 * The substring with start index = 2 is "ab", which is an anagram of "ab".
	 */
	public List<Integer> findAnagrams(String s, String p) {
		/**
		 * Data structure: sliding window
		 * Step1: map all letters in pattern to a hashmap, match = map.size()
		 * Step2: move the window
		 *  1) if map contains cur char, decrement count by 1; if current count is 0, match--;
		 *  2) when match = 0 (which means all letters are matched)
		 *        if (sliding window size == pattern size) add left point to res
		 *        update with char at left pointer in the map, move left pointer
		 *
		 *  Time = O(2n + m)
		 *  Space = O(m)
		 */
		List<Integer> result = new ArrayList<>();
		if(p.length()> s.length())
			return result;

		Map<Character, Integer> map = new HashMap<>();
		for(char c : p.toCharArray()){
			map.put(c, map.getOrDefault(c, 0) + 1);
		}

		//not only means how many letters are matched, but also means whether we have enough letters than required
		int match = map.size();
		int left = 0, right = 0;

		while(right < s.length()){
			char c = s.charAt(right);
			if( map.containsKey(c) ){
				map.put(c, map.get(c)-1);
				if(map.get(c) == 0) match--;
			}


			while(match == 0){
				if(right - left + 1 == p.length()){
					result.add(left);
				}

				char tempc = s.charAt(left);
				if(map.containsKey(tempc)){
					map.put(tempc, map.get(tempc) + 1);
					// must be larger than 0, because negative number means we have more chars than required
					// only positive numbers means we cannot match the letters required
					if(map.get(tempc) > 0){
						match++;
					}
				}
				left++;
			}

			right++;

		}
		return result;
	}

	public static void main(String[] args) {
		LC438 sol = new LC438();
		String s ="ababacbbaac";
		String p = "aab";
		List<Integer> res  = sol.findAnagrams(s,p);
		for (Integer idx:res)
			System.out.println(idx);
	}
}
