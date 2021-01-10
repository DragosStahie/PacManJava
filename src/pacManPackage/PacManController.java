package pacManPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Timer;

// The controller coordinates the player and the ghosts
public class PacManController extends KeyAdapter implements ActionListener
{
	MazePanel maze;
	private static Player 	player1;
	private static Blinky 	blinky;
	private static Pinky  	pinky;
	private static Inky	  	inky;
	private static Clyde  	clyde;
	private static Timer  	timer;
	
	private static final int 		TIMERSPEED 		= 1;
	private static 		 int 		startTimer 		= 0;
	private static 		 int 		animationTimer  = 0;
	private static 		 int 		pacTimer 		= 0;
	private static 		 int 		blinkyTimer 	= 0;
	private static 		 int 		pinkyTimer		= 0;
	private static		 int  		inkyTimer		= 0;
	private static		 int		clydeTimer		= 0;
	public  static 		 boolean 	dyingAnimation  = false;
	private static		 int 		cellDim			= 0;

	
	public PacManController(Player player, Blinky blinkyInstance, Pinky pinkyInstance,
						Inky inkyInstance, Clyde clydeInstance, int cellDimVal) throws IOException
	{
		//sets the instances of each game entity
		player1 = player;
		blinky 	= blinkyInstance;
		pinky 	= pinkyInstance;
		inky	= inkyInstance;
		clyde	= clydeInstance;
		cellDim = cellDimVal;
		
		//sets each ghost's starting game state
		blinky.setGameState(GhostState.SCATTER);
		pinky.setGameState(GhostState.SPAWN);
		inky.setGameState(GhostState.SPAWN);
		clyde.setGameState(GhostState.SPAWN);
		
		//initialize the animation timer
		timer = new Timer(TIMERSPEED, this);
		timer.start();
	}
	
	private void startGame()
	{
		player1.setGameStatus(true);

	}
	
	private void endGame()
	{
		player1.setGameStatus(false);
		timer.stop();
	}
	
	private void gameWon() {
		
		player1.setGameStatus(false);
		timer.stop();
	}
	
