package pacManPackage;

import java.io.IOException;

public class Blinky extends Ghost {
	
	// timer keeps track of the duration of states
	private int timer = 0;

	//constructor
	public Blinky(int x, int y, Player player) throws IOException{
		
		super("Sprites/upBlinky.png", "Sprites/downBlinky.png", "Sprites/leftBlinky.png",
								"Sprites/rightBlinky.png", "Sprites/pausedBlinky.png", x, y, player);
		
	}
	
	//keeps track of state changes and Blinky's movement in general
	public void action(int x, int y) {
		
		GhostState state = this.getGameState();
		//System.out.println(" " + this.getGameState());
		
		switch (state) {
		
		//updates the target to top right corner
		//updates current speed to SCATTER speed
		//switches to CHASE after the time passes
		case SCATTER :
			setNextDirection(32 * cellDim, 0 - 3 * cellDim);
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED75.value);
			GameEntity.setGhostEaten(0, false);
			//System.out.println(timer + "");
			if(timer >= 3500 / PacManController.getTimerSpeed()) {
				timer = 0;
				setGameState(GhostState.CHASE);
				char oppositeDirection = do180();
				
				this.setDirection(oppositeDirection);
				System.out.println("Blinky set to chase!");
			}
			break;
			
		//updates the target to the player's coordinates
		//updates current speed to CHASE speed
		//switches to SCATTER after the time passes
		case CHASE :
			setNextDirection(x, y);
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED75.value);
			GameEntity.setGhostEaten(0, false);
			if(timer >= 10000 / PacManController.getTimerSpeed()) {
				timer = 0;
				setGameState(GhostState.SCATTER);
				char oppositeDirection = do180();
				
				this.setDirection(oppositeDirection);
				System.out.println("Blinky set to scatter!");
			}
			break;
		
		//chooses a random direction to travel
		//updates current speed to FRIGHTENED speed
		//switches to CHASE after the time passes
		case FRIGHTENED :
			setFrightenedDirection();
			timer += this.getMovementSpeed();
			this.setMovementSpeed(GameSpeeds.SPEED50.value);
			GameEntity.setGhostEaten(0, false);
			if(timer >= 3000 / PacManController.getTimerSpeed()) {
				setGameState(GhostState.CHASE);
				Player.updateGameState(PlayerState.NORMAL);
				blinking = false;
				System.out.println("Blinky set to chase!");
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
			GameEntity.setGhostEaten(0, true);
			//System.out.println("Position: " + this.getPozitionX() + " " + this.getPozitionY());
			if(this.getPozitionX() == 14 * cellDim  + cellDim / 4 &&
					this.getPozitionY() < 16 * cellDim && this.getPozitionY() > 13 * cellDim) {
				
				this.setDirection(' ');
				setGameState(GhostState.RESPAWN);
				System.out.println("Blinky set to respawn!");
			}
			break;
			
		//updates the target to outside of the ghost house
		//switches to CHASE after the ghost has exited the ghost house
		case RESPAWN :
			setNextDirection(14 * cellDim  + cellDim / 4, 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
			GameEntity.setGhostEaten(0, true);
			if(this.getPozitionX() == 14 * cellDim  + cellDim / 4 &&
					this.getPozitionY() == 11 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset)) {
				
				setGameState(GhostState.CHASE);
				System.out.println("Blinky set to chase!");
			}
			break;
			
		//does nothing
		//only for development purposes
		case DISABLED :
			break;
		}
		
		//checks if Blinky has collided with the player and updates Blinky's state appropriately
		if (this.checkPlayerColission()) {
			
			if(state == GhostState.FRIGHTENED) {		//if Blinky is in FRIGHTENED mode,
														// then Blinky switches to EATEN upon player contact
				
				this.setGameState(GhostState.EATEN);
				this.blinking = false;
				Player.updateCurrentPoints(200 * Ghost.getMultiplier());
				Ghost.updatePointsMultiplier(Ghost.getMultiplier() * 2);
			}
			else {
				
				if(state != GhostState.EATEN) {
					
					//System.out.println("contact!");
					setContact(true);				//if Blinky is neither in FRIGHTENED mode, nor in EATEN mode
													// then Blinky eats PacMan
				}
			}
		}
		
		//Blinky's speed decreases when in tunnels
		if(this.getPozitionY() > 13 * cellDim + cellDim / 2 &&
				this.getPozitionY() < 16 * cellDim) {
			
			if(this.getPozitionX() > 23 * cellDim || this.getPozitionX() < 5 * cellDim ||
					(this.getPozitionX() > 11 * cellDim + cellDim / 2 && this.getPozitionX() < 16 * cellDim + cellDim / 2)) {
				
				this.setMovementSpeed(GameSpeeds.SPEED40.value);
			}
		}
		
		//getPlayerDirection actually gets Blinky's direction in this case
		//didn't get to changing the name yet ups
		move(getPlayerDirection());
	}
	
	//resets timer and switches direction 180 degrees
	public void resetTimer() {
		if(this.getGameState() == GhostState.FRIGHTENED) {
			System.out.println("Blinky frightened!");
			char oppositeDirection = do180();
			
			this.setDirection(oppositeDirection);
		}
		
		timer = 0;
	}

}
