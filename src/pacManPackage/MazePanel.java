package pacManPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//The graphical interface for the maze space (the main play area)
public class MazePanel extends JPanel
{
	
	private Image mazeImg;
	private int cellDim; 
	private int cellOffset = 0;
	private int width;
	private int height;
	
	private static Player 			player1;
	private static Blinky 			blinky;
	private static Pinky			pinky;
	private static Inky				inky;
	private static Clyde			clyde;
	private static PacManController controller;
	private static Collectable[] 	smallPelletsInstance;
	private static Collectable[] 	bigPelletsInstance;
	
	private static boolean readyDisplay = true;
	
	
	//constructor
	public MazePanel(Player player) throws IOException
	{
		
		mazeImg = ImageIO.read(new File("Sprites/Maze.png"));
		
		this.cellDim = 23;
		
		if(cellDim % 8 != 0)
		{
			cellOffset = 1;		//needed for resolution independence
		}
		
		this.width = this.cellDim * 28;
		this.height = this.cellDim * 31;
		
		System.out.println("CellDim = " + cellDim);
		
		//set player instance and instantiate the ghosts 
		player1 = player;
		player1.setCellDim(this.cellDim, this.width, this.height);
		blinky 	= new Blinky(14 * cellDim  + cellDim / 4, 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset), player1);
		pinky 	= new Pinky(14 * cellDim  + cellDim / 4, 14 * cellDim + cellDim / 2, player1);
		inky 	= new Inky(12 * cellDim  + cellDim / 8, 14 * cellDim + cellDim / 2, player1);
		clyde	= new Clyde(16 * cellDim  + cellDim / 8, 14 * cellDim + cellDim / 2, player1);
		controller = new PacManController(player1, blinky, pinky, inky, clyde, cellDim);
		controller.setMazeInstance(this);
		smallPelletsInstance = player1.getPelletsArray();
		bigPelletsInstance = player1.getPowerupsArray();
		
		//this.setPreferredSize(new Dimension(this.width, this.height));
		//this.setMinimumSize(new Dimension(mazeImg.getWidth(this), mazeImg.getHeight(this)));
		this.setFocusable(true);
		this.addKeyListener(controller);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(mazeImg, 0, 0, this.width, this.height, this);
		draw(g);
		
		//System.out.println("Punctaj: " + player1.getCurrentPoints());
		
