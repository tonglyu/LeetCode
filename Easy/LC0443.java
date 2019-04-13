package Leetcode.Easy;

public class LC0443 {
    public int compress(char[] chars) {
        if (chars.length ==  0) return  0;
        int slow = 0, fast = 0;
        int count = 0;
        while (fast < chars.length) {
            char cur = chars[fast];
            while (fast < chars.length && chars[fast] == cur) {
                fast++;
                count++;
            }
            chars[slow++] = cur;
            if (count != 1) {
                String number = Integer.toString(count);
                for (int i = 0; i < number.length(); i++) {
                    chars[slow++] = number.charAt(i);
                }
            }
            count = 0;
        }
        return slow;
    }

    public static void main(String[] args) {
        LC0443 sol = new LC0443();
        char[] input = new char[]{'a','b','b','b','b','b','b','c','c','d','k','b','b'};
        System.out.println(sol.compress(input));
    }
}
