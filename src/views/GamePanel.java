package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import models.Food;
import models.Snake;
import models.SnakeNode;

/*
 * GamePanel class; where the graphics are drawn.
 */
public class GamePanel extends JPanel implements Runnable {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	
	private static final long serialVersionUID = -3900994690337722915L;
	private static final Color background = Color.decode("#C2C2A3");
	private Border border;
	Snake snake;
	Food food;
	JLabel scorelbl, endlbl;
	int score;
	
	Thread thread;
	private boolean running;
	
	public GamePanel() {
		setSize(WIDTH,HEIGHT);
		setFocusable(true);
		setLayout(null);
	    border = BorderFactory.createLineBorder(Color.BLACK, 1, true);
	    setBorder(border);
	    scorelbl = new JLabel(String.valueOf(score));
		scorelbl.setFont(new Font("Consolas", Font.PLAIN, 45));
	    scorelbl.setSize(scorelbl.getPreferredSize());
	    scorelbl.setBounds(WIDTH/2 - scorelbl.getWidth()/2, 20, 200, 50);
	    add(scorelbl);
	    
	    endlbl = new JLabel("<html><div style='text-align: center;'>GameOver<br>"
	    		+ "Press 'Space' to play again.</div></html>");
	    endlbl.setHorizontalAlignment(SwingConstants.CENTER);
	    endlbl.setVerticalAlignment(SwingConstants.TOP);

	    endlbl.setFont(new Font("Consolas", Font.PLAIN, 35));
	    endlbl.setSize(300,200);
	    endlbl.setLocation(WIDTH/2 - endlbl.getWidth()/2, endlbl.getHeight());

	    endlbl.setVisible(false);
	    add(endlbl);
	    
		addKeyListener(new MoveKeyAdapter());
		snake = new Snake();
		food = new Food();
		startNewThread();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(background);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		snake.move();
		for(SnakeNode node: snake.getBody()) {
			g.setColor(Color.decode("#595959"));
			g.fillRect(node.getX(), node.getY(), Snake.SCALE, Snake.SCALE);
			g.setColor(Color.BLACK);
			g.drawRect(node.getX(), node.getY(), Snake.SCALE, Snake.SCALE);
		}
		
		g.setColor(Color.RED);
		g.fillOval(food.getLoc().x, food.getLoc().y, Snake.SCALE, Snake.SCALE); // Same scale as snake.
		
		if(gameOver(snake)) {
			snake.freeze(); //Remove active velocity.
			running = false; // Stops thread.
			endlbl.setVisible(true);
			resetGame();
		}
		
		if(snake.eat(food)) {
			score++;
			scorelbl.setText(String.valueOf(score));
			food.setNewLocation(); // New random location.
			snake.addNode(new SnakeNode(snake.getHead().getX(), snake.getHead().getY()));
		}
		
		
		
	}
	
	/**
	 * Returns true if the snake touches any of the borders,
	 * or itself, false otherwise.
	 * @param snake Snake object
	 * @return boolean
	 */
	private boolean gameOver(Snake snake) {
		ArrayList<SnakeNode> nodes = snake.getBody();
		SnakeNode snakeHead = snake.getHead(); 
		if(snakeHead.getX() < 0 || snakeHead.getX() > WIDTH - Snake.SCALE ||
				snakeHead.getY() < 0 || snakeHead.getY() > HEIGHT - Snake.SCALE) {
			snake.freeze();
			return true;
		}
		for(int i = 0; i < nodes.size() - 1; i++) {
			if((snake.getHead().getX() == nodes.get(i).getX())
					&& (snake.getHead().getY() == nodes.get(i).getY())) {
				snake.freeze();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Sets running to true, then
	 * starts a new thread.
	 */
	protected void startNewThread() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	/*
	 * See Snake class for implementation of keyPressed.
	 */
	private class MoveKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e){
			snake.keyPressed(e);
			if(!running && e.getKeyCode() == KeyEvent.VK_SPACE) {
				endlbl.setVisible(false);
				scorelbl.setText(String.valueOf(score));
				startNewThread();
			}
		}
		
	}
	
	/**
	 * Resets all objects.
	 */
	private void resetGame() {
		snake.reset();
		food.setNewLocation();
		score = 0;
	}


	@Override
	public void run() {
		// Speed of drawing.
		while(running) {
			try {
				repaint();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


