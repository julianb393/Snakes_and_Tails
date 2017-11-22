package views;

import javax.swing.JFrame;

/*
 * Primary game board.
 */
public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	GamePanel gamePanel;
	
	public GameFrame() {
		setSize(807,841);
		setResizable(false);
		setTitle("Snake and Tails");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		gamePanel = new GamePanel();
		setContentPane(gamePanel);
	}

}