		PacManView.updatePointsDisplay(player1);

	}
	
	public void draw(Graphics g)
	{
		//draws the matrix lines - development
		//for(int i = 0; i < 28; i++)
		//{
			//g.drawLine(i*cellDim, 0, i*cellDim, this.getHeight());
			//System.out.println("Line drawn at: " + i*cellDim +  " " + 0 +  " " + i*cellDim + " " + this.getHeight());
		//}
				
		//for(int i = 0; i < 31; i++)
		//{
			//g.drawLine(0, i*cellDim, this.getWidth(), i*cellDim);
		//}
		
		//draws the active small pellets
		for(Collectable i : smallPelletsInstance)
		{
			if(Player.getPelletCount() > 0 && i.getVisibilityStatus())
			{
				g.setColor(Color.pink);
				g.fillRect(i.getX() - (cellDim / 4 - cellDim / 16) / 2, i.getY() - (cellDim / 4 - cellDim / 16) / 2,
																cellDim / 4 - cellDim / 16, cellDim / 4 - cellDim / 16);
			}
		}
		
		//draws the active big pellets
		for(Collectable i : bigPelletsInstance)
		{
			if(Player.getPelletCount() > 0 && i.getVisibilityStatus())
			{
				g.setColor(Color.pink);
				g.fillOval(i.getX() - (cellDim - cellDim / 8) / 2, i.getY() - (cellDim - cellDim / 8) / 2,
																cellDim - cellDim / 8, cellDim - cellDim / 8);
			}
		}
		
		//draws the player
		g.drawImage(player1.getCurrentImage(), player1.getPozitionX() - cellDim,
				player1.getPozitionY() - cellDim, 2 * cellDim - cellDim / 4,
												2 * cellDim  - cellDim / 4, this);	
		
		
		if(!PacManController.getDyingAnimationState()) {
		
			//draws Blinky
			if(blinky.getGameState() != GhostState.DISABLED) {
				
				if(blinky.getGameState() == GhostState.EATEN) {
					
					g.drawImage(blinky.getCurrentImage(), blinky.getPozitionX() - (cellDim + cellDim / 4) / 2,
							blinky.getPozitionY() - (cellDim / 2 + cellDim / 4) / 2, cellDim + cellDim / 4,
															cellDim / 2 + cellDim / 4, this);	
				}
				else {
					
					g.drawImage(blinky.getCurrentImage(), blinky.getPozitionX() - cellDim,
							blinky.getPozitionY() - cellDim, 2 * cellDim - cellDim / 4,
													2 * cellDim  - cellDim / 4, this);
				}
			}
			
			//draws Pinky
			if(pinky.getGameState() != GhostState.DISABLED) {

				if(pinky.getGameState() == GhostState.EATEN) {
					
					g.drawImage(pinky.getCurrentImage(), pinky.getPozitionX() - (cellDim + cellDim / 4) / 2,
							pinky.getPozitionY() - (cellDim / 2 + cellDim / 4) / 2, cellDim + cellDim / 4,
															cellDim / 2 + cellDim / 4, this);	
				}
				else {
					
					g.drawImage(pinky.getCurrentImage(), pinky.getPozitionX() - cellDim,
							pinky.getPozitionY() - cellDim, 2 * cellDim - cellDim / 4,
													2 * cellDim  - cellDim / 4, this);
				}
			}
			
			//draws Inky
			if(inky.getGameState() != GhostState.DISABLED) {
				
				if(inky.getGameState() == GhostState.EATEN) {
					
					g.drawImage(inky.getCurrentImage(), inky.getPozitionX() - (cellDim + cellDim / 4) / 2,
							inky.getPozitionY() - (cellDim / 2 + cellDim / 4) / 2, cellDim + cellDim / 4,
															cellDim / 2 + cellDim / 4, this);	
				}
				else {
					
					g.drawImage(inky.getCurrentImage(), inky.getPozitionX() - cellDim,
							inky.getPozitionY() - cellDim, 2 * cellDim - cellDim / 4,
													2 * cellDim  - cellDim / 4, this);
				}
			}
			
			//draws Clyde
			if(clyde.getGameState() != GhostState.DISABLED) {
				
				if(clyde.getGameState() == GhostState.EATEN) {
					
					g.drawImage(clyde.getCurrentImage(), clyde.getPozitionX() - (cellDim + cellDim / 4) / 2,
							clyde.getPozitionY() - (cellDim / 2 + cellDim / 4) / 2, cellDim + cellDim / 4,
															cellDim / 2 + cellDim / 4, this);	
				}
				else {
					
					g.drawImage(clyde.getCurrentImage(), clyde.getPozitionX() - cellDim,
							clyde.getPozitionY() - cellDim, 2 * cellDim - cellDim / 4,
													2 * cellDim  - cellDim / 4, this);
				}
			}
		}
	
		//draws pause screen
		if(player1.getGamePaused())
		{
			int alpha = 160;
			
			g.setColor(new Color(3, 3, 3, alpha / 2));
			g.fillRect(0, 0, this.getWidth() - 2, this.getHeight() - 2);
			
			g.setColor(new Color(150, 150, 147, alpha));
			g.setFont(new Font("Verdana", Font.BOLD, 5 * cellDim));
			g.drawString("PAUSED", 2 * cellDim + cellDim / 2, 15 * cellDim + cellDim / 2 + cellDim / 8);
		}
		
		//draws the win screen
		if(Player.getPelletCount() <= 0)
		{
			int alpha = 200;
			
			g.setColor(new Color(3, 3, 3, alpha - 20));
			g.fillRect(0, 0, this.getWidth() - 2, this.getHeight() - 2);
			
			g.setColor(new Color(18, 56, 196, alpha));
			g.setFont(new Font("Verdana", Font.BOLD, 3 * cellDim));
			g.drawString("CONGRATZ", 4 * cellDim + cellDim / 2, 15 * cellDim + cellDim / 2 + cellDim / 8);
		}
		
		//draws the 'Ready' text
		if(readyDisplay) {
			
			g.setColor(Color.yellow);
			g.setFont(new Font("Verdana", Font.BOLD, cellDim + cellDim / 2));
			g.drawString("READY!", 10 * cellDim + cellDim / 2 + cellDim / 4, 18 * cellDim);
		}
	}
	
	public Image getMazeImg()
	{
		return mazeImg;
	}
	
	public int getMazeWidth()
	{
		return this.width;
	}
	
	public int getMazeHeight()
	{
		return this.height;
	}
	
	public static void setReadyDisplay(boolean value) {
		
		readyDisplay = value;
	}

}
