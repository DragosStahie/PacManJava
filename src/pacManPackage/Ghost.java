package pacManPackage;

import java.io.IOException;

import javax.swing.ImageIcon;

//The Ghost class implements movements patterns and methods common all ghosts
public class Ghost extends GameEntity {
	
	private static Player playerInstance;

	private GhostState gameState = GhostState.SCATTER;
	private static 	int 	pointsMultiplier 	= 1;
	private static 	boolean contact 			= false;
	protected	 	boolean blinking 			= false;
	private static 	int 	on 					= 0;
	private static 	boolean on2 				= false;
	public 			int 	movementSpeed;
	protected static ImageIcon frightened 		= new ImageIcon("Sprites/frightenedGhost.png");
	protected static ImageIcon frightenedBlink 	= new ImageIcon("Sprites/frightenedGhostBlink.png");
		
	//constructor
	public Ghost(String upGhost, String downGhost, String leftGhost, String rightGhost, String pausedGhost, int x, int y, Player player) throws IOException {

		this.upImg = new ImageIcon(upGhost);
		this.downImg = new ImageIcon(downGhost);
		this.leftImg = new ImageIcon(leftGhost);
		this.rightImg = new ImageIcon(rightGhost);
		this.pausedImg = new ImageIcon(pausedGhost);

		//this.setPlayerNextDirection('L');
		this.currentImg = pausedImg.getImage();
		
		this.setPozX(x);
		this.setPozY(y);
		
		this.setMovementSpeed(GameSpeeds.SPEED75.value);

		
		playerInstance = player;
	}
	
	//calculates the next direction based on the target coordinates
	public void setNextDirection(int x, int y) {
		
		char[] direction = {' ', ' ', ' ', ' '};		// 0 = UP  1 = DOWN  2 = LEFT  3 = RIGHT		
		int[] dist = {0, 0, 0, 0};
		
		int distMin = 999999;


		//System.out.println("Position: " + this.getPozitionX() + " " + this.getPozitionY());
		
		if(getPlayerDirection() != 'D' && !checkUpDirectionException()) {
			if (!this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() - cellDim - (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() - cellDim - (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim / 2, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim / 2, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX(), this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))) {
				direction[0] = 'U';
				dist[0] = (int) ( Math.pow(this.getPozitionX() - x, 2) + Math.pow(this.getPozitionY() - cellDim - y, 2) );
				if(dist[0] < distMin) {
					distMin = dist[0];
				}
			}
		}

		if(getPlayerDirection() != 'U') {
			if (!this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() + cellDim + (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() + cellDim + (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim / 2, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim / 2, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX(), this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))) {
				direction[1] = 'D';
				dist[1] = (int) ( Math.pow(this.getPozitionX() - x, 2) + Math.pow(this.getPozitionY() + cellDim - y, 2) );
				if(dist[1] < distMin) {
					distMin = dist[1];
				}
			}
		}
		
		if(getPlayerDirection() != 'R') {
			if (!this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY())) {
				direction[2] = 'L';
				dist[2] = (int) ( Math.pow(this.getPozitionX() - cellDim - x, 2) + Math.pow(this.getPozitionY() - y, 2) );
				if(dist[2] < distMin) {
					distMin = dist[2];
				}
			}
		}
		
		if(getPlayerDirection() != 'L') {
			if (!this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY())) {
				direction[3] = 'R';
				dist[3] = (int) ( Math.pow(this.getPozitionX() + cellDim - x, 2) + Math.pow(this.getPozitionY() - y, 2) );
				if(dist[3] < distMin) {
					distMin = dist[3];
				}
			}
		}
		
		
		//System.out.println("Directiile posibile: " + direction[0] + " " + direction[1] + " " + direction[2] + " " + direction[3]);
		
		for(int i = 0; i < 4; i++) {
			if(direction[i] != ' ') {
				if(dist[i] == distMin) {
					this.setDirection(direction[i]);
					this.setCurrentImage(direction[i], this.getGameState());
					//System.out.println("Chose direction: " + direction[i]);
					i = 10;
				}
			}
		}
	}
	
	//for frightened mode, the ghost picks a random direction to travel in every intersection
