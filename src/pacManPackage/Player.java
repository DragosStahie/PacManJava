package pacManPackage;

import java.io.IOException;

import javax.swing.ImageIcon;

public class Player extends GameEntity{
	
	
	//pacMan properties
	private boolean inGame = false;
	private boolean gamePaused = false;
	private static int pelletCount;
	private static int currentPoints;
	private static int noLives;
	private static int movementSpeed;
	private static PlayerState gameState;
	protected ImageIcon pacDead;




	// player constructor
	//		initialize player specific data
	public Player() throws IOException {

		pelletCount = 244;
		currentPoints = 0;

		this.upImg = new ImageIcon("Sprites/pacUp.gif");
		this.downImg = new ImageIcon("Sprites/pacDown.gif");
		this.leftImg = new ImageIcon("Sprites/pacLeft.gif");
		this.rightImg = new ImageIcon("Sprites/pacRight.gif");
		this.pausedImg = new ImageIcon("Sprites/pacPaused.png");
		this.pacDead = new ImageIcon("Sprites/pacDead.gif");

		this.currentImg = pausedImg.getImage();

		noLives = 3;
		
		updateGameState(PlayerState.NORMAL);
	}


	/**updates whether the player is currently in a game*/
	public void setGameStatus(boolean inGame) {
		this.inGame = inGame;
		//System.out.println("Game status updated :" + inGame);
	}

	/**returns true is player is currently in game and false otherwise*/
	public boolean getGameStatus() {
		return this.inGame;
	}

	/**returns the number of pellets that are still active*/
	public static int getPelletCount() {
		return pelletCount;
	}
	
	/**increases current count of pellets by the amount 'pellets'*/
	public static void updatePelletCount(int pellets) {
		pelletCount += pellets;
	}

	/**returns the current amount of points*/
	public int getCurrentPoints() {
		return currentPoints;
	}
	
	/**increases current amount of points by the amount 'points'*/
	public static void updateCurrentPoints(int points) {
		currentPoints += points;
	}
	
	/**sets whether the game is paused to the value of 'paused' and updates
	 * current image if the game is paused*/
	public void setGamePaused(boolean paused) {
		this.gamePaused = paused;
		if(paused == true) {
			this.currentImg = pausedImg.getImage();
		}
	}
	
	/**returns whether the game is paused or not*/
	public boolean getGamePaused() {
		return this.gamePaused;
	}
	
	/**increases current number of lives by the amount 'increment'*/
	public static void updateLives(int increment) {
		
		noLives += increment;
		PacManView.updateLivesDisplay();
	}
	
	/**returns current amount of lives*/
	public static int getLives() {
		
		return noLives;
	}
	
	/**resets player to starting position and updates related info*/
	public void reset() {
		
		this.setPozX(14 * cellDim  + cellDim / 4);
		this.setPozY(23 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
		this.setPlayerNextDirection(' ');
		this.setDirection(' ');
		this.currentImg = this.pausedImg.getImage();
		updateGameState(PlayerState.NORMAL);
	}
	
	/**updates current game state and speed accordingly*/
	public static void updateGameState(PlayerState state) {
		
		gameState = state;
		
		switch (gameState) {
		case NORMAL :
			setMovementSpeed(GameSpeeds.SPEED80.value);
			break;
			
		case DOTS :
			setMovementSpeed(GameSpeeds.SPEED70.value);
			break;
			
		case FRIGHT :
			setMovementSpeed(GameSpeeds.SPEED90.value);
			break;
		
		case FRIGHT_DOTS :
			setMovementSpeed(GameSpeeds.SPEED80.value);
			break;
			
		}
	}
	
	/**returns current game state*/
	public static PlayerState getGameState() {
		
		return gameState;
	}
	
	/**sets current movement speed to 'speed' value*/
	public static void setMovementSpeed(int speed) {
		
		movementSpeed = speed;
	}
	
	/**returns current movement speed*/
	public static int getMovementSpeed() {
		
		return movementSpeed;
	}
	
}
