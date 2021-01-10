package pacManPackage;

//Walls class refers to any wall within the maze
public class Walls
{
	private int[] coords = new int[4];
	
	public Walls()
	{
		this.coords[0] = 0;
		this.coords[1] = 0;
		this.coords[2] = 0;
		this.coords[3] = 0;
	}
	
	public Walls(int xi, int xs, int yi, int ys)
	{
		this.coords[0] = xi;
		this.coords[1] = xs;
		this.coords[2] = yi;
		this.coords[3] = ys;
	}
	
	public int[] getWallCoordinates()
	{
		return this.coords;
	}
}
