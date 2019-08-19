package Leetcode.Medium;

/**
 * 418. Sentence Screen Fitting
 * Given a rows x cols screen and a sentence represented by a list of non-empty words, find how many times the given sentence can be fitted on the screen.
 *
 * Note:
 * A word cannot be split into two lines.
 * The order of words in the sentence must remain unchanged.
 * Two consecutive words in a line must be separated by a single space.
 * Total words in the sentence won't exceed 100.
 * Length of each word is greater than 0 and won't exceed 10.
 * 1 ≤ rows, cols ≤ 20,000.
 * Example 1:
 *
 * Input:
 * rows = 2, cols = 8, sentence = ["hello", "world"]
 * Output:
 * 1
 *
 * Explanation:
 * hello---
 * world---
 *
 * The character '-' signifies an empty space on the screen.
 *
 * Example 2:
 * Input:
 * rows = 3, cols = 6, sentence = ["a", "bcd", "e"]
 * Output:
 * 2
 *
 * Explanation:
 * a-bcd-
 * e-a---
 * bcd-e-
 *
 * Example 3:
 * Input:
 * rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]
 * Output:
 * 1
 *
 * Explanation:
 * I-had
 * apple
 * pie-I
 * had--
 */
public class LC0418 {
	/**
	 * Solution1: brute force
	 * Maintain an index to record next word needed to be put in the row
	 * When col + len(s[idx]) <= cols, we can add that word in current row
	 *      if idx == length of sentence, means we finish one round
	 *
	 * Time = O(rows * (sentence length / cols))
	 * Space = O(1)
	 */
	public int wordsTyping(String[] sentence, int rows, int cols) {
		int len = sentence.length;

		int idx = 0;
		int res = 0;
		for (int row = 0; row < rows; row++) {
			int col = 0;
			while (col + sentence[idx].length() <= cols) {
				col += (sentence[idx].length() + 1);
				idx++;
				if (idx == len) {
					res++;
					idx = 0;
				}
			}
		}

		return res;
	}

	/**
	 * Solution2:
	 * We join the sentence into one whole sentence: a-bcd-e- (8). Our goal is to find the start position of the row next to the last row on the screen, which is 25 here.
	 * Since actually it's the length of everything earlier,
	 * we can get the answer by dividing this number by the length of (non-repeated) sentence string.
	 * 0     6   10    16
	 * a-bcd-e-a-bcd-e-a-bcd-e-
	 * xxxxxx
	 *       xxxx
	 *           xxxxxx
	 *
	 * start: how many characters of the reformatted sentence have been put on the screen
	 * Go through all the rows:
	 *      Case 1: a-bcd-e-, reach to the space before 'e'
	 *          we don't need an extra space for current row. So that we need to increase our counter by using start++ and go to next line.
	 *      Case 2: a-bcd-e-, reach to the 'e'
	 *          the previous character is space, the current line is end, we can go to next line.
	 *      Case 3: a-bcd-e-, reach to the 'c'
	 *          move the cursor back until it reach to some space, and go to next line.
	 *
	 * Time = O(rows * (max length in the word))
	 * Space = O(length of reformatted sentence)
	 */
	public int wordsTyping2(String[] sentence, int rows, int cols) {
		String s = String.join(" ", sentence) + " ";

		int start = 0;
		int len = s.length();
		for (int i = 0; i < rows; i++) {
			start += cols; // denote the length here, so start is the beginning of next line
			if (s.charAt(start % len) != ' ') {
				while (start > 0 && s.charAt((start - 1) % len) != ' ') {
					start--;
				}
			} else {
				start++;
			}

		}

		return start / len; //(# of valid characters) / our formatted sentence
	}

	/**
	 * Solution3: "DP" solution
	 * nextIndex[i]: if there's a new line which is starting with word sentence[i], what is the starting word index of next line.
	 * times[i]: we compute how many times the pointer in current line (start from sentence[i]) passes over the last word, which means 1 complete round.
	 *
	 * Iterate all rows:
	 *      Find the count of round for current line, and find the start index for the next line.
	 *      ans += times[i], i = nextIndex[i]. where i indicates what is the first word in the current line.
	 *
	 * Time = O(n * (cols / lenAverage) + rows)
	 * Space = O(2n)
	 */
	public int wordsTyping3(String[] sentence, int rows, int cols) {
		int[] nextIndex = new int[sentence.length];
		int[] times = new int[sentence.length];
		for(int i = 0; i < sentence.length; i++) {
			int curLen = 0;
			int idx = i;
			int time = 0;
			while(curLen + sentence[idx].length() <= cols) {
				curLen += sentence[idx++].length() + 1;
				if(idx == sentence.length) {
					idx = 0;
					time ++;
				}
			}
			nextIndex[i] = idx;
			times[i] = time;
		}

		int ans = 0;
		int cur = 0;
		for(int i = 0; i < rows; i++) {
			ans += times[cur];
			cur = nextIndex[cur];
		}
		return ans;
	}

	public static void main(String[] args) {
		LC0418 sol = new LC0418();
		String[] sentence = new String[]{"a", "bcd", "e"};
		int rows = 3;
		int cols = 6;
		System.out.println(sol.wordsTyping3(sentence,rows,cols));
	}
}
