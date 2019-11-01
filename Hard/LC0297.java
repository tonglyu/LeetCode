package Leetcode.Hard;

import Leetcode.Medium.LC0102;
import Leetcode.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 297. Serialize and Deserialize Binary Tree
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
 *
 * Example:
 *
 * You may serialize the following tree:
 *
 *     1
 *    / \
 *   2   3
 *      / \
 *     4   5
 *
 * as "[1,2,3,null,null,4,5]"
 * Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
 * Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
 */

/**
 * Method1: BFS
 * ref: https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/74260/Recursive-DFS-Iterative-DFS-and-BFS
 */
class Codec1 {
	public String serialize(TreeNode root) {
		if (root == null) return "";
		Queue<TreeNode> q = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		q.offer(root);
		sb.append(String.valueOf(root.val));
		sb.append(',');
		while (!q.isEmpty()) {
			TreeNode cur = q.poll();
			if (cur.left == null) sb.append("null,");
			else {
				q.offer(cur.left);
				sb.append(String.valueOf(cur.left.val));
				sb.append(',');
			}
			if (cur.right == null) sb.append("null,");
			else {
				q.offer(cur.right);
				sb.append(String.valueOf(cur.right.val));
				sb.append(',');
			}
		}

		int idx = sb.length() - 1;
		while (!Character.isDigit(sb.charAt(idx))) {
			sb.deleteCharAt(sb.length() - 1);
			idx--;
		}
		return sb.toString();
	}

	// Decodes your encoded data to tree.
	public TreeNode deserialize(String data) {
		if (data.length() == 0) return null;
		String[] node = data.split(",");
		Queue<TreeNode> q = new LinkedList<>();
		TreeNode root = new TreeNode(Integer.valueOf(node[0]));
		q.offer(root);
		int idx = 1;
		while (!q.isEmpty()) {
			Queue<TreeNode> nextLevel = new LinkedList<>();
			while (!q.isEmpty() && idx < node.length) {
				TreeNode cur = q.poll();
				if (!node[idx].equals("null")) {
					cur.left = new TreeNode(Integer.valueOf(node[idx]));
					nextLevel.offer(cur.left);
				}
				idx++;
				if (!node[idx].equals("null")) {
					cur.right = new TreeNode(Integer.valueOf(node[idx]));
					nextLevel.offer(cur.right);
				}
				idx++;
			}
			// Each time update the queue to the new level's !!!
			q = nextLevel;
		}
		return root;
	}
}

/**
 * Method2: DFS recursion
 *
 * Time = O(n)
 * Space = O(height)
 */
class Codec2 {
	public String serialize(TreeNode root) {
		if (root == null) return "";
		StringBuilder sb = new StringBuilder();

		dfs(root,sb);
		return sb.toString();
	}

	private void dfs(TreeNode root, StringBuilder sb) {
		if (root == null) {
			sb.append("null,");
			return;
		}
		sb.append(String.valueOf(root.val));
		sb.append(',');
		dfs(root.left,sb);
		dfs(root.right,sb);
	}

	public TreeNode deserialize(String data) {
		String[] node = data.split(",");
		int[] idx = new int[1];
		return dfs(node, idx);
	}
	private TreeNode dfs(String[] node, int[] idx) {
		if (node[idx[0]].equals("null")) {
			//increase index for each null
			idx[0]++;
			return null;
		}

		TreeNode cur = new TreeNode(Integer.valueOf(node[idx[0]]));
		idx[0]++;
		cur.left = dfs(node, idx);
		//we do not need to increase index after recursive, because when we complete constructing the subtree, we must reach the null
		cur.right = dfs(node, idx);
		return cur;
	}
}

class Codec3{

	// Encodes a tree to a single string.
	public String serialize(TreeNode root) {
		StringBuilder sb=new StringBuilder();
		TreeNode x=root;
		Deque<TreeNode> stack=new LinkedList<>();
		while (x!=null || !stack.isEmpty()) {
			if (x != null) {
				sb.append(String.valueOf(x.val));
				sb.append(' ');
				stack.push(x);
				x=x.left;
			} else {
				sb.append("null ");
				x=stack.pop();
				x=x.right;
			}
		}
		return sb.toString();
	}

	// Decodes your encoded data to tree.
	public TreeNode deserialize(String data) {
		if (data.length()==0) return null;
		String[] node=data.split(" ");
		int n=node.length;
		Deque<TreeNode> stack=new LinkedList<>();
		TreeNode root=new TreeNode(Integer.valueOf(node[0]));
		TreeNode x=root;
		stack.push(x);

		int i = 1;
		while (i  < n) {
			while (i<n && !node[i].equals("null")) {
				x.left=new TreeNode(Integer.valueOf(node[i++]));
				x=x.left;
				stack.push(x);
			}
			while (i<n && node[i].equals("null")) {
				x=stack.pop();
				i++;
			}
			if (i<n) {
				x.right=new TreeNode(Integer.valueOf(node[i++]));
				x=x.right;
				stack.push(x);
			}
		}
		return root;
	}
}

public class LC0297 {
	public static void main(String[] args) {
		Codec3 codec = new Codec3();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		root.right.left.left = new TreeNode(6);
		root.right.left.right = new TreeNode(7);

		//5,2,3,null,null,2,4,3,1
		String data = codec.serialize(root);
		System.out.println(data);

		TreeNode newRoot = codec.deserialize(data);
		LC0102 helper = new LC0102();
		List<List<Integer>> res = helper.levelOrder(newRoot);
		System.out.println(res);
	}
}
