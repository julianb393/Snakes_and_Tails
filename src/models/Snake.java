package models;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import views.GamePanel;

/*
 * Snake class; always hungry
 */
public class Snake implements Runnable {
	
	public static final int SCALE = 20;
	int x, y, dx, dy;
	SnakeNode head;
	ArrayList<SnakeNode> body;
	boolean flag = false;
	private Thread thread;
	
	public Snake() {
		this.x = (GamePanel.WIDTH)/2;
		this.y = (GamePanel.HEIGHT)/2;
		this.body = new ArrayList<>();
		this.head = new SnakeNode(this.x, this.y);
		addNode(this.head);
		thread = new Thread(this);
		thread.start();
		
	}
	
	public ArrayList<SnakeNode> getBody() {
		return body;
	}
	
	public SnakeNode getHead() {
		return head;
	}
	
	public void addNode(SnakeNode node) {
		this.body.add(node);
	}
	
	/**
	 * Makes every SnakeNode object follow it's predecessor.
	 * Note: the head is the end of the body.
	 */
	public void move() {
		
		for (int i = 0; i < this.body.size() - 1; i++) {
			this.body.set(i, this.body.get(i + 1));
		}
		
		this.body.set(body.size() - 1, new SnakeNode(this.head.getX() + dx, this.head.getY() + dy));
		this.head = body.get(body.size() - 1);
		
	}
	
	/**
	 * Return true if the head of the snake lands on the
	 * Food object, food. False otherwise.
	 * @param food Food
	 * @return boolean
	 */
	public boolean eat(Food food) {
		return this.head.getLoc().equals(food.getLoc());
	}
	
	/**
	 * Listener for key events.
	 * Controls for the movement of this Snake object.
	 * @param e KeyEvent
	 */
	public void keyPressed(KeyEvent e){
		
		if(!flag) {
			int key = e.getKeyCode(); // Gets listened key.
			if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) 
					&& dy == 0) {
				
				dx = 0;
				dy = - 1 * SCALE;
				flag = true;
			} else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) 
					&& dy == 0) {
				dx = 0;
				dy = SCALE;
				flag = true;
			} else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_KP_RIGHT) 
					&& dx == 0) {
				dx = SCALE;
				dy = 0;
				flag = true;
			} else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_KP_LEFT)
					&& dx == 0) {
				dx = -1  * SCALE;
				dy = 0;
				flag = true;
			} 
		}
	}
	
	/**
	 * Stops movement of this Snake object.
	 */
	public void freeze() {
		dx = 0;
		dy = 0;
	}
	
	/**
	 * Resets this Snake object to its initial
	 * state.
	 */
	public void reset() {
		this.body.clear();
		this.head = new SnakeNode(GamePanel.WIDTH/2, GamePanel.HEIGHT/2);
		this.body.add(this.head);
		
	}

	@Override
	public void run() {
		
		// Controls delay between keys.
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(flag) {
				flag = false;
			}
	
		}
	}

}