	//resets the ghosts' properties
	public static void resetGhosts(int cellDim, int cellOffset) {
		
		// Reset Blinky
		blinky.setPozX(14 * cellDim  + cellDim / 4);
		blinky.setPozY(11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
		blinky.setGameState(GhostState.SCATTER);
		blinky.blinking = false;
		blinky.setDirection(' ');
		blinky.currentImg = blinky.pausedImg.getImage();
		
		
		// Reset Pinky
		pinky.setPozX(14 * cellDim  + cellDim / 4);
		pinky.setPozY(14 * cellDim + cellDim / 2);
		pinky.resetGo();
		pinky.setGameState(GhostState.SPAWN);
		pinky.blinking = false;
		pinky.setDirection('U');
		pinky.currentImg = pinky.pausedImg.getImage();
		
		// Reset Inky
		inky.setPozX(12 * cellDim  + cellDim / 8);
		inky.setPozY(14 * cellDim + cellDim / 2);
		inky.resetGo();
		inky.setGameState(GhostState.SPAWN);
		inky.blinking = false;
		inky.setDirection('U');
		inky.currentImg = inky.pausedImg.getImage();
		
		// Reset Clyde
		clyde.setPozX(16 * cellDim  + cellDim / 8);
		clyde.setPozY(14 * cellDim + cellDim / 2);
		clyde.resetGo();
		clyde.setGameState(GhostState.SPAWN);
		clyde.blinking = false;
		clyde.setDirection('U');
		clyde.currentImg = clyde.pausedImg.getImage();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//waits for a couple of seconds before starting the game
		if(!player1.getGameStatus()) {
			
			startTimer++;
		}
		
		if(startTimer != -5 && startTimer > 1000 / TIMERSPEED)
		{
			startGame();
			startTimer = -5;
			MazePanel.setReadyDisplay(false);
		}
		
		if(player1.getGameStatus() && !player1.getGamePaused() && !dyingAnimation)
		{
			//moves PacMan
			if(pacTimer > Player.getMovementSpeed()) {
				
				player1.move(player1.getPlayerDirection());
				//System.out.println("Player Speed: " + Player.getMovementSpeed());
				pacTimer = 0;
			}
			else {
				
				pacTimer++;
			}
			
			//moves Blinky
			if(blinky.getGameState() != GhostState.DISABLED) {
			
				if(blinkyTimer > blinky.getMovementSpeed()) {
					
					blinky.action(player1.getPozitionX(), player1.getPozitionY());

					blinkyTimer = 0;
				}
				else {
					
					blinkyTimer++;
				}
			}
			
			//calculates Pinky's target coordinates and moves Pinky
			if(pinky.getGameState() != GhostState.DISABLED) {
				
				Pinky.setDisabled(false);

				if(pinkyTimer > pinky.getMovementSpeed()) {
					int pinkyChaseX = player1.getPozitionX(), pinkyChaseY = player1.getPozitionY();
					
					switch (player1.getPlayerDirection()) {
					
					case 'U':
						pinkyChaseX -= 4 * cellDim;
						pinkyChaseY -= 4 * cellDim;
						break;
						
					case 'D':
						pinkyChaseY += 4 * cellDim;
						break;
						
					case 'L':
						pinkyChaseX -= 4 * cellDim;
						break;
						
					case 'R':
						pinkyChaseX += 4 * cellDim;
						break;
					}
					pinky.action(pinkyChaseX, pinkyChaseY);
					
					pinkyTimer = 0;
				}
				else {
					
					pinkyTimer++;
				}
			}
			else {
				
				Pinky.setDisabled(true);
			}
			
			//calculates Inky's target coordinates and moves Inky
			if(inky.getGameState() != GhostState.DISABLED) {
				
				Inky.setDisabled(false);
				if(inkyTimer > inky.getMovementSpeed()) {
					int inkyChaseX = player1.getPozitionX(), inkyChaseY = player1.getPozitionY();
					
					switch (player1.getPlayerDirection()) {
					
					case 'U':
						inkyChaseX -= 2 * cellDim;
						inkyChaseY -= 2 * cellDim;
						break;
						
					case 'D':
						inkyChaseY += 2 * cellDim;
						break;
						
					case 'L':
						inkyChaseX -= 2 * cellDim;
						break;
						
					case 'R':
						inkyChaseX += 2 * cellDim;
						break;
					}
					
					inkyChaseX = 2 * inkyChaseX - blinky.getPozitionX();
					inkyChaseY = 2 * inkyChaseY - blinky.getPozitionY();
					inky.action(inkyChaseX, inkyChaseY);
					
					inkyTimer = 0;
				}
				else {
					
					inkyTimer++;
				}
			}
			else {
				
				Inky.setDisabled(true);
			}
			
			//calculates Clyde's target coordinates and moves Clyde
			if(clyde.getGameState() != GhostState.DISABLED) {
				
				if(clydeTimer > clyde.getMovementSpeed()) {
					int clydeChaseX = player1.getPozitionX(), clydeChaseY = player1.getPozitionY();

					if(Math.pow(clyde.getPozitionX() - player1.getPozitionX(), 2) +
							Math.pow(clyde.getPozitionY() - player1.getPozitionY(), 2) < Math.pow(cellDim * 8, 2)) {
						
						clydeChaseX = 31 * cellDim;
						clydeChaseY = 34 * cellDim;
					}
					
					clyde.action(clydeChaseX, clydeChaseY);
					
					clydeTimer = 0;
				}
				else {
					
					clydeTimer++;
				}
			}
			
			//Updates the ghosts' game states
			if(GameEntity.getFrightenedState()) {
				if(blinky.getGameState() == GhostState.CHASE || blinky.getGameState() == GhostState.SCATTER) {
					
					blinky.setGameState(GhostState.FRIGHTENED);
					blinky.resetTimer();
				}
				
				if((pinky.getGameState() == GhostState.CHASE || pinky.getGameState() == GhostState.SCATTER) && !Pinky.inSpawn()) {
					
					pinky.setGameState(GhostState.FRIGHTENED);
					pinky.resetTimer();
				}
				
				if((inky.getGameState() == GhostState.CHASE || inky.getGameState() == GhostState.SCATTER) && !Inky.inSpawn()) {
					
					inky.setGameState(GhostState.FRIGHTENED);
					inky.resetTimer();
				}
				
				if((clyde.getGameState() == GhostState.CHASE || clyde.getGameState() == GhostState.SCATTER) && !Clyde.inSpawn()) {
					
					clyde.setGameState(GhostState.FRIGHTENED);
					clyde.resetTimer();
				}
				//Player.updateCurrentPoints(500);
				GameEntity.resetFrightenedState();
			}
			else {
				GameEntity.resetFrightenedState();
			}
		}
		
		//if all pellets have been eaten, then the game is won
		if(Player.getPelletCount() <= 0)
		{
			gameWon();
		}
		
		//if the player has no more lives and makes contact with any ghost, then the game is lost
		if(Ghost.playerContact(blinky.getGameState()) && Player.getLives() == 0) {
				
			endGame();
		}
		
		if(Ghost.playerContact(pinky.getGameState()) && Player.getLives() == 0) {
			
			endGame();
		}
		
		if(Ghost.playerContact(inky.getGameState()) && Player.getLives() == 0) {
			
			endGame();
		}
		
		if(Ghost.playerContact(clyde.getGameState()) && Player.getLives() == 0) {
			
			endGame();
		}
		
		//play dying animation if necessary and reset the playground afterwards
		if(dyingAnimation) {
			
			animationTimer++;
			//System.out.println("Timer: " + animationTimer);

			if(animationTimer < 1000 / TIMERSPEED) {
				
				//System.out.println("Animation here");
				player1.currentImg = player1.pacDead.getImage();
			}
			else {
				
				dyingAnimation = false;
				animationTimer = 0;
				
				Player.updateLives(-1);
				player1.reset();
				//blinky.reset();
				blinky.setGameState(GhostState.SCATTER);
				blinky.resetTimer();
				pinky.setGameState(GhostState.SPAWN);
				pinky.resetTimer();
				inky.setGameState(GhostState.SPAWN);
				inky.resetTimer();
				clyde.setGameState(GhostState.SPAWN);
				clyde.resetTimer();
				startTimer = 0;
				player1.setGameStatus(false);
				MazePanel.setReadyDisplay(true);
			}
		}
		
		
		maze.repaint();
		//System.out.println("Current direction: " + player1.getPlayerDirection() +  "-" +  "Next direction: " + player1.getPlayerNextDirection());
	}

	//get key input from player
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(!dyingAnimation) {
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				player1.setPlayerNextDirection('L');
				break;
			case KeyEvent.VK_RIGHT:
				player1.setPlayerNextDirection('R');
				break;
			case KeyEvent.VK_UP:
				player1.setPlayerNextDirection('U');
				break;
			case KeyEvent.VK_DOWN:
				player1.setPlayerNextDirection('D');
				break;
			case KeyEvent.VK_ESCAPE:
				if(player1.getGameStatus()) {
					player1.setGamePaused(!player1.getGamePaused());
				}
				break;
			}
		}
	}
	
	public void setMazeInstance(MazePanel maze)
	{
		this.maze = maze;
	}
	
	public static boolean getDyingAnimationState() {
		
		return dyingAnimation;
	}
	
	public static void setDyingAnimationState(boolean value) {
		
		dyingAnimation = value;
	}
	
	public static void resetAnimationTimer() {
		
		animationTimer = 0;
	}
	
	public static int getTimerSpeed() {
		
		return TIMERSPEED;
	}
	
}
