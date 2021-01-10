package pacManPackage;

import java.awt.Image;

import javax.swing.ImageIcon;

//The GameEntity class implements movements patterns and methods common
//	to any entity from the game (either PacMan or one of the ghosts)
public class GameEntity {

	private int pozX;
	private int pozY;
	protected static int cellDim;
	protected static int cellOffset = 0;
	private static int mazeWidth;
	private static int mazeHeight;
	private char direction = ' ';
	private char nextDirection = ' ';
	private static int k = 0;
	
	private static boolean frightened = false;
	
	///////////////////////////////////////Blinky, Pinky, Inky , Clyde
	private static boolean[] eatenGhosts = {false, false, false, false};	//stores for each ghost whether it is in its EATEN state
	
	private static Walls[] wallArray;			//an array that holds the walls of the maze

	private static Collectable[] smallPelets;	//an array that holds the small pellets within the maze

	private static Collectable[] bigPelets;		//an array that holds the big pellets within the maze
	
	protected ImageIcon upImg;
	protected ImageIcon downImg;
	protected ImageIcon leftImg;
	protected ImageIcon rightImg;
	protected ImageIcon pausedImg;
	protected Image currentImg;
	
	protected static ImageIcon eyesUp = new ImageIcon("Sprites/ghostEyesUp.png");
	protected static ImageIcon eyesDown = new ImageIcon("Sprites/ghostEyesDown.png");
	protected static ImageIcon eyesLeft = new ImageIcon("Sprites/ghostEyesLeft.png");
	protected static ImageIcon eyesRight = new ImageIcon("Sprites/ghostEyesRight.png");
	
	
	//changes direction to the next set direction whenever it is possible
	//checks for walls and when the path is clear it changes the current direction
	public void changeDirection() {
		char direction = this.nextDirection;
		int offset = (cellDim / 8 + cellOffset);

		switch (direction) {
		case ' ':
			break;

			//check if the path upwards is clear
		case 'U':
			if (!this.checkWalls(this.pozX - cellDim + offset, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim - offset, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX - cellDim / 2, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim / 2, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX, this.pozY - cellDim - (cellDim / 8 + cellOffset))) {
				this.direction = direction;
				this.currentImg = this.upImg.getImage();
			}
			break;

			//check if the path downwards is clear
		case 'D':
			if (!this.checkWalls(this.pozX - cellDim + offset, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim - offset, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX - cellDim / 2, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim / 2, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX, this.pozY + cellDim + (cellDim / 8 + cellOffset))) {
				this.direction = direction;
				this.currentImg = this.downImg.getImage();
			}
			break;

			//check if the path leftwards is clear
		case 'L':
			if (!this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY - cellDim + offset)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY + cellDim - offset)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY - cellDim / 2)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY + cellDim / 2)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY)) {
				this.direction = direction;
				this.currentImg = this.leftImg.getImage();
			}
			break;

			//check if the path rightwards is clear
		case 'R':
			if (!this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY - cellDim + offset)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY + cellDim - offset)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY - cellDim / 2)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY + cellDim / 2)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY)) {
				this.direction = direction;
				this.currentImg = this.rightImg.getImage();
			}
			break;
		}
	}

	//moves the entity in the current direction until either the direction is changed
	// or it reaches a wall and it stops to wait for a clear direction
	public void move(char direction) {
		
		int offset = 0;
		if(this instanceof Player) {
			this.changeDirection();
			
			//updates the player's state on every 15th move of the player
			if(k > 15) {
				
				if(Player.getGameState() == PlayerState.DOTS) {
										
					Player.updateGameState(PlayerState.NORMAL);
				}
				
				if(Player.getGameState() == PlayerState.FRIGHT_DOTS) {
					
					Player.updateGameState(PlayerState.FRIGHT);
				}
				k = 0;
			}
			else {
				
				k++;
			}
			
		}

		switch (direction) {
		case ' ':
			break;

		case 'U':
			if (!this.checkWalls(this.pozX - cellDim + offset, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim - offset, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX - cellDim / 2, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim / 2, this.pozY - cellDim - (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX, this.pozY - cellDim - (cellDim / 8 + cellOffset)))
				{
				this.pozY -= (cellDim / 8 + cellOffset);
				//System.out.println(cellDim + "");
				}
			if(this instanceof Player) {
				this.checkSmallPellet(this.pozX, this.pozY);
				this.checkBigPellet(this.pozX, this.pozY);
			}
			break;

		case 'D':
			if (!this.checkWalls(this.pozX - cellDim + offset, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim - offset, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX - cellDim / 2, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX + cellDim / 2, this.pozY + cellDim + (cellDim / 8 + cellOffset))
					&& !this.checkWalls(this.pozX, this.pozY + cellDim + (cellDim / 8 + cellOffset)))
				{
				this.pozY += (cellDim / 8 + cellOffset);
				//System.out.println(cellDim + "");
				}
			if(this instanceof Player) {			//if the entity being moved is the player it checks to see
				this.checkSmallPellet(this.pozX, this.pozY);	// whether the player can eat any collectible 
				this.checkBigPellet(this.pozX, this.pozY);
			}
			break;

		case 'L':
			if (!this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY - cellDim + offset)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY + cellDim - offset)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY - cellDim / 2)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY + cellDim / 2)
					&& !this.checkWalls(this.pozX - cellDim - (cellDim / 8 + cellOffset), this.pozY))
				{
				this.pozX -= (cellDim / 8 + cellOffset);
				//System.out.println(cellDim + "");
				}
			
			if (this.pozX < 0 - 3 * cellDim) {					//if the player passes through the tunnel from the left side
				this.pozX = 210 * (cellDim / 8 + cellOffset);		// teleport it to the right side of the maze
			}
			
			if(this instanceof Player) {
				this.checkSmallPellet(this.pozX, this.pozY);
				this.checkBigPellet(this.pozX, this.pozY);
			}
			break;

		case 'R':
			if (!this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY - cellDim + offset)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY + cellDim - offset)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY - cellDim / 2)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY + cellDim / 2)
					&& !this.checkWalls(this.pozX + cellDim + (cellDim / 8 + cellOffset), this.pozY))
				{
				this.pozX += (cellDim / 8 + cellOffset);
				//System.out.println(cellDim + "");
				}
			
			if (this.pozX > 31 * cellDim) {							//if the player passes through the tunnel from the right side
				this.pozX = 0 - 27 * (cellDim / 8 + cellOffset);		// teleport it to the left side of the maze
			}
			if(this instanceof Player) {
				this.checkSmallPellet(this.pozX, this.pozY);
				this.checkBigPellet(this.pozX, this.pozY);
			}
			break;
		}

		 //System.out.println(this.pozX + " " + this.pozY);
	}

	//checks for walls in the specified coordinates
	public boolean checkWalls(int x, int y) {
		for (int i = 0; i < wallArray.length; i++) {
			int[] wallCoords = wallArray[i].getWallCoordinates();
			
			if(i == 54 && ((this instanceof Blinky && eatenGhosts[0]) || (this instanceof Pinky && eatenGhosts[1]) ||
						(this instanceof Inky && eatenGhosts[2]) || (this instanceof Clyde && eatenGhosts[3]))) {
				return false;				
			}
			else {
				if (x >= wallCoords[0] && x <= wallCoords[1] && y >= wallCoords[2] && y <= wallCoords[3]) {
					 //System.out.println("True: " + x + " " + y);
					return true;
				}
			}
		}

		// System.out.println("False: " + x + " " + y);
		return false;
	}


	//checks for small pellets at the specified coordinates
	public void checkSmallPellet(int x, int y) {
		for (Collectable i : smallPelets) {
			if (Player.getPelletCount() > 0 && i.getVisibilityStatus()) {
				int pelletX = i.getX();
				int pelletY = i.getY();
				if (pelletX >= x - cellDim / 2 && pelletY >= y - cellDim / 2 && pelletX <= x + cellDim / 2
						&& pelletY <= y + cellDim / 2) {
					i.setVisibilityStatus(false);
					Player.updatePelletCount(-1);
					Player.updateCurrentPoints(10);

					if(!Pinky.inSpawn() || Pinky.isDisabled()) {
						
						if(Inky.inSpawn() && !Inky.isDisabled()) {
							Inky.incrementCounter();
						}
						else {
							if(Clyde.inSpawn()) {
								Clyde.incrementCounter();
							}
						}
					}
					
					if(Player.getGameState() == PlayerState.NORMAL) {
						
						Player.updateGameState(PlayerState.DOTS);
					}
					
					if(Player.getGameState() == PlayerState.FRIGHT) {
						
						Player.updateGameState(PlayerState.FRIGHT_DOTS);
					}
				}
			}
		}
	}

	//checks for big pellets at the specified coordinates
	public void checkBigPellet(int x, int y) {
		for (Collectable i : bigPelets) {
			if (Player.getPelletCount() > 0 && i.getVisibilityStatus()) {
				int pelletX = i.getX();
				int pelletY = i.getY();
				if (pelletX >= x - cellDim / 2 && pelletY >= y - cellDim / 2 && pelletX <= x + cellDim / 2
						&& pelletY <= y + cellDim / 2) {
					Ghost.updatePointsMultiplier(1);
					i.setVisibilityStatus(false);
					frightened = true;
					Player.updatePelletCount(-1);
					Player.updateCurrentPoints(50);
					Player.updateGameState(PlayerState.FRIGHT);
				}
			}
		}
	}

	//returns current X coordinate
	public int getPozitionX() {
		return this.pozX;
	}

	//returns current Y coordinate
	public int getPozitionY() {
		return this.pozY;
	}

	//returns current image
	public Image getCurrentImage() {
		return this.currentImg;
	}

	//returns current direction
	public char getPlayerDirection() {
		return this.direction;
	}
	
	//sets current direction as 'direction'
	public void setDirection(char direction)
	{
		this.direction = direction;
	}

	//sets next direction as 'direction'
	public void setPlayerNextDirection(char direction) {
		this.nextDirection = direction;
		//System.out.println("Direction set to " + direction);
	}
	
	//returns next direction
	public char getNextDirection() {
		return this.nextDirection;
	}

	//returns the small pellets array
	public Collectable[] getPelletsArray() {
		return smallPelets;
	}

	//returns the big pellets array
	public Collectable[] getPowerupsArray() {
		return bigPelets;
	}
	
	//returns the current frightened state
	public static boolean getFrightenedState()
	{
		return frightened;
	}
	
	//sets current frightened state to false
	public static void resetFrightenedState() {
		frightened = false;
	}

	//sets current cell dimension and calls the initialisation of the walls and the collectibles
	public void setCellDim(int cellDimension, int width, int height)
	{
		cellDim = cellDimension;
		if(cellDim % 8 != 0)
		{
			cellOffset = 1;
		}
		
		mazeWidth = width + cellDim / 4;
		mazeHeight = height + cellDim / 4;
		this.setPozX( 14 * cellDim  + cellDim / 4);
		this.setPozY( 23 * cellDim + cellDim / 2 + (cellDim / 8 + cellOffset));
		this.initWalls();
		this.initPellets();
	}
	
	//initializes the walls coordinates
	private void initWalls()
	{
		int verticalOffset = cellDim / 4;
		int horizontalOffset = cellDim / 4 - cellOffset;
		if(cellDim == 19 || cellDim == 17 || cellDim == 15) {
			
			horizontalOffset += cellOffset;
		}

		wallArray = new Walls[] { new Walls(0, mazeWidth, 0, cellDim / 2), // 1
				new Walls(0, mazeWidth, mazeHeight - cellDim / 2, mazeHeight), // 2
				new Walls(0, cellDim / 2, 0, 10 * cellDim), // 3
				new Walls(27 * cellDim + cellDim / 2 + horizontalOffset, 28 * cellDim, 0, 10 * cellDim), // 4
				new Walls(0, 5 * cellDim + cellDim / 2, 10 * cellDim + horizontalOffset - cellDim / 2, 10 * cellDim + verticalOffset), // 5
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, mazeWidth, 10 * cellDim + verticalOffset - cellDim / 2, 10 * cellDim + verticalOffset), // 6
				new Walls(5 * cellDim, 5 * cellDim + cellDim / 2, 10 * cellDim + horizontalOffset - cellDim / 2, 13 * cellDim + cellDim / 2), // 7
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, 23 * cellDim, 10 * cellDim + verticalOffset - cellDim / 2, 13 * cellDim + cellDim / 2), // 8
				new Walls(0 - 10 * cellDim, 5 * cellDim + cellDim / 2, 13 * cellDim, 13 * cellDim + cellDim / 2), // 9
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, mazeWidth + 10 * cellDim, 13 * cellDim + cellDim / 2, 13 * cellDim + cellDim / 2), // 10
				new Walls(0 - 10 * cellDim, 5 * cellDim + cellDim / 2, 16 * cellDim + verticalOffset - cellDim / 2, 16 * cellDim + verticalOffset), // 11
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, mazeWidth + 10 * cellDim, 16 * cellDim + verticalOffset - cellDim / 2, 16 * cellDim + verticalOffset), // 12
				new Walls(5 * cellDim, 5 * cellDim + cellDim / 2, 16 * cellDim + verticalOffset - cellDim / 2, 19 * cellDim + cellDim / 2), // 13
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, 23 * cellDim, 16 * cellDim + verticalOffset - cellDim / 2, 19 * cellDim + cellDim / 2), // 14
				new Walls(0, 5 * cellDim + cellDim / 2, 19 * cellDim, 19 * cellDim + cellDim / 2), // 15
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, mazeWidth, 19 * cellDim, 19 * cellDim + cellDim / 2), // 16
				new Walls(0, cellDim / 2, 19 * cellDim, mazeHeight), // 17
				new Walls(27 * cellDim + cellDim / 2 + horizontalOffset, 28 * cellDim, 19 * cellDim, mazeHeight), // 18
				new Walls(13 * cellDim + horizontalOffset + cellDim / 2, 14 * cellDim + cellDim / 2, 0, 4 * cellDim + cellDim / 2), // 19
				new Walls(0, 2 * cellDim + cellDim / 2, 24 * cellDim + verticalOffset + cellDim / 2, 25 * cellDim + cellDim / 2), // 20
				new Walls(25 * cellDim + cellDim / 2 + horizontalOffset, 28 * cellDim  + horizontalOffset, 24 * cellDim + verticalOffset + cellDim / 2, 25 * cellDim + cellDim / 2), // 21
				new Walls(2 * cellDim + horizontalOffset + cellDim / 2, 5 * cellDim + cellDim / 2, 2 * cellDim + verticalOffset + cellDim / 2, 4 * cellDim + cellDim / 2), // 22
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, 25 * cellDim + cellDim / 2, 2 * cellDim + verticalOffset + cellDim / 2, 4 * cellDim + cellDim / 2), // 23
				new Walls(7 * cellDim + horizontalOffset + cellDim / 2, 11 * cellDim + cellDim / 2, 2 * cellDim + verticalOffset + cellDim / 2, 4 * cellDim + cellDim / 2), // 24
				new Walls(16 * cellDim + horizontalOffset + cellDim / 2, 20 * cellDim + cellDim / 2, 2 * cellDim + verticalOffset + cellDim / 2, 4 * cellDim + cellDim / 2), // 25
				new Walls(2 * cellDim + horizontalOffset + cellDim / 2, 5 * cellDim + cellDim / 2, 6 * cellDim + verticalOffset + cellDim / 2, 7 * cellDim + cellDim / 2), // 26
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, 25 * cellDim + cellDim / 2, 6 * cellDim + verticalOffset + cellDim / 2, 7 * cellDim + cellDim / 2), // 27
				new Walls(7 * cellDim + horizontalOffset + cellDim / 2, 8 * cellDim + cellDim / 2, 6 * cellDim + verticalOffset + cellDim / 2, 13 * cellDim + cellDim / 2), // 28
				new Walls(19 * cellDim + horizontalOffset + cellDim / 2, 20 * cellDim + cellDim / 2, 6 * cellDim + verticalOffset + cellDim / 2, 13 * cellDim + cellDim / 2), // 29
				new Walls(7 * cellDim + horizontalOffset + cellDim / 2, 11 * cellDim + cellDim / 2, 10 * cellDim + verticalOffset - cellDim / 2, 11 * cellDim - cellDim / 2), // 30
				new Walls(16 * cellDim + horizontalOffset + cellDim / 2, 20 * cellDim + cellDim / 2, 10 * cellDim + verticalOffset - cellDim / 2, 11 * cellDim - cellDim / 2), // 31
				new Walls(10 * cellDim + horizontalOffset + cellDim / 2, 17 * cellDim + cellDim / 2, 6 * cellDim + verticalOffset + cellDim / 2, 7 * cellDim + cellDim / 2), // 32
				new Walls(13 * cellDim + horizontalOffset + cellDim / 2, 14 * cellDim + cellDim / 2, 6 * cellDim + verticalOffset + cellDim / 2, 10 * cellDim + cellDim / 2), // 33
				new Walls(7 * cellDim + horizontalOffset + cellDim / 2, 8 * cellDim + cellDim / 2, 16 * cellDim + verticalOffset - cellDim / 2, 19 * cellDim + cellDim / 2), // 34
				new Walls(19 * cellDim + horizontalOffset + cellDim / 2, 20 * cellDim + cellDim / 2, 16 * cellDim + verticalOffset - cellDim / 2, 19 * cellDim + cellDim / 2), // 35
				new Walls(10 * cellDim + horizontalOffset + cellDim / 2, 17 * cellDim + cellDim / 2, 18 * cellDim + verticalOffset + cellDim / 2, 19 * cellDim + cellDim / 2), // 36
				new Walls(13 * cellDim + horizontalOffset + cellDim / 2, 14 * cellDim + cellDim / 2, 18 * cellDim + verticalOffset + cellDim / 2, 22 * cellDim + cellDim / 2), // 37
				new Walls(7 * cellDim + horizontalOffset + cellDim / 2, 11 * cellDim + cellDim / 2, 21 * cellDim + verticalOffset + cellDim / 2, 22 * cellDim + cellDim / 2), // 38
				new Walls(16 * cellDim + horizontalOffset + cellDim / 2, 20 * cellDim + cellDim / 2, 21 * cellDim + verticalOffset + cellDim / 2, 22 * cellDim + cellDim / 2), // 39
				new Walls(2 * cellDim + horizontalOffset + cellDim / 2, 5 * cellDim + cellDim / 2, 21 * cellDim + verticalOffset + cellDim / 2, 22 * cellDim + cellDim / 2), // 40
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, 25 * cellDim + cellDim / 2, 21 * cellDim + verticalOffset + cellDim / 2, 22 * cellDim + cellDim / 2), // 41
				new Walls(4 * cellDim + horizontalOffset + cellDim / 2, 5 * cellDim + cellDim / 2, 21 * cellDim + verticalOffset + cellDim / 2, 25 * cellDim + cellDim / 2), // 42
				new Walls(22 * cellDim + cellDim / 2 + horizontalOffset, 23 * cellDim + cellDim / 2, 21 * cellDim + verticalOffset + cellDim / 2, 25 * cellDim + cellDim / 2), // 43
				new Walls(10 * cellDim + horizontalOffset + cellDim / 2, 17 * cellDim + cellDim / 2, 24 * cellDim + verticalOffset + cellDim / 2, 25 * cellDim + cellDim / 2), // 44
				new Walls(13 * cellDim + horizontalOffset + cellDim / 2, 14 * cellDim + cellDim / 2, 24 * cellDim + verticalOffset + cellDim / 2, 28 * cellDim + cellDim / 2), // 45
				new Walls(2 * cellDim + horizontalOffset + cellDim / 2, 11 * cellDim + cellDim / 2, 27 * cellDim + verticalOffset + cellDim / 2, 28 * cellDim + cellDim / 2), // 46
				new Walls(16 * cellDim + horizontalOffset + cellDim / 2, 25 * cellDim + cellDim / 2, 27 * cellDim + verticalOffset + cellDim / 2, 28 * cellDim + cellDim / 2), // 47
				new Walls(7 * cellDim + horizontalOffset + cellDim / 2, 8 * cellDim + cellDim / 2, 24 * cellDim + verticalOffset + cellDim / 2, 28 * cellDim + cellDim / 2), // 48
				new Walls(19 * cellDim + horizontalOffset + cellDim / 2, 20 * cellDim + cellDim / 2, 24 * cellDim + verticalOffset + cellDim / 2, 28 * cellDim + cellDim / 2), // 49
				new Walls(10 * cellDim + horizontalOffset + cellDim / 2, 17 * cellDim + cellDim / 2, 16 * cellDim + verticalOffset, 16 * cellDim + cellDim / 2), // 50 - zona fantome
				new Walls(10 * cellDim + horizontalOffset + cellDim / 2, 11 * cellDim, 12 * cellDim + verticalOffset + cellDim / 2, 16 * cellDim + cellDim / 2), // 51 - zona fantome
				new Walls(17 * cellDim + horizontalOffset, 17 * cellDim + cellDim / 2, 12 * cellDim + verticalOffset + cellDim / 2, 16 * cellDim + cellDim / 2), // 52 - zona fantome
				new Walls(10 * cellDim + horizontalOffset + cellDim / 2, 13 * cellDim, 12 * cellDim + verticalOffset + cellDim / 2, 13 * cellDim), 				// 53 - zona fantome
				new Walls(15 * cellDim + horizontalOffset, 17 * cellDim + cellDim / 2, 12 * cellDim + verticalOffset + cellDim / 2, 13 * cellDim), 				// 54 - zona fantome
				new Walls(13 * cellDim, 15 * cellDim, 12 * cellDim + verticalOffset + cellDim / 2, 13 * cellDim), 												// 55 - poarta fantome

		};
	}

	//initializes the small pellets' coordinates
	private void initPellets() {
		
		smallPelets = new Collectable[] {
				// row 1
				 new Collectable(cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(2 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(3 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(4 * cellDim + cellDim / 2, cellDim + cellDim / 2),
						new Collectable(5 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(7 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(8 * cellDim + cellDim / 2, cellDim + cellDim / 2),
						new Collectable(9 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(10 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(11 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, cellDim + cellDim / 2),
						new Collectable(15 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(16 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(17 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(18 * cellDim + cellDim / 2, cellDim + cellDim / 2),
						new Collectable(19 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(20 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(21 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(22 * cellDim + cellDim / 2, cellDim + cellDim / 2),
						new Collectable(23 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(24 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(25 * cellDim + cellDim / 2, cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, cellDim + cellDim / 2),
						// row 2
						new Collectable(cellDim + cellDim / 2, 2 * cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 2 * cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 2 * cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 2 * cellDim + cellDim / 2),
						new Collectable(21* cellDim + cellDim / 2, 2 * cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 2 * cellDim + cellDim / 2),
						// row 3
						new Collectable(6 * cellDim + cellDim / 2, 3 * cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 3 * cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 3 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 3 * cellDim + cellDim / 2),
						// row 4
						new Collectable(cellDim + cellDim / 2, 4 * cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 4 * cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 4 * cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 4 * cellDim + cellDim / 2),
						new Collectable(21 * cellDim + cellDim / 2, 4 * cellDim + cellDim / 2), new Collectable(26 * cellDim + cellDim / 2, 4 * cellDim + cellDim / 2),
						// row 5
						new Collectable(cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(2 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(3 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(4 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2),
						new Collectable(5 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(7 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(8 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2),
						new Collectable(26 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(10 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(11 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2),
						new Collectable(12 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(13 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(14 * cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(15* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2),
						new Collectable(16* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(17* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(18* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(19* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2),
						new Collectable(20* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(22* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(23* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2),
						new Collectable(24* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2), new Collectable(25* cellDim + cellDim / 2, 5 * cellDim + cellDim / 2),
						// row 6
						new Collectable(cellDim + cellDim / 2, 6 * cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 6 * cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 6 * cellDim + cellDim / 2), new Collectable(18 * cellDim + cellDim / 2, 6 * cellDim + cellDim / 2),
						new Collectable(21 * cellDim + cellDim / 2, 6 * cellDim + cellDim / 2), new Collectable(26 * cellDim + cellDim / 2, 6 * cellDim + cellDim / 2),
						// row 7
						new Collectable(cellDim + cellDim / 2, 7 * cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 7 * cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 7 * cellDim + cellDim / 2), new Collectable(18 * cellDim + cellDim / 2, 7 * cellDim + cellDim / 2),
						new Collectable(21 * cellDim + cellDim / 2, 7 * cellDim + cellDim / 2), new Collectable(26 * cellDim + cellDim / 2, 7 * cellDim + cellDim / 2),
						// row 8
						new Collectable(cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(2 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(3 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(4 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2),
						new Collectable(5 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2),
						new Collectable(10 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(11 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(18 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(15* cellDim + cellDim / 2, 8 * cellDim + cellDim / 2),
						new Collectable(16* cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(17* cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(26 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 8 * cellDim + cellDim / 2),
						new Collectable(22* cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(23* cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(24 * cellDim + cellDim / 2, 8 * cellDim + cellDim / 2), new Collectable(25* cellDim + cellDim / 2, 8 * cellDim + cellDim / 2),
						// row 9
						new Collectable(6 * cellDim + cellDim / 2, 9 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 9 * cellDim + cellDim / 2),
						// row 10
						new Collectable(6 * cellDim + cellDim / 2, 10 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 10 * cellDim + cellDim / 2),
						// row 11
						new Collectable(6 * cellDim + cellDim / 2, 11 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 11 * cellDim + cellDim / 2),
						// row 12
						new Collectable(6 * cellDim + cellDim / 2, 12 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 12 * cellDim + cellDim / 2),
						// row 13
						new Collectable(6 * cellDim + cellDim / 2, 13 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 13 * cellDim + cellDim / 2),
						// row 14
						new Collectable(6 * cellDim + cellDim / 2, 14 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 14 * cellDim + cellDim / 2),
						// row 15
						new Collectable(6 * cellDim + cellDim / 2, 15 * cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 15 * cellDim + cellDim / 2),
						// row 16
						new Collectable(6 * cellDim + cellDim / 2, 16* cellDim + cellDim / 2), new Collectable(21 * cellDim + cellDim / 2, 16* cellDim + cellDim / 2),
						// row 17
						new Collectable(6 * cellDim + cellDim / 2, 17* cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 17* cellDim + cellDim / 2),
						// row 18
						new Collectable(6 * cellDim + cellDim / 2, 18* cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 18* cellDim + cellDim / 2),
						// row 19
						new Collectable(6 * cellDim + cellDim / 2, 19* cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 19* cellDim + cellDim / 2),
						// row 20
						new Collectable(cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(2 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(3 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(4 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2),
						new Collectable(5 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(7 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(8 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2),
						new Collectable(9 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(10 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(11 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2),
						new Collectable(26 * cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(19* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(16* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(17* cellDim + cellDim / 2, 20* cellDim + cellDim / 2),
						new Collectable(18* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(20* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(15* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 20* cellDim + cellDim / 2),
						new Collectable(22* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(23* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(24* cellDim + cellDim / 2, 20* cellDim + cellDim / 2), new Collectable(25* cellDim + cellDim / 2, 20* cellDim + cellDim / 2),

						// row 21
						new Collectable(cellDim + cellDim / 2, 21* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 21* cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 21* cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 21* cellDim + cellDim / 2),
						new Collectable(21* cellDim + cellDim / 2, 21* cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 21* cellDim + cellDim / 2),
						// row 22
						new Collectable(cellDim + cellDim / 2, 22* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 22* cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 22* cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 22* cellDim + cellDim / 2),
						new Collectable(21* cellDim + cellDim / 2, 22* cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 22* cellDim + cellDim / 2),
						// row 23
						new Collectable(2 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(3 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(7 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2),
						new Collectable(8 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(10 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(11 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2),
						new Collectable(12 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(16* cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(17* cellDim + cellDim / 2, 23* cellDim + cellDim / 2),
						new Collectable(18* cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(19* cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(20* cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 23* cellDim + cellDim / 2),
						new Collectable(24* cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(25* cellDim + cellDim / 2, 23* cellDim + cellDim / 2),
						// row 24
						new Collectable(3 * cellDim + cellDim / 2, 24* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 24* cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 24* cellDim + cellDim / 2), new Collectable(18* cellDim + cellDim / 2, 24* cellDim + cellDim / 2),
						new Collectable(21* cellDim + cellDim / 2, 24* cellDim + cellDim / 2), new Collectable(24* cellDim + cellDim / 2, 24* cellDim + cellDim / 2),
						// row 25
						new Collectable(3 * cellDim + cellDim / 2, 25* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 25* cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 25* cellDim + cellDim / 2), new Collectable(18* cellDim + cellDim / 2, 25* cellDim + cellDim / 2),
						new Collectable(21* cellDim + cellDim / 2, 25* cellDim + cellDim / 2), new Collectable(24* cellDim + cellDim / 2, 25* cellDim + cellDim / 2),
						// row 26
						new Collectable(cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(2 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(3 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(4 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2),
						new Collectable(5 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(10 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2),
						new Collectable(11 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(16* cellDim + cellDim / 2, 26* cellDim + cellDim / 2),
						new Collectable(17* cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(18* cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(22* cellDim + cellDim / 2, 26* cellDim + cellDim / 2),
						new Collectable(23* cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(24* cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(25* cellDim + cellDim / 2, 26* cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 26* cellDim + cellDim / 2),
						// row 27
						new Collectable(cellDim + cellDim / 2, 27* cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 27* cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 27* cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 27* cellDim + cellDim / 2),
						// row 28
						new Collectable(cellDim + cellDim / 2, 28* cellDim + cellDim / 2), new Collectable(12 * cellDim + cellDim / 2, 28* cellDim + cellDim / 2), new Collectable(15 * cellDim + cellDim / 2, 28* cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 28* cellDim + cellDim / 2),
						// row 29
						new Collectable(cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(2 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(3 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(4 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2),
						new Collectable(5 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(6 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(7 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(26 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2),
						new Collectable(8 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(9 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(10 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(11 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2),
						new Collectable(12 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(13 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(14 * cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(15* cellDim + cellDim / 2, 29* cellDim + cellDim / 2),
						new Collectable(16* cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(17* cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(18* cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(19* cellDim + cellDim / 2, 29* cellDim + cellDim / 2),
						new Collectable(20* cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(21* cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(22* cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(23* cellDim + cellDim / 2, 29* cellDim + cellDim / 2),
						new Collectable(24* cellDim + cellDim / 2, 29* cellDim + cellDim / 2), new Collectable(25* cellDim + cellDim / 2, 29* cellDim + cellDim / 2) };
		
		bigPelets = new Collectable[] { new Collectable(cellDim + cellDim / 2, 3 * cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 3* cellDim + cellDim / 2),
				new Collectable(cellDim + cellDim / 2, 23* cellDim + cellDim / 2), new Collectable(26* cellDim + cellDim / 2, 23* cellDim + cellDim / 2) };
	}
	
	//sets the X coordinate
	public void setPozX(int x)
	{
		this.pozX = x;
	}
	
	//sets the Y coordinate
	public void setPozY(int y)
	{
		this.pozY = y;
	}
	
	//sets the current image according to the state
	public void setCurrentImage(char direction, GhostState state) {
		if(state != GhostState.EATEN) {
			switch (direction) {
			
			case 'U' :
				this.currentImg = this.upImg.getImage();
				break;
				
			case 'D' :
				this.currentImg = this.downImg.getImage();
				break;
				
			case 'L' :
				this.currentImg = this.leftImg.getImage();
				break;
				
			case 'R' :
				this.currentImg = this.rightImg.getImage();
				break;
			}
		}
		else {
			switch (direction) {
			
			case 'U' :
				this.currentImg = eyesUp.getImage();
				break;
				
			case 'D' :
				this.currentImg = eyesDown.getImage();
				break;
				
			case 'L' :
				this.currentImg = eyesLeft.getImage();
				break;
				
			case 'R' :
				this.currentImg = eyesRight.getImage();
				break;
			}
		}
	}
	
	public static void setGhostEaten(int i, boolean value) {
		
		eatenGhosts[i] = value;
	}
}
