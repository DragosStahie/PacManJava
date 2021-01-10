package pacManPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PacManView extends JFrame
{
	// Components
	private 		JPanel 		highScore 		= new JPanel();
	private 		JPanel 		currentScore 	= new JPanel(new BorderLayout(0,5));
	private static 	JLabel 		scoreValue;
	private static 	JLabel 		lives;
	private 		JPanel 		playerStats 	= new JPanel(new BorderLayout(0,5));
	private 		MazePanel 	maze;	
	
	private final 	int 		gridWidth;
	private final 	int 		gridHeight;
	
	//============================================== constructor
	public PacManView(Player player) throws IOException
	{
	
		// Initialize Components
		maze = new MazePanel(player);

		gridWidth = maze.getMazeWidth() + 16;
		System.out.println("width: " + gridWidth);
		gridHeight = maze.getMazeHeight() + 38;
		System.out.println("height: " + gridHeight);
		
		//set dimensions
		this.highScore.setMinimumSize(new Dimension(gridWidth, 3 * (gridHeight / 31)));	
		this.highScore.setPreferredSize(new Dimension(gridWidth, 3 * (gridHeight / 31)));
		this.highScore.setMaximumSize(new Dimension(gridWidth, 3 * (gridHeight / 31)));
		
		this.highScore.setBackground(Color.black);
		
		this.playerStats.setMinimumSize(new Dimension(gridWidth, 2 * (gridHeight / 31)));	
		this.playerStats.setPreferredSize(new Dimension(gridWidth, 2 * (gridHeight / 31)));
		this.playerStats.setMaximumSize(new Dimension(gridWidth, 2 * (gridHeight / 31)));
		
		this.playerStats.setBackground(Color.black);

		
		JLabel scoreText = new JLabel("1 UP");
		scoreText.setFont(new Font("Verdana", Font.BOLD, 16));
		scoreText.setForeground(Color.YELLOW);
		scoreText.setPreferredSize(new Dimension(120, 16));
		
		scoreValue = new JLabel("00");
		scoreValue.setFont(new Font("Verdana", Font.BOLD, 16));
		scoreValue.setForeground(Color.YELLOW);
		scoreValue.setPreferredSize(new Dimension(120, 16));
		
		this.currentScore.add(scoreText, BorderLayout.NORTH);
		this.currentScore.add(scoreValue, BorderLayout.CENTER);
		this.currentScore.setBackground(Color.black);

		lives = new JLabel("3");
		lives.setFont(new Font("Verdana", Font.BOLD, 16));
		lives.setForeground(Color.YELLOW);
		lives.setPreferredSize(new Dimension(120, 16));
		
		this.highScore.setLayout(new BorderLayout(35, 0));
		JLabel highScoreText = new JLabel("HIGH SCORE");
		highScoreText.setFont(new Font("Verdana", Font.BOLD, 20));
		highScoreText.setForeground(Color.YELLOW);
		JLabel statsText = new JLabel("STATS GO HERE");
		statsText.setFont(new Font("Verdana", Font.BOLD, 20));
		statsText.setForeground(Color.YELLOW);
		this.highScore.add(highScoreText, BorderLayout.CENTER);
		this.highScore.add(currentScore, BorderLayout.WEST);
		//this.playerStats.add(statsText, BorderLayout.WEST);
		this.playerStats.add(lives, BorderLayout.WEST);

		
		// Components Layout
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(gridWidth, gridHeight + (gridHeight / 31) * 5));
		//System.out.println("Window size: " + gridWidth + "x" + gridHeight);

		this.add(highScore);
		this.add(maze);
		this.add(playerStats);
		//maze.add(pacPlayer);

		this.setBackground(Color.black);

		 this.pack();
		 
		 this.setTitle("PacMan DELUXE");
		 
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 this.setResizable(false);
		 this.setLocationRelativeTo(null);

	}


	public static void updatePointsDisplay(Player player)
	{
		Integer points = player.getCurrentPoints();
		
		scoreValue.setText(points.toString());
	}
	
	public static void updateLivesDisplay()
	{
		Integer livesValue = Player.getLives();
		
		lives.setText(livesValue.toString());
	}
	
}
