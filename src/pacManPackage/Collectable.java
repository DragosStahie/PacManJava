package pacManPackage;

//Collectible class refers to any object within the maze that PacMan is able to pick up
public class Collectable
{
	private int x;
	private int y;
	private boolean visible = false;
	
	public Collectable()
	{
		this.x = 0;
		this.y = 0;

	}
	
	public Collectable(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.visible = true;

	}
	
	//returns the x coordinate
	public int getX()
	{
		return this.x;
	}
	
	//returns the y coordinate
	public int getY()
	{
		return this.y;
	}
	
	//returns whether the Collectible instance is visible on the screen
	//	or intractable with
	public boolean getVisibilityStatus()
	{
		return this.visible;
	}
	
	//updates the visibility status of the Collectible instance
	public void setVisibilityStatus(boolean status)
	{
		this.visible = status;
	}
}
