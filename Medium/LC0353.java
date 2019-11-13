package Leetcode.Medium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.*;

/**
 * 353. Design Snake Game
 * Design a Snake game that is played on a device with screen size = width x height. Play the game online if you are not familiar with the game.
 * The snake is initially positioned at the top left corner (0,0) with length = 1 unit.
 * You are given a list of food's positions in row-column order. When a snake eats the food, its length and the game's score both increase by 1.
 * Each food appears one by one on the screen. For example, the second food will not appear until the first food was eaten by the snake.
 * When a food does appear on the screen, it is guaranteed that it will not appear on a block occupied by the snake.
 *
 * Example:
 * Given width = 3, height = 2, and food = [[1,2],[0,1]].
 * Snake snake = new Snake(width, height, food);
 * Initially the snake appears at position (0,0) and the food at (1,2).
 * |S| | |
 * | | |F|
 *
 * snake.move("R"); -> Returns 0
 * | |S| |
 * | | |F|
 *
 * snake.move("D"); -> Returns 0
 * | | | |
 * | |S|F|
 *
 * snake.move("R"); -> Returns 1 (Snake eats the first food and right after that, the second food appears at (0,1) )
 * | |F| |
 * | |S|S|
 *
 * snake.move("U"); -> Returns 1
 * | |F|S|
 * | | |S|
 *
 * snake.move("L"); -> Returns 2 (Snake eats the second food)
 * | |S|S|
 * | | |S|
 *
 * snake.move("U"); -> Returns -1 (Game over because snake collides with border)
 */
class SnakeGame {
	/**
	 * Solution: LinkedList
	 * (1) The new position is out of the boundary, return -1
	 * (2) The new position reach the end of the snake body, remember to delete the oldest point in the linkedlist first!
	 * (3) the new position is the food, then we just need to add the deleted point back to the linkedlist.
	 *      The list will be one point longer then before.
	 */
	/** Initialize your data structure here.
	 @param width - screen width
	 @param height - screen height
	 @param food - A list of food positions
	 E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
	int[][] food;
	int width;
	int height;
	int foodIndex;
	LinkedList<Point> snake;
	Map<String, Point> moveDir;
	public SnakeGame(int width, int height, int[][] food) {
		this.width = width;
		this.height = height;
		this.foodIndex = 0;
		this.food = food;

		snake = new LinkedList<>();
		snake.add(new Point(0,0));

		moveDir = new HashMap<>();
		moveDir.put("U", new Point(-1,0));
		moveDir.put("D", new Point(1,0));
		moveDir.put("L", new Point(0,-1));
		moveDir.put("R", new Point(0,1));
	}

	/** Moves the snake.
	 @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
	 @return The game's score after the move. Return -1 if game over.
	 Game over when snake crosses the screen boundary or bites its body. */
	public int move(String direction) {
		Point head = new Point(snake.get(0).x, snake.get(0).y);
		Point dir = moveDir.get(direction);

		//1. Check if the head is out of bound
		Point curHead = new Point(head.x + dir.x, head.y + dir.y);
		if(curHead.x < 0 || curHead.x >= height || curHead.y < 0 || curHead.y >= width) {
			return -1;
		}

		//2. Delete the last point of snake, and check if the new head is already in the snake body
		// we need to delete first then check cuz we can move the head to the last point in the last round
		Point lastPoint = snake.get(snake.size()-1);
		snake.remove(snake.size()-1);
		if(snake.contains(curHead)) {
			return -1;
		}

		//3. Add new head as 1st point
		snake.add(0,curHead);
		if(foodIndex < food.length && curHead.x == food[foodIndex][0] && curHead.y == food[foodIndex][1]) {
			//if we eat the food, we do not need to move the last point
			snake.add(lastPoint);
			foodIndex++;
		}

		return foodIndex;
	}
}

class Point {
	int x;
	int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) {
			return false;
		}

		Point p = (Point) obj;
		return this.x == p.x &&  this.y == p.y;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(x).hashCode() + Integer.valueOf(y).hashCode();
	}
}


public class LC0353 {
	@Test
	public void test1() {
		int[][] food = new int[][]{{0,1}};
		int width = 2;
		int height = 2;
		SnakeGame obj = new SnakeGame(width, height, food);
		String[] moves = new String[]{"R","D"};
		int[] acts = new int[]{1,1};
		for (int i = 0; i < moves.length; i++) {
			Assert.assertEquals(acts[i],obj.move(moves[i]));
		}
	}

	@Test
	public void test2() {
		int[][] food = new int[][]{{2,0},{0,0},{0,2},{0,1},{2,2},{0,1}};
		int width = 3;
		int height = 3;
		SnakeGame obj = new SnakeGame(width, height, food);
		String[] moves = new String[]{"D","D","R","U","U","L","D","R","R","U","L","L","D","R","U"};
		int[] acts = new int[]{0,1,1,1,1,2,2,2,2,3,4,4,4,4,-1};
		for (int i = 0; i < moves.length; i++) {
			Assert.assertEquals(acts[i],obj.move(moves[i]));
		}
	}

	@Test
	public void test3() {
		int[][] food = new int[][]{{2,0},{0,0},{0,2},{2,2}};
		int width = 3;
		int height = 3;
		SnakeGame obj = new SnakeGame(width, height, food);
		String[] moves = new String[]{"D","D","R","U","U","L","D","R","R","U","L","D"};
		int[] acts = new int[]{0,1,1,1,1,2,2,2,2,3,3,3};
		for (int i = 0; i < moves.length; i++) {
			Assert.assertEquals(acts[i],obj.move(moves[i]));
		}
	}

	public static void main(String[] args) {
		JUnitCore.main(LC0353.class.getName());
	}
}
