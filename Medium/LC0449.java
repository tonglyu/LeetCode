package Leetcode.Medium;

import Leetcode.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Codec {
	private static final String SEP = ",";
	private static final String NULL = "null";

	/**
	 * Method1: iterative way
	 * We encode the BST with pre-order traversal
	 * 5,3,2,1,6,7
	 */
	public String serialize(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		if (root == null) return NULL;
		//traverse it recursively if you want to, I am doing it iteratively here
		Deque<TreeNode> st = new LinkedList<>();
		st.push(root);
		while (!st.isEmpty()) {
			root = st.pop();
			sb.append(root.val).append(SEP);
			if (root.right != null) st.push(root.right);
			if (root.left != null) st.push(root.left);
		}
		return sb.toString();
	}

	// Decodes your encoded data to tree.
	// pre-order traversal
	public TreeNode deserialize(String data) {
		if (data.equals(NULL)) return null;
		String[] strs = data.split(SEP);
		Queue<Integer> q = new LinkedList<>();
		for (String e : strs) {
			q.offer(Integer.parseInt(e));
		}
		return getNode(q);
	}

	// some notes:
	//   5
	//  3 6
	// 2   7
	private TreeNode getNode(Queue<Integer> q) { //q: 5,3,2,1,6,7
		if (q.isEmpty()) return null;
		TreeNode root = new TreeNode(q.poll());//root (5)
		Queue<Integer> samllerQueue = new LinkedList<>();
		while (!q.isEmpty() && q.peek() < root.val) {
			samllerQueue.offer(q.poll());
		}
		//smallerQueue : 3,2   storing elements smaller than 5 (root)
		root.left = getNode(samllerQueue);
		//q: 6,7   storing elements bigger than 5 (root)
		root.right = getNode(q);
		return root;
	}

	/**
	 * Method2: recursion way
	 */
	public String serialize2(TreeNode root) {
		if (root == null) return null;

		StringBuilder sb = new StringBuilder();
		dfs(root, sb);
		return sb.toString();
	}

	private void dfs(TreeNode root, StringBuilder sb) {
		if (root == null) {
			return;
		}

		sb.append(root.val);
		sb.append(SEP);
		dfs(root.left,sb);
		dfs(root.right,sb);
	}

	public TreeNode deserialize2(String data) {
		if (data == null) return null;
		String[] strs = data.split(SEP);
		TreeNode root = build(strs, 0, strs.length - 1);

		return root;
	}

	private TreeNode build(String[] strs, int start, int end) {
		if (start > end) {
			return null;
		}

		int key = atoi(strs[start]);
		TreeNode root = new TreeNode(key);

		int leftend = start + 1;
		while (leftend <= end && atoi(strs[leftend]) < key) {
			leftend++;
		}
		leftend = leftend - 1;

		root.left = build(strs, start + 1, leftend);
		root.right = build(strs, leftend+ 1, end);
		return root;
	}

	private int atoi(String s) {
		return Integer.valueOf(s);
	}
}

public class LC0449 {
	public static void main(String[] args) {
		Codec codec = new Codec();
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right.right = new TreeNode(7);
		//root.right.left.left = new TreeNode(6);
		//root.right.left.right = new TreeNode(7);

		String data = codec.serialize2(root);
		System.out.println(data);

		TreeNode newRoot = codec.deserialize2(data);
		LC0102 helper = new LC0102();
		List<List<Integer>> res = helper.levelOrder(newRoot);
		System.out.println(res);
	}
}