public void setFrightenedDirection() {
		
		char[] direction = {' ', ' ', ' ', ' '};		// 0 = UP  1 = DOWN  2 = LEFT  3 = RIGHT		
		
		//System.out.println("Position: " + this.getPozitionX() + " " + this.getPozitionY());
		
		int k = 0;
		
		if(getPlayerDirection() != 'D') {
			if (!this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() - cellDim - (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() - cellDim - (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim / 2, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim / 2, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX(), this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))) {
				direction[0] = 'U';
				k++;
			}
		}

		if(getPlayerDirection() != 'U') {
			if (!this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() + cellDim + (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() + cellDim + (cellDim + cellOffset))
					&& !this.checkWalls(this.getPozitionX() - cellDim / 2, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX() + cellDim / 2, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.getPozitionX(), this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))) {
				direction[1] = 'D';
				k++;
			}
		}
		
		if(getPlayerDirection() != 'R') {
			if (!this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() - cellDim - (cellDim / 8 + cellOffset), this.getPozitionY())) {
				direction[2] = 'L';
				k++;
			}
		}
		
		if(getPlayerDirection() != 'L') {
			if (!this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim + cellOffset), this.getPozitionY() - cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim + cellOffset), this.getPozitionY() + cellDim)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() - cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY() + cellDim / 2)
					&& !this.checkWalls(this.getPozitionX() + cellDim + (cellDim / 8 + cellOffset), this.getPozitionY())) {
				direction[3] = 'R';
				k++;
			}
		}
				
		int i = (int) (Math.random() % 4);
		
		if(k > 0) {
			while(direction[i] == ' ') {
			
				i = (int) ((Math.random() * 100) % 4);
			}
			
			this.setDirection(direction[i]);
			
			//before changing back to normal mode, the ghost blink to show the immediate change
			if(this.isBlinking()) {
				if(on == 7) {
					
					on2 = !on2;
					on = 0;
				}
				else {
					
					on ++;
				}
				
				if(on2) {
					
					this.currentImg = frightenedBlink.getImage();
				}
				else {
					
					this.currentImg = frightened.getImage();
				}
			}
			else
			{
				this.currentImg = frightened.getImage();
			}
		}
	}

	//checks to see whether a ghost has colided with PacMan
	public boolean checkPlayerColission() {
		
		if(playerInstance.getPozitionX() >= this.getPozitionX() - cellDim && playerInstance.getPozitionX() <= this.getPozitionX() + cellDim &&
					playerInstance.getPozitionY() >= this.getPozitionY() - cellDim && playerInstance.getPozitionY() <= this.getPozitionY() + cellDim) {
			if(this.gameState != GhostState.FRIGHTENED && this.gameState != GhostState.EATEN) {	//if a collision has been detected and the ghost is either
				PacManController.setDyingAnimationState(true);			// in CHASE or in SCATTER, then PacMan gets eaten and the death animation is displayed
				PacManController.resetAnimationTimer();
				PacManController.resetGhosts(cellDim, cellOffset);
				this.setGameState(GhostState.DISABLED);
			}
			return true;
		}
		
		return false;
	}
	
	//sets current game state as 'state'
	public void setGameState(GhostState state)
	{
		this.gameState = state;
	}
	
	//returns current game state
	public GhostState getGameState()
	{
		return this.gameState;
	}
	
	//returns whether a contact was detected between a ghost and PacMan
	public static boolean playerContact(GhostState state) {
		
		if(state != GhostState.EATEN && state != GhostState.FRIGHTENED) {
			
			return contact;
		}
		
		else return false;
	}
	
	public static void setContact(boolean value) {
		
		contact = value;
	}
	
	//sets current movement speed to 'speed'
	public void setMovementSpeed(int speed) {
		
		this.movementSpeed = speed;
	}
	
	//returns current movement speed
	public int getMovementSpeed() {
		
		return this.movementSpeed;
	}
	
	//switches the current direction 180 degrees
	public char do180() {
		
		char oppositeDirection = ' ';
		char currentDireciton = this.getPlayerDirection();
		
		switch (currentDireciton) {
		
		case 'U' :
			oppositeDirection = 'D';
			break;
			
		case 'D' :
			oppositeDirection = 'U';
			break;
			
		case 'L' :
			oppositeDirection = 'R';
			break;
			
		case 'R' :
			oppositeDirection = 'L';
			break;
		}
		
		return oppositeDirection;
	}
	
	//checks for the exception in movement pattern for the ghosts
	private boolean checkUpDirectionException() {
		
		
		if(this.getPozitionX() > 10 * cellDim && this.getPozitionX() < 17 * cellDim) {
			
			if((this.getPozitionY() > 11 * cellDim && this.getPozitionY() < 12 * cellDim) ||
					(this.getPozitionY() > 23 * cellDim && this.getPozitionY() < 24 * cellDim)) {
				
				return true;
			}
		}
		
		return false;
	}
	
	//returns current value of the points multiplier
	public static int getMultiplier() {
		
		return pointsMultiplier;
	}
	
	//updates current value of the points multiplier
	public static void updatePointsMultiplier(int value) {
		
		pointsMultiplier = value;
	}
	
	//returns whether the ghost should be blinking at the moment
	public boolean isBlinking() {
		
		return this.blinking;
	}
	
}