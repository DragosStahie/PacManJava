package pacManPackage;

import java.io.IOException;

public class Pinky extends Ghost {

	private 		int 	timer 		= 0;		//keeps track of state change timing
	private static 	int 	goCounter 	= 0;		//keeps track of when Pinky is released from the ghost house
	
	private 		boolean go 			= false;
	private 		boolean dir 		= true;
	private static 	boolean inSpawn 	= true;		//keeps track of whether Pinky is inside the ghost house
	private static	boolean isDisabled 	= false;

	//constructor
	public Pinky(int x, int y, Player player) throws IOException{
		
		super("Sprites/upPinky.png", "Sprites/downPinky.png", "Sprites/leftPinky.png",
								"Sprites/rightPinky.png", "Sprites/pausedPinky.png", x, y, player);
		
	}
	
	//keeps track of state changes and Pinky's movement in general
	public void action(int x, int y) {
		
		GhostState state = this.getGameState();
		//System.out.println(" " + this.getGameState());
		
		switch (state) {
		
		//updates the target to top left corner
		//updates current speed to SCATTER speed
		//switches to CHASE after the time passes
		case SCATTER :
			setNextDirection(0 - 3 * cellDim, 0 - 3 * cellDim);
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED75.value);
			GameEntity.setGhostEaten(1, false);
			inSpawn = false;
			//System.out.println(timer + "");
			if(timer >= 3500 / PacManController.getTimerSpeed()) {
				timer = 0;
				setGameState(GhostState.CHASE);
				char oppositeDirection = do180();
				
				this.setDirection(oppositeDirection);
				//System.out.println("Pinky set to chase!");
			}
			break;
		
		//updates the target to the calculated coordinates
		//updates current speed to CHASE speed
		//switches to SCATTER after the time passes
		case CHASE :
			setNextDirection(x, y);
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED75.value);
			GameEntity.setGhostEaten(1, false);
			inSpawn = false;
			if(timer >= 10000 / PacManController.getTimerSpeed()) {
				timer = 0;
				setGameState(GhostState.SCATTER);
				char oppositeDirection = do180();
				
				this.setDirection(oppositeDirection);
				//System.out.println("Pinky set to scatter!");
			}
			break;
		
