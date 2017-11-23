package models;

import java.awt.Point;

/*
 * Node of a Snake.
 */
public class SnakeNode {

	private Point loc;
	
	public SnakeNode(int x, int y){
		this.loc = new Point(x, y);
	}
	
	public int getX() {
		return loc.x;
	}
	
	public int getY() {
		return loc.y;
	}
	
	public void setLoc(int x, int y) {
		this.loc.x = x;
		this.loc.y = y;	
	}
	
	public Point getLoc() {
		return this.loc;
	}
	
	public void increment(int dx, int dy) {
		loc.x += dx;
		loc.y += dy;
	}
}
