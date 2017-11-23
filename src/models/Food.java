package models;

import java.awt.Point;
import java.util.Random;

/*
 * Food class; snakes love food
 */
public class Food {
	
	private Point loc;
	private Random random;
	
	public Food() {
		random = new Random();
		int x = (random.nextInt(38) * 20) + 20;
		int y = (random.nextInt(34) * 20) + 20;
		this.loc = new Point(x,y);
	}
	
	public Point getLoc() {
		return this.loc;
	}
	
	/**
	 * Sets a new pseudo-random location
	 * for this Food.
	 */
	public void setNewLocation() {
		int x = (random.nextInt(34) * 20) + 20;
		int y = (random.nextInt(34) * 20) + 20;
		this.loc.x = x;
		this.loc.y = y;
	}
}