		//chooses a random direction to travel
		//updates current speed to FRIGHTENED speed
		//switches to CHASE after the time passes
		case FRIGHTENED :
			setFrightenedDirection();
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED50.value);
			GameEntity.setGhostEaten(1, false);
			inSpawn = false;
			if(timer >= 3000 / PacManController.getTimerSpeed()) {
				setGameState(GhostState.CHASE);
				Player.updateGameState(PlayerState.NORMAL);
				blinking = false;
				//System.out.println("Pinky set to chase!");
			}
			else {
				
				if(timer >= 2200 / PacManController.getTimerSpeed()) {
					
					blinking = true;
				}
			}
			break;
		
		//sets the target as the respawn point
		//updates current speed to SUPERSPEED
		//switches to RESPAWN after the ghost's eyes reach the spawn point
		case EATEN :
			setNextDirection(14 * cellDim  + cellDim / 4, 14 * cellDim + cellDim / 2);
			this.setMovementSpeed(GameSpeeds.SUPERSPEED.value);
			GameEntity.setGhostEaten(1, true);
			inSpawn = false;
			isDisabled = false;
			//System.out.println("Position: " + this.getPozitionX() + " " + this.getPozitionY());
			if(this.getPozitionX() == 14 * cellDim  + cellDim / 4 &&
					this.getPozitionY() < 16 * cellDim && this.getPozitionY() > 13 * cellDim) {
				
				this.setDirection(' ');
				setGameState(GhostState.RESPAWN);
				//System.out.println("Pinky set to respawn!");
			}
			break;
		
		//updates the target to outside of the ghost house after the player has eaten the set amount of pellets
		//switches to SCATTER after the ghost has exited the ghost house
		case SPAWN :
			
			inSpawn = true;
			
			if(go) {
				
				setNextDirection(14 * cellDim  + cellDim / 4, 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
				GameEntity.setGhostEaten(1, true);
				if(this.getPozitionX() == 14 * cellDim  + cellDim / 4 &&
						this.getPozitionY() == 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset)) {
					
					setGameState(GhostState.SCATTER);
					this.setDirection('L');
					System.out.println("Pinky set to scatter!");
				}
			}
			else {
				
				if(dir) {
					
					this.setDirection('U');
					this.setCurrentImage('U', this.getGameState());
					dir = false;
				}
				
				if(this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX() - cellDim / 2, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX() + cellDim / 2, this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX(), this.getPozitionY() - cellDim - (cellDim / 8 + cellOffset))) {
					
					this.setDirection('D');
					this.setCurrentImage('D', this.getGameState());
				}
				
				if(this.checkWalls(this.getPozitionX() - cellDim, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX() + cellDim, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX() - cellDim / 2, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX() + cellDim / 2, this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))
						|| this.checkWalls(this.getPozitionX(), this.getPozitionY() + cellDim + (cellDim / 8 + cellOffset))) {
					
					this.setDirection('U');
					this.setCurrentImage('U', this.getGameState());
					go = true;
				}
			}
			
			break;
		
		//updates the target to outside of the ghost house
		//switches to CHASE after the ghost has exited the ghost house
		case RESPAWN :
			setNextDirection(14 * cellDim  + cellDim / 4, 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
			GameEntity.setGhostEaten(1, true);
			
			inSpawn = true;
			
			if(this.getPozitionX() == 14 * cellDim  + cellDim / 4 &&
					this.getPozitionY() == 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset)) {
				
				setGameState(GhostState.CHASE);
				//System.out.println("Pinky set to chase!");
			}
			break;
		
		//does nothing
		//only for development purposes
		case DISABLED :
			break;
		}
		
		//System.out.println("Pinky: " + this.getGameState());
		
		//checks if Pinky has collided with the player and updates Pinky's state appropriately
		if (this.checkPlayerColission()) {
			
			if(state == GhostState.FRIGHTENED) {			//if Pinky is in FRIGHTENED mode,
															// then Pinky switches to EATEN upon player contact
				
				this.setGameState(GhostState.EATEN);
				this.blinking = false;
				Player.updateCurrentPoints(200 * Ghost.getMultiplier());
				Ghost.updatePointsMultiplier(Ghost.getMultiplier() * 2);
			}
			else {
				
				if(state != GhostState.EATEN) {
					
					//System.out.println("contact!");
					setContact(true);						//if Pinky is neither in FRIGHTENED mode, nor in EATEN mode
															// then Pinky eats PacMan
				}
			}
		}
		
		//System.out.println("Disabled: " + isDisabled);
		
		//Pinky moves slower inside the ghost house and inside tunnels
		if(inSpawn) {
			
			this.setMovementSpeed(GameSpeeds.SPEED20.value);
		}
		else {
			
			if(this.getPozitionY() > 13 * cellDim + cellDim / 2 &&
					this.getPozitionY() < 16 * cellDim) {
				
				if(this.getPozitionX() > 23 * cellDim || this.getPozitionX() < 5 * cellDim) {
					
					this.setMovementSpeed(GameSpeeds.SPEED40.value);
				}
			}
		}
		
		move(getPlayerDirection());
	}
	
	//resets timer and switches direction 180 degrees
	public void resetTimer() {
		if(this.getGameState() == GhostState.FRIGHTENED) {
			System.out.println("Pinky frightened!");

			char oppositeDirection = do180();
			
			this.setDirection(oppositeDirection);
		}
		
		timer = 0;
	}
	
	public static void incrementCounter() {
		
		goCounter++;
	}
	
	public void resetGo() {
		
		go = false;
	}
	
	//returns whether Pinky is inside the ghost house
	public static boolean inSpawn() {
		
		return inSpawn;
	}
	
	public static void setDisabled(boolean value) {
		
		isDisabled = value;
	}
	
	public static boolean isDisabled() {
		
		return isDisabled;
	}
}
