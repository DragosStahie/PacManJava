package pacManPackage;

import java.io.IOException;

public class Inky extends Ghost {


	private 		int 	timer 		= 0;		//keeps track of state change timing
	private static 	int 	goCounter 	= 0;		//keeps track of when Inky is released from the ghost house
	
	private 		boolean go 			= false;
	private 		boolean dir 		= true;
	private static 	boolean inSpawn 	= true;		//keeps track of whether Inky is inside the ghost house
	private static	boolean isDisabled 	= false;	//keeps track of whether Inky is disabled


	public Inky(int x, int y, Player player) throws IOException{
		
		super("Sprites/upInky.png", "Sprites/downInky.png", "Sprites/leftInky.png",
								"Sprites/rightInky.png", "Sprites/pausedInky.png", x, y, player);
		
	}
	
	//keeps track of state changes and Inky's movement in general
	public void action(int x, int y) {
		
		GhostState state = this.getGameState();
		//System.out.println(" " + this.getGameState());
		
		switch (state) {
		
		//updates the target to bottom left corner
		//updates current speed to SCATTER speed
		//switches to CHASE after the time passes
		case SCATTER :
			setNextDirection(0 - 3 * cellDim, 34 * cellDim);
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED75.value);
			GameEntity.setGhostEaten(2, false);
			inSpawn = false;
			//System.out.println(timer + "");
			if(timer >= 3500 / PacManController.getTimerSpeed()) {
				timer = 0;
				setGameState(GhostState.CHASE);
				char oppositeDirection = do180();
				
				this.setDirection(oppositeDirection);
				System.out.println("Inky set to chase!");
			}
			break;
			
		//updates the target to the calculated coordinates
		//updates current speed to CHASE speed
		//switches to SCATTER after the time passes
		case CHASE :
			setNextDirection(x, y);
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED75.value);
			GameEntity.setGhostEaten(2, false);
			inSpawn = false;
			if(timer >= 10000 / PacManController.getTimerSpeed()) {
				timer = 0;
				setGameState(GhostState.SCATTER);
				char oppositeDirection = do180();
				
				this.setDirection(oppositeDirection);
				System.out.println("Inky set to scatter!");
			}
			break;
			
		//chooses a random direction to travel
		//updates current speed to FRIGHTENED speed
		//switches to CHASE after the time passes
		case FRIGHTENED :
			setFrightenedDirection();
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED50.value);
			GameEntity.setGhostEaten(2, false);
			inSpawn = false;
			if(timer >= 3000 / PacManController.getTimerSpeed()) {
				setGameState(GhostState.CHASE);
				Player.updateGameState(PlayerState.NORMAL);
				blinking = false;
				System.out.println("Inky set to chase!");
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
			GameEntity.setGhostEaten(2, true);
			inSpawn = false;
			//System.out.println("Position: " + this.getPozitionX() + " " + this.getPozitionY());
			if(this.getPozitionX() > 12 * cellDim && this.getPozitionX() < 16 * cellDim &&
					this.getPozitionY() < 16 * cellDim && this.getPozitionY() > 14 * cellDim) {
				
				this.setDirection(' ');
				setGameState(GhostState.RESPAWN);
				System.out.println("Inky set to respawn!");
			}
			break;
		
		//updates the target to outside of the ghost house after the player has eaten the set amount of pellets
		//switches to SCATTER after the ghost has exited the ghost house
		case SPAWN :
			
			inSpawn = true;
			
			if(go) {
				
				setNextDirection(14 * cellDim  + cellDim / 4, 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
				GameEntity.setGhostEaten(2, true);
				if(this.getPozitionX() > 12 * cellDim && this.getPozitionX() < 16 * cellDim &&
						this.getPozitionY() < 12 * cellDim && this.getPozitionY() > 10 * cellDim) {
					
					setGameState(GhostState.SCATTER);
					System.out.println("Inky set to scatter!");
				}
			}
			else {
				
				if(dir) {
					
					this.setDirection('D');
					this.setCurrentImage('D', this.getGameState());
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
					//System.out.println("Here I be");
				}
			}
			
			if(goCounter >= 30) {
				
				go = true;
				inSpawn = false;
				this.setMovementSpeed(GameSpeeds.SPEED75.value);
			}
			
			break;
			
		//updates the target to outside of the ghost house
		//switches to CHASE after the ghost has exited the ghost house
		case RESPAWN :
			setNextDirection(14 * cellDim  + cellDim / 4, 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
			GameEntity.setGhostEaten(2, true);
			
			inSpawn = true;
			
			if(this.getPozitionX() > 12 * cellDim && this.getPozitionX() < 16 * cellDim &&
					this.getPozitionY() < 12 * cellDim && this.getPozitionY() > 10 * cellDim) {
				
				setGameState(GhostState.CHASE);
				System.out.println("Inky set to chase!");
			}
			break;
			
		//does nothing
		//only for development purposes
		case DISABLED :
			break;
		}
		
		//System.out.println("Inky: " + this.getGameState());
		
		//checks if Inky has collided with the player and updates Inky's state appropriately
		if (this.checkPlayerColission()) {
			
			if(state == GhostState.FRIGHTENED) {		//if Inky is in FRIGHTENED mode,
														// then Inky switches to EATEN upon player contact
				
				this.setGameState(GhostState.EATEN);
				this.blinking = false;
				Player.updateCurrentPoints(200 * Ghost.getMultiplier());
				Ghost.updatePointsMultiplier(Ghost.getMultiplier() * 2);
			}
			else {
				
				if(state != GhostState.EATEN) {
					
					//System.out.println("contact!");
					setContact(true);					//if Inky is neither in FRIGHTENED mode, nor in EATEN mode
														// then Inky eats PacMan
				}
			}
		}
		
		//Inky moves slower inside the ghost house and inside tunnels
		if(inSpawn) {
			this.setMovementSpeed(GameSpeeds.SPEED20.value);
			//System.out.println("Inky in spawn");
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
			System.out.println("Inky frightened!");

			char oppositeDirection = do180();
			
			this.setDirection(oppositeDirection);
		}
		
		timer = 0;
	}
	
	//increments the counter that releases Inky out of the ghost house
	public static void incrementCounter() {
		
		goCounter++;
		//System.out.println("Counter: " + goCounter);
	}
	
	//resets auxilary variable 'go'
	public void resetGo() {
		
		go = false;
	}
	
	//returns whether Inky is inside the ghost house
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
