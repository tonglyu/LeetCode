package Leetcode.Easy;

public class LC5016 {
	public String removeOuterParentheses(String S) {
		if (S == null || S.length() == 0) return "";
		StringBuilder res = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		int left = 0, right = 0;

		for (int i = 0; i < S.length(); i++) {
			if (S.charAt(i) == '(') {
				left++;
			} else {
				right++;
			}
			sb.append(S.charAt(i));
			if (left == right) {
				sb.deleteCharAt(0);
				sb.deleteCharAt(sb.length() - 1);
				res.append(sb.toString());
				left = 0;
				right =  0;
				sb = new StringBuilder();
			}
		}

		return res.toString();
	}

	public static void main(String[] args) {
		LC5016 sol = new LC5016();
		String S = "(()())(())";
		System.out.println(sol.removeOuterParentheses(S));
	}
}
